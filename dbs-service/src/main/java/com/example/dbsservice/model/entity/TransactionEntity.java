package com.example.dbsservice.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_TRANSACTION")
public class TransactionEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "REQUEST_TYPE")
    private String requestType;
    @Column(name = "SOURCE_ACCOUNT")
    private String sourceAccount;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "DEBTOR_ACCOUNT_NUMBER")
    private String debtorAccountNumber;
    @Column(name = "DEBTOR_ACCOUNT_NAME")
    private String debtorAccountName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
}
