package com.app.midtrans.domain.payment;

public interface PaymentGatewayPort {
    Payment createPaymentMidtrans(Payment payment);
}
