package com.app.midtrans.infra.adapter.outbound.client.midtrans;

import com.app.midtrans.domain.payment.Payment;
import com.app.midtrans.domain.payment.PaymentGatewayPort;
import org.springframework.stereotype.Component;

@Component
public class MidtransGatewayAdapter implements PaymentGatewayPort {

    @Override
    public Payment createPaymentMidtrans(Payment payment) {
        return null;
    }

}
