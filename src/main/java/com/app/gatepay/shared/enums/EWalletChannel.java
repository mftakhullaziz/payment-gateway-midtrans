package com.app.gatepay.shared.enums;

import lombok.Getter;

import java.util.Arrays;

public enum EWalletChannel {

    QRIS("QRIS", "e_wallet", "qris"),
    GOPAY("GOPAY", "e_wallet", "gopay"),
    SHOPEEPAY("SHOPEEPAY", "e_wallet", "shopeepay"),
    ;

    @Getter
    private final String label;

    @Getter
    private final String paymentType;

    @Getter
    private final String ewalletType;

    EWalletChannel(String label, String paymentType, String ewalletType) {
        this.label = label;
        this.paymentType = paymentType;
        this.ewalletType = ewalletType;
    }

    public static EWalletChannel from(String value) {
        return Arrays.stream(values())
            .filter(v -> v.label.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException("Unsupported bank transfer: " + value)
            );
    }

    public static EWalletChannel fromPaymentType(String ewalletType) {
        return Arrays.stream(values())
            .filter(v -> ewalletType.equalsIgnoreCase(v.getEwalletType()))
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException("Unsupported bank: " + ewalletType)
            );
    }
}
