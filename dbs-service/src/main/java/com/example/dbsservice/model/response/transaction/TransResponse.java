package com.example.dbsservice.model.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransResponse {
    private String id;
    private String userId;
    private String userName;
    private String requestType;
    private String sourceAccount;
    private String amount;
    private String debtorAccountNumber;
    private String debtorAccountName;
    private String description;
    private String status;
    private Date createdDate;
    private Date updatedDate;
}
