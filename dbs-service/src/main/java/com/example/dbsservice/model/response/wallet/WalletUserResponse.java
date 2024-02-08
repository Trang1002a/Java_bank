package com.example.dbsservice.model.response.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletUserResponse {
    private String walletId;
    private String walletName;
    private String accountNumber;
    private String accountName;
    private String imageUrl;
    private String status;
}
