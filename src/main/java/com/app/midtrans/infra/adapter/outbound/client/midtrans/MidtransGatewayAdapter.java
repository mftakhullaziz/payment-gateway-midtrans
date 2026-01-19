package com.app.midtrans.infra.adapter.outbound.client.midtrans;

import com.app.midtrans.domain.payment.Payment;
import com.app.midtrans.domain.payment.PaymentGatewayPort;
import com.app.midtrans.shared.enums.VaChannel;
import com.app.midtrans.shared.resources.PaymentResource;
import com.app.midtrans.shared.restclient.RestClientInvoker;
import com.app.midtrans.shared.utils.Base64Utils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MidtransGatewayAdapter implements PaymentGatewayPort {

    private static final Logger log = LogManager.getLogger(MidtransGatewayAdapter.class);
    private final RestClientInvoker restClientInvoker;
    private final PaymentResource paymentResource;

    @Override
    public Payment executeTransferVA(Payment payment) {
        String serverKeyEncode = Base64Utils.encodeToBase64(paymentResource.getMidtrans().getServerKey());
        String vaPaymentURI = paymentResource.getMidtrans().getHostname() +
            paymentResource.getMidtrans().getCoreApi().getCharge();

        MidtransVARequest request = buildRequestBody(payment);
        MidtransVAResponse response = restClientInvoker.post(
            vaPaymentURI,
            request,
            Map.of(
                HttpHeaders.AUTHORIZATION,
                "Basic " + serverKeyEncode
            ),
            MidtransVAResponse.class
        );
        log.info("Response: {}", response);

        Payment paymentResponse = toPayment(response);
        paymentResponse.setCustomerId(payment.getCustomerId());
        return paymentResponse;
    }

    @Override
    public Payment executeTransferEWallet(Payment payment) {
        return null;
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
        MidtransVARequest.TransactionDetails details =
            new MidtransVARequest.TransactionDetails();
        details.setOrderId(payment.getOrderId());
        details.setGrossAmount(payment.getTotalAmount().longValue());
        return details;
    }

    private MidtransVARequest.BankTransfer buildBankTransfer(String bank) {
        MidtransVARequest.BankTransfer bankTransfer =
            new MidtransVARequest.BankTransfer();
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
