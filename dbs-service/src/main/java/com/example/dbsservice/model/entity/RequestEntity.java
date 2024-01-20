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
@Table(name = "TBL_Request")
public class RequestEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "REQUEST_TYPE")
    private String requestType;
    @Column(name = "REQUEST_BODY")
    private String requestBody;
    @Column(name = "OTP_ID")
    private String otpId;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
}
