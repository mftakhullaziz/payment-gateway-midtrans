package com.application.paymentmidtranssrv.domain;

import lombok.Getter;

@Getter
public enum PaymentTypes {
    BANK_TRANSFER,
    CREDIT_CARD,
    E_WALLET,
    QRIS
}
