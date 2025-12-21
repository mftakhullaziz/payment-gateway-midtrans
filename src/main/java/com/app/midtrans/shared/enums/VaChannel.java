package com.app.midtrans.shared.enums;

import lombok.Getter;

import java.util.Arrays;

public enum VaChannel {

    BCA_VA("BCA VA", "bank_transfer", "bca"),
    BNI_VA("BNI VA", "bank_transfer", "bni"),
    BRI_VA("BRI VA", "bank_transfer", "bri"),
    CIMB_VA("CIMB VA", "bank_transfer", "cimb"),
    PERMATA_VA("PERMATA VA", "permata", "permata"),
    MANDIRI_VA("MANDIRI VA", "echannel", "mandiri");

    @Getter
    private final String label;

    @Getter
    private final String paymentType;

    @Getter
    private final String bankName;

    VaChannel(String label, String paymentType, String bankName) {
        this.label = label;
        this.paymentType = paymentType;
        this.bankName = bankName;
    }

    public static VaChannel from(String value) {
        return Arrays.stream(values())
            .filter(v -> v.label.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException("Unsupported bank transfer: " + value)
            );
    }

    public static VaChannel fromBank(String bank) {
        return Arrays.stream(values())
            .filter(v -> bank.equalsIgnoreCase(v.getBankName()))
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException("Unsupported bank: " + bank)
            );
    }

}

