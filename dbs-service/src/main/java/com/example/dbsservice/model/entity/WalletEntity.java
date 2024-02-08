package com.example.dbsservice.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_WALLET")
public class WalletEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "WALLET_NAME")
    private String walletName;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
}
