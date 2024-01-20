package com.example.dbsservice.presentation.service;

import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.request.account.CheckNameRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.account.AccountResponse;

import java.util.List;

public interface AccountService {
//    List<UserResponse> findAll();
//    UserResponse findById(String id);

    ResStatus save(UserEntity userEntity);

    AccountResponse checkName(CheckNameRequest request);

    List<AccountResponse> getInfo();

//    UserResponse getInfo();
}
