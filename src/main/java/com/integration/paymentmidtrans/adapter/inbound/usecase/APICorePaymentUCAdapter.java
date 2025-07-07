package com.integration.paymentmidtrans.adapter.inbound.usecase;

//import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.APICorePaymentPresenter;
//import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.VAChargeRequest;
//import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.PaymentMidtransResponse;
//import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.VAChargeResponse;
//import com.integration.paymentmidtrans.adapter.inbound.usecase.mapper.CoreAPIPaymentUCMapper;
//import com.integration.paymentmidtrans.ports.inbound.usecase.APICorePaymentUCPort;
//import com.integration.paymentmidtrans.ports.outbound.email.EmailOutboundPort;
//import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.CustomerJPAOutboundPort;
//import com.integration.paymentmidtrans.ports.outbound.midtrans.MidtransCoreAPIOutboundPort;
//import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.PaymentJPAOutboundPort;
//import com.integration.paymentmidtrans.shared.annotation.Usecase;
//import com.integration.paymentmidtrans.shared.dto.coreapis.VaTransferDTO;
//import com.integration.paymentmidtrans.shared.enums.PaymentTypes;
//import com.integration.paymentmidtrans.shared.exception.BusinessException;
//import com.integration.paymentmidtrans.shared.utility.ValidatorUtility;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.transaction.annotation.Transactional;
//
//@Log4j2
//@Usecase
//@RequiredArgsConstructor
//public class APICorePaymentUCAdapter implements APICorePaymentUCPort {
//
//    private final CustomerJPAOutboundPort customerPort;
//    private final MidtransCoreAPIOutboundPort midtransCoreAPIPort;
//    private final EmailOutboundPort emailPort;
//    private final PaymentJPAOutboundPort paymentPort;
//
//    @Transactional(rollbackFor = BusinessException.class)
//    @Override
//    public void vaExecutor(VAChargeRequest vaChargeRequest, APICorePaymentPresenter presenter) {
//        String email = vaChargeRequest.getCustomerDetails().getEmail();
//        try {
//            Boolean customerPresent = customerPort.checkCustomerAndHasRole(email, "CUSTOMER");
//            ValidatorUtility.requirePresent(customerPresent, "Invalid customer for payment", HttpStatus.NOT_FOUND);
//
//            VaTransferDTO vaTransferDTO = CoreAPIPaymentUCMapper.vaTransferMapper(vaChargeRequest);
//            ValidatorUtility.requireNonNull(vaTransferDTO, "Invalid payment-midtrans payload", HttpStatus.UNPROCESSABLE_ENTITY);
//
//            PaymentMidtransResponse midtransResponse = coreapiPaymentExecutor(vaTransferDTO, vaTransferDTO.getPaymentTypes());
//            ValidatorUtility.requireNonNull(midtransResponse, "Failed to create payment transaction with Midtrans", HttpStatus.BAD_GATEWAY);
//
//            emailNotifyPayment(vaChargeRequest, midtransResponse, email);
//
////            paymentPort.savePaymentTransaction();
//        } catch (BusinessException e) {
//            throw new BusinessException("Error message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//        constructVaResponse(presenter);
//    }
//
//    private void emailNotifyPayment(VAChargeRequest vaChargeRequest, PaymentMidtransResponse midtransResponse, String email) {
//        String nameReceiver = vaChargeRequest.getCustomerDetails().getFirstName() + " " + vaChargeRequest.getCustomerDetails().getLastName();
//        emailPort.publishEmailRemainderNotification(
//            email,
//            nameReceiver,
//            midtransResponse.getVaNumbers().getFirst().getVaNumber(),
//            midtransResponse.getVaNumbers().getFirst().getBank(),
//            midtransResponse.getExpiryTime(),
//            midtransResponse.getTransactionStatus().toUpperCase());
//    }
//
//    private PaymentMidtransResponse coreapiPaymentExecutor(VaTransferDTO vaTransferDTO, PaymentTypes paymentTypes) {
//        if (PaymentTypes.VA_BANK_TRANSFER.equals(paymentTypes)) {
//            return midtransCoreAPIPort.executePayMidtransBankTransfer(vaTransferDTO);
//        }
//        return null;
//    }
//
//    private void constructVaResponse(APICorePaymentPresenter presenter) {
//        VAChargeResponse vaChargeResponse = new VAChargeResponse();
//        vaChargeResponse.setPaymentType("");
//        vaChargeResponse.setOrderId("");
//        vaChargeResponse.setTransactionId("");
//        presenter.vaPaymentResponse(vaChargeResponse);
//    }
//
//}
