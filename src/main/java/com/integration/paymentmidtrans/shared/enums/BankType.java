package com.integration.paymentmidtrans.shared.enums;

import lombok.Getter;

@Getter
public enum BankType {
    BCA("bca"),
    BRI("bri"),
    CIMB("cimb"),
    MANDIRI("mandiri"),
    PERMATA("permata"),
    BNI("bni");

    private final String value;

    BankType(String value) {
        this.value = value;
    }
}
