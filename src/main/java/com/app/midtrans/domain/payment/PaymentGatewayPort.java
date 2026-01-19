package com.app.midtrans.domain.payment;

public interface PaymentGatewayPort {
    Payment executeTransferVA(Payment payment);
    Payment executeTransferEWallet(Payment payment);
}
