package com.example.dbsservice.model.response.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String userName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String status;
    private String cardNumber;
    private String address;
    private String customerType;
    private Date createdDate;
    private Date updatedDate;
}
