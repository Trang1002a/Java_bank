package com.example.dbsservice.utils.mapper;

import com.example.dbsservice.jwt.PasswordService;
import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.request.customer.UserRequest;
import com.example.dbsservice.model.response.customer.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class CustomerMapper {
    public UserResponse mapUserEntityToUserResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setUserName(userEntity.getUserName());
        userResponse.setFullName(userEntity.getFullName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setPhoneNumber(userEntity.getPhoneNumber());
        userResponse.setCardNumber(userEntity.getCardNumber());
        userResponse.setAddress(userEntity.getAddress());
        userResponse.setStatus(userEntity.getStatus());
        userResponse.setCreatedDate(userEntity.getCreatedDate());
        userResponse.setUpdatedDate(userEntity.getUpdatedDate());
        return userResponse;
    }
    public UserEntity mapUserRequestToUserEntity(UserRequest userRequest) {
        String password = PasswordService.encodePassword(userRequest.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setFullName(userRequest.getFullName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhoneNumber(userRequest.getPhoneNumber());
        userEntity.setCardNumber(userRequest.getCardNumber());
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setStatus(userRequest.getStatus());
        userEntity.setCustomerType(userRequest.getCustomerType());
        userEntity.setPassword(password);
        userEntity.setCreatedDate(new Date(System.currentTimeMillis()));
        userEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        return userEntity;
    }

    public UserEntity mapUserRequestToUserEntity(UserRequest userRequest, UserEntity userEntity) {
        userEntity.setFullName(userRequest.getFullName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhoneNumber(userRequest.getPhoneNumber());
        userEntity.setCardNumber(userRequest.getCardNumber());
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        return userEntity;
    }
}
