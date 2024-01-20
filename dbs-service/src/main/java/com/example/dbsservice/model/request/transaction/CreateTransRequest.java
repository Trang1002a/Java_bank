package com.example.dbsservice.model.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransRequest {
    private String sourceAccount;
    private String amount;
    private String debtorAccountNumber;
    private String debtorAccountName;
    private String description;
    private boolean saveAccount;
}
