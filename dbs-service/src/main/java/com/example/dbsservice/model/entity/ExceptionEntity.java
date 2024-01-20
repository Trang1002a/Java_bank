package com.example.dbsservice.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tbl_exception")
public class ExceptionEntity {
    @Id
    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Column(name = "ERROR_DESC")
    private String errorDesc;

    @Column(name = "MESSAGE_VN")
    private String messageVn;

    @Column(name = "MESSAGE_EN")
    private String messageEn;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

}
