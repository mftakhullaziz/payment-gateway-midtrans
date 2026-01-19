package com.app.gatepay.domain.bank;

public interface BankPersistencePort {
    Bank getBankByName(String bankName);
}
