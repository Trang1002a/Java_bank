package com.example.dbsservice.model.request.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletLinkRequest {
    private String requestType;
    private String walletId;
    private String accountNumber;
    private String accountName;
}
