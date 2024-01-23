package com.example.dbsservice.model.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {
    private String oldPassword;
    private String newPassword;
}
