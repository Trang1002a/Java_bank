package com.example.dbsservice.presentation.impl;

import com.example.dbsservice.configuration.TokenConfig;
import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.jwt.PasswordService;
import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.repository.UserRepository;
import com.example.dbsservice.model.response.auth.LoginResponse;
import com.example.dbsservice.presentation.service.AuthService;
import com.example.dbsservice.utils.JwtToken;
import com.example.dbsservice.utils.StatusType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import project.api.spec.model.CreateLoginRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    UserRepository userRepository;

//    @Autowired
//    private KafkaTemplate kafkaTemplate;

    @Value(value = "${tpb.kafka.topic-name}")
    private String topicName;

    @Resource
    private JwtToken jwtToken;

    @Resource
    private TokenConfig tokenConfig;

    @Override
    public LoginResponse login(CreateLoginRequest request) {
        LoginResponse response = new LoginResponse();
        String userName = request.getUserName();
        String password = request.getPassword();
        String customerType = request.getCustomerType();
        if (StringUtils.isBlank(userName)) {
            throw new ProjectException(ErrorCode.USERNAME_CANNOT_BE_EMPTY);
        }
        if (StringUtils.isBlank(password)) {
            throw new ProjectException(ErrorCode.PASSWORD_CANNOT_BE_EMPTY);
        }
        if (!StringUtils.equalsAnyIgnoreCase(customerType, "CUSTOMER")) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Optional<UserEntity> userEntity = userRepository.findByUserNameAndStatusAndCustomerType(
                userName, StatusType.ACTIVE.name(), customerType);
        if (!userEntity.isPresent()) {
            throw new ProjectException(ErrorCode.INCORRECT_ACCOUNT_OR_PASSWORD);
        }
        if (!PasswordService.matchPassword(password, userEntity.get().getPassword())) {
            throw new ProjectException(ErrorCode.INCORRECT_ACCOUNT_OR_PASSWORD);
        }
        String token = jwtToken.generateToken(tokenConfig, userEntity.get());
        response.setToken(token);
        response.setExpiredTime(new Date(Long.parseLong(System.currentTimeMillis() + tokenConfig.getExpriedTime())).toString());
        return response;
    }
}
