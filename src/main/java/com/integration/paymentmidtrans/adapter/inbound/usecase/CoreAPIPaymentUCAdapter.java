package com.integration.paymentmidtrans.adapter.inbound.usecase;

import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.APICorePaymentPresenter;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.request.VAChargeRequest;
import com.integration.paymentmidtrans.adapter.inbound.delivery.coreapis.response.PaymentMidtransResponse;
import com.integration.paymentmidtrans.adapter.inbound.usecase.mapper.CoreAPIPaymentUCMapper;
import com.integration.paymentmidtrans.ports.inbound.usecase.iCoreAPIPaymentUCPort;
import com.integration.paymentmidtrans.ports.outbound.mysql.jpa.CustomerJPAOutboundPort;
import com.integration.paymentmidtrans.ports.outbound.midtrans.MidtransCoreAPIOutboundPort;
import com.integration.paymentmidtrans.shared.annotation.Usecase;
import com.integration.paymentmidtrans.shared.dto.coreapis.VaTransferDTO;
import com.integration.paymentmidtrans.shared.enums.PaymentTypes;
import com.integration.paymentmidtrans.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class CoreAPIPaymentUCAdapter implements iCoreAPIPaymentUCPort {

  private final CustomerJPAOutboundPort customerJPAOutboundPort;
  private final MidtransCoreAPIOutboundPort midtransCoreAPIOutboundPort;

  @Transactional(rollbackFor = BusinessException.class)
  @Override
  public void vaExecutor(VAChargeRequest vaChargeRequest, APICorePaymentPresenter presenter) {
    String email = vaChargeRequest.getCustomerDetails().getEmail();

    try {
      Boolean customerExist = customerJPAOutboundPort.checkCustomerAndHasRole(email, "CUSTOMER");
      if (Boolean.FALSE.equals(customerExist)) {
        throw new BusinessException("Invalid customer for payment", HttpStatus.NOT_FOUND.value());
      }

      // Transform to vaTransferDTO
      VaTransferDTO vaTransferDTO = CoreAPIPaymentUCMapper.vaTransferMapper(vaChargeRequest);

      PaymentMidtransResponse midtransResponse = coreapiPaymentExecutor(vaTransferDTO, vaTransferDTO.getPaymentTypes());
      if (midtransResponse == null) {
        throw new BusinessException("Failed to create payment transaction with Midtrans", HttpStatus.BAD_GATEWAY.value()); // 502
      }


    } catch (BusinessException e) {
      throw new BusinessException("Error message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    presenter.vaPaymentResponse(null);
  }

  private PaymentMidtransResponse coreapiPaymentExecutor(VaTransferDTO vaTransferDTO, PaymentTypes paymentTypes) {
    return switch (paymentTypes) {
      case BANK_TRANSFER -> midtransCoreAPIOutboundPort.executePayMidtransBankTransfer(vaTransferDTO);
      case CREDIT_CARD -> midtransCoreAPIOutboundPort.executePayMidtransCreditCard(vaTransferDTO);
      default -> throw new BusinessException("enum not found", HttpStatus.UNPROCESSABLE_ENTITY.value());
    };
  }

}
