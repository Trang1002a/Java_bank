package com.example.dbsservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoDto {
    private String userId;
    private String userName;
    private String phoneNumber;
}
