package com.application.paymentmidtransservice.domain;

import lombok.Getter;

@Getter
public enum PaymentTypes {
    BANK_TRANSFER,
    CREDIT_CARD,
    E_WALLET,
    QRIS
}
