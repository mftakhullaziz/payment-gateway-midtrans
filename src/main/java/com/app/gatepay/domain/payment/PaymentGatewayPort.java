package com.app.gatepay.domain.payment;

public interface PaymentGatewayPort {
    Payment executeTransferVA(Payment payment);
    Payment executeTransferEWallet(Payment payment);
}
