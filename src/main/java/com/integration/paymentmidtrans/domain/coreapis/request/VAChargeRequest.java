package com.integration.paymentmidtrans.domain.coreapis.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VAChargeRequest {

  private String paymentType; // ex: "bank_transfer" or "echannel"
  private TransactionDetails transactionDetails;
  private CustomerDetails customerDetails;
  private List<ItemDetails> itemDetails;
  private BankTransfer bankTransfer;
  private EChannel echannel;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TransactionDetails {
    private String orderId;
    private Long grossAmount;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CustomerDetails {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ItemDetails {
    private String id;
    private Long price;
    private Integer quantity;
    private String name;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BankTransfer {
    private String bank; // permata, bca, bni
    private String vaNumber; // khusus bca, bni
    private PermataBank permata;
    private BcaBank bca;
    private Map<String, List<FreeText>> freeText; // free text untuk inquiry/payment

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermataBank {
      private String recipientName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BcaBank {
      private String subCompanyCode;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FreeText {
      private String id;
      private String en;
    }
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EChannel {
    private String billInfo1;
    private String billInfo2;
    private String billKey;
  }
}
