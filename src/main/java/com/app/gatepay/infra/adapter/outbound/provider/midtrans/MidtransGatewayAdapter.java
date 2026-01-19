package com.app.gatepay.infra.adapter.outbound.provider.midtrans;

import com.app.gatepay.domain.payment.Payment;
import com.app.gatepay.domain.payment.PaymentGatewayPort;
import com.app.gatepay.infra.config.GatepayPaymentProperties;
import com.app.gatepay.shared.enums.VaChannel;
import com.app.gatepay.shared.resources.PaymentResource;
import com.app.gatepay.shared.restclient.RestClientInvoker;
import com.app.gatepay.shared.utils.Base64Utils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("midtransPaymentGateway")
@RequiredArgsConstructor
public class MidtransGatewayAdapter implements PaymentGatewayPort {

    private static final Logger log = LogManager.getLogger(MidtransGatewayAdapter.class);

    private final RestClientInvoker restClientInvoker;

    /** New config namespace */
    private final GatepayPaymentProperties paymentProperties;

    /** Legacy config namespace (kept for backward compatibility) */
    private final PaymentResource paymentResource;

    @Override
    public Payment executeTransferVA(Payment payment) {
        String serverKey = resolveServerKey();
        String hostname = resolveHostname();
        String chargePath = resolveChargePath();

        String serverKeyEncode = Base64Utils.encodeToBase64(serverKey);
        String vaPaymentURI = hostname + chargePath;

        MidtransVARequest request = buildRequestBody(payment);
        MidtransVAResponse response = restClientInvoker.post(
            vaPaymentURI,
            request,
            Map.of(HttpHeaders.AUTHORIZATION, "Basic " + serverKeyEncode),
            MidtransVAResponse.class
        );

        log.info("Response: {}", response);

        Payment paymentResponse = toPayment(response);
        paymentResponse.setCustomerId(payment.getCustomerId());
        return paymentResponse;
    }

    @Override
    public Payment executeTransferEWallet(Payment payment) {
        // TODO: implement provider-specific e-wallet charge
        throw new UnsupportedOperationException("Midtrans e-wallet is not implemented yet");
    }

    private String resolveServerKey() {
        String v = paymentProperties.getProviders().getMidtrans().getServerKey();
        if (v != null && !v.isBlank()) return v;
        return paymentResource.getMidtrans().getServerKey();
    }

    private String resolveHostname() {
        String v = paymentProperties.getProviders().getMidtrans().getHostname();
        if (v != null && !v.isBlank()) return v;
        return paymentResource.getMidtrans().getHostname();
    }

    private String resolveChargePath() {
        String v = paymentProperties.getProviders().getMidtrans().getCoreApi().getCharge();
        if (v != null && !v.isBlank()) return v;
        return paymentResource.getMidtrans().getCoreApi().getCharge();
    }

    private Payment.PaymentBuilder basePayment(MidtransVAResponse response) {
        return Payment.builder()
            .transactionId(response.getTransactionId())
            .orderId(response.getOrderId())
            .currency(response.getCurrency())
            .paymentType(response.getPaymentType())
            .transactionStatus(response.getTransactionStatus())
            .transactionTime(response.getTransactionTime())
            .provider("MIDTRANS")
            .expiredTime(response.getExpiryTime());
    }

    private Payment toPayment(MidtransVAResponse response) {
        Payment.PaymentBuilder builder = basePayment(response);

        // PERMATA VA
        if (response.getPermataVaNumber() != null) {
            return builder
                .vaChannel(VaChannel.PERMATA_VA)
                .vaBank("permata")
                .vaNumber(response.getPermataVaNumber())
                .build();
        }

        // MANDIRI VA (E-Channel)
        if (response.getBillKey() != null && response.getBillerCode() != null) {
            return builder
                .vaChannel(VaChannel.MANDIRI_VA)
                .billKey(response.getBillKey())
                .billCode(response.getBillerCode())
                .isMandiri(true)
                .build();
        }

        // BANK TRANSFER VA (BCA / BNI / BRI / CIMB)
        var va = response.getVaNumbers().getFirst();
        return builder
            .vaChannel(VaChannel.fromBank(va.getBank()))
            .vaBank(va.getBank())
            .vaNumber(va.getVaNumber())
            .build();
    }

    private MidtransVARequest buildRequestBody(Payment payment) {
        VaChannel channel = payment.getVaChannel();

        MidtransVARequest request = new MidtransVARequest();
        request.setPaymentType(channel.getPaymentType());
        request.setTransactionDetails(buildTransactionDetails(payment));

        if (channel.getBankName() != null) {
            request.setBankTransfer(buildBankTransfer(channel.getBankName()));
        }

        if (channel == VaChannel.MANDIRI_VA) {
            request.setEchannel(buildEchannel());
        }

        return request;
    }

    private MidtransVARequest.TransactionDetails buildTransactionDetails(Payment payment) {
        MidtransVARequest.TransactionDetails details = new MidtransVARequest.TransactionDetails();
        details.setOrderId(payment.getOrderId());
        details.setGrossAmount(payment.getTotalAmount().longValue());
        return details;
    }

    private MidtransVARequest.BankTransfer buildBankTransfer(String bank) {
        MidtransVARequest.BankTransfer bankTransfer = new MidtransVARequest.BankTransfer();
        bankTransfer.setBank(bank);
        return bankTransfer;
    }

    private MidtransVARequest.EChannel buildEchannel() {
        MidtransVARequest.EChannel echannel = new MidtransVARequest.EChannel();
        echannel.setBillInfo1("Test Bill Info 1");
        echannel.setBillInfo2("Test Bill Info 2");
        return echannel;
    }
}
