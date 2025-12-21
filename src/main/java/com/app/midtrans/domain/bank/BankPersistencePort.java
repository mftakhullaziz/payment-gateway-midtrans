package com.app.midtrans.domain.bank;

public interface BankPersistencePort {
    Bank getBankByName(String bankName);
}
