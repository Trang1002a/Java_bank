package com.example.dbsservice.model.response.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private String id;
    private String userId;
    private String cif;
    private String accountNumber;
    private String accountName;
    private String status;
    private BigDecimal amount;
    private Date createdDate;
    private Date updatedDate;
}
