package com.example.dbsservice.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_USER")
public class UserEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "FULLNAME")
    private String fullName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "CARD_NUMBER")
    private String cardNumber;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    @Column(name = "CUSTOMER_TYPE")
    private String customerType;
}
