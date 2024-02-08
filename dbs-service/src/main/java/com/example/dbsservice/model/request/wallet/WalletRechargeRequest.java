package com.example.dbsservice.model.request.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletRechargeRequest {
    private String sourceAccount;
    private String walletId;
    private String amount;
}
