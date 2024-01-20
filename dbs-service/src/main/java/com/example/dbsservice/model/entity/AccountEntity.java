package com.example.dbsservice.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_ACCOUNT")
public class AccountEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "CIF")
    private String cif;
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name = "ACCOUNT_NAME")
    private String accountName;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
}
