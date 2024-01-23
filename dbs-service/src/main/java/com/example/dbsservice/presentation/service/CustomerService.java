package com.example.dbsservice.presentation.service;

import com.example.dbsservice.model.request.customer.ChangePassword;
import com.example.dbsservice.model.request.customer.UserRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.customer.UserResponse;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;

import java.util.List;

public interface CustomerService {
    List<UserResponse> findAll();

    UserResponse findById(String id);

    ResStatus save(UserRequest userRequest);

    UserResponse getInfo();

    CreateTransResponse changePassword(ChangePassword changePassword);
}
