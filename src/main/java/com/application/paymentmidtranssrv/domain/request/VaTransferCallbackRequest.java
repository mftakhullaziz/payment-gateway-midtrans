package com.application.paymentmidtranssrv.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class VaTransferCallbackRequest {
    // Shared
    private String transaction_time;
    private String transaction_status;
    private String transaction_id;
    private String status_message;
    private String status_code;
    private String signature_key;
    private String settlement_time;
    private String payment_type;
    private String order_id;
    private String merchant_id;
    private String gross_amount;
    private String fraud_status;
    private String currency;

    // Common VA fields
    private List<VaNumber> va_numbers;            // For BCA, BNI, BRI
    private String permata_va_number;             // For Permata
    private List<PaymentAmount> payment_amounts;  // May exist for BNI, etc.

    // Mandiri bill-specific
    private String biller_code;                   // Mandiri only
    private String bill_key;                      // Mandiri only

    @Data
    public static class VaNumber {
        private String va_number;
        private String bank;
    }

    @Data
    public static class PaymentAmount {
        private String paid_at;
        private String amount;
    }
}
