package com.example.dbsservice.model.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRechargeRequest {
    private String selectedAccount;
    private String phoneNumber;
    private String network;
    private String denomination;
}
