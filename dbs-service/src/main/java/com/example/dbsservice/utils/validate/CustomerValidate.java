package com.example.dbsservice.utils.validate;

import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.repository.UserRepository;
import com.example.dbsservice.model.request.customer.UserRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class CustomerValidate {
    @Autowired
    private UserRepository userRepository;

    public void validateRequestUser(UserRequest userRequest) {
        if (StringUtils.isBlank(userRequest.getUserName())
                || StringUtils.isBlank(userRequest.getPhoneNumber())
                || StringUtils.isBlank(userRequest.getEmail())
                || StringUtils.isBlank(userRequest.getCardNumber())) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (checkExistUserName(userRequest.getUserName(), userRequest.getId())) {
            throw new ProjectException(ErrorCode.EXIST_USER_WITH_USERNAME);
        }
        if (checkExistPhoneNumber(userRequest.getPhoneNumber(), userRequest.getId())) {
            throw new ProjectException(ErrorCode.EXIST_USER_WITH_PHONENUMBER);
        }
        if (checkExistCardNumber(userRequest.getCardNumber(), userRequest.getId())) {
            throw new ProjectException(ErrorCode.EXIST_USER_WITH_CARDNUMBER);
        }
        if (checkExistEmail(userRequest.getEmail(), userRequest.getId())) {
            throw new ProjectException(ErrorCode.EXIST_USER_WITH_EMAIL);
        }

    }

    public boolean checkExistUserName(String userName, String id) {
        Optional<UserEntity> userEntity = userRepository.findByUserName(userName);
        return userEntity.isPresent() && checkId(userEntity.get().getId(), id);
    }

    public boolean checkExistPhoneNumber(String phoneNumber, String id) {
        Optional<UserEntity> userEntity = userRepository.findByPhoneNumber(phoneNumber);
        return userEntity.isPresent() && checkId(userEntity.get().getId(), id);
    }

    public boolean checkExistCardNumber(String cardNumber, String id) {
        Optional<UserEntity> userEntity = userRepository.findByCardNumber(cardNumber);
        return userEntity.isPresent() && checkId(userEntity.get().getId(), id);
    }

    public boolean checkExistEmail(String email, String id) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userEntity.isPresent() && checkId(userEntity.get().getId(), id);
    }

    private boolean checkId(String id, String requestId) {
        return StringUtils.isBlank(requestId) || !StringUtils.equalsIgnoreCase(id, requestId);
    }
}
