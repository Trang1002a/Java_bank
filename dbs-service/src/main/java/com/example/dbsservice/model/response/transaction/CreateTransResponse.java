package com.example.dbsservice.model.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransResponse {
    private String requestId;
    private String otpId;
    private String phoneNumber;
}
