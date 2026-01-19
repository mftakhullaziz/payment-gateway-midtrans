package com.app.gatepay.domain.bank;

import org.springframework.stereotype.Component;

@Component
public class BankService {

    public String getBankName(String bankTransfer) {
        return switch (bankTransfer) {
            case "permata" -> "permata";
            case "bca" -> "bca";
            case "bni" -> "bni";
            case "bri" -> "bri";
            case "mandiri" -> "mandiri";
            case "cimb" -> "cimb";
            default -> null;
        };
    }

    public void bankMustBeEligible(Bank bank) {
        if (bank == null) {
            throw new IllegalArgumentException("Bank transfer must not be null");
        }

        if (bank.getName().isBlank()) {
            throw new IllegalArgumentException("Bank transfer must not be empty");
        }
    }
}
