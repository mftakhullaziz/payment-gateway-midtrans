package com.application.paymentmidtranssrv.core.usecase.coreapi;

import com.application.paymentmidtranssrv.core.gateway.CustomerGateway;
import com.application.paymentmidtranssrv.core.gateway.EmailGateway;
import com.application.paymentmidtranssrv.core.gateway.MidtransGateway;
import com.application.paymentmidtranssrv.core.gateway.PaymentGateway;
import com.application.paymentmidtranssrv.delivery.coreapi.request.VATransferRequest;
import com.application.paymentmidtranssrv.delivery.coreapi.response.VATransferResponse;
import com.application.paymentmidtranssrv.facade.annotation.Usecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Usecase
@RequiredArgsConstructor
public class VATransferUsecase {

    private final CustomerGateway customerGateway;
    private final PaymentGateway paymentGateway;
    private final MidtransGateway midtransGateway;
    private final EmailGateway emailGateway;
    private final PlatformTransactionManager transactionManager;

    @Transactional(rollbackFor = Exception.class)
    public VATransferResponse vaExecutor(VATransferRequest vaTransferRequest) {
        return new VATransferResponse();
    }
}
