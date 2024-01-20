package com.example.dbsservice.model.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequest {
    private String requestId;
    private String otpId;
    private String otp;
}
