package com.example.dbsservice.model.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckNameRequest {
    private String accountNumber;
    private String type;
}
