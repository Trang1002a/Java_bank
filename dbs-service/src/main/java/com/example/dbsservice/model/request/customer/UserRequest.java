package com.example.dbsservice.model.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String id;
    private String userName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String status;
    private String cardNumber;
    private String address;
    private String customerType;
    private String password;
    private Date createdDate;
    private Date updatedDate;
}
