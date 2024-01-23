package com.example.dbsservice.presentation.impl;

import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.jwt.PasswordService;
import com.example.dbsservice.model.dto.UserInfoDto;
import com.example.dbsservice.model.entity.RequestEntity;
import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.repository.RequestRepository;
import com.example.dbsservice.model.repository.UserRepository;
import com.example.dbsservice.model.request.customer.ChangePassword;
import com.example.dbsservice.model.request.customer.UserRequest;
import com.example.dbsservice.model.request.transaction.CreateTransRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.customer.UserResponse;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.presentation.service.AccountService;
import com.example.dbsservice.presentation.service.CustomerService;
import com.example.dbsservice.utils.*;
import com.example.dbsservice.utils.mapper.CustomerMapper;
import com.example.dbsservice.utils.validate.ConvertUtils;
import com.example.dbsservice.utils.validate.CustomerValidate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Resource
    UserRepository userRepository;

    @Resource
    AccountService accountService;

    @Resource
    RequestUtils requestUtils;

    private final CustomerMapper customerMapper;
    private final CustomerValidate customerValidate;

    public CustomerServiceImpl(CustomerValidate customerValidate,
                               CustomerMapper customerMapper) {
        this.customerValidate = customerValidate;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<UserResponse> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(customerMapper::mapUserEntityToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return customerMapper.mapUserEntityToUserResponse(userEntity.get());
    }

    @Override
    public ResStatus save(UserRequest userRequest) {
        UserEntity userEntity;
        customerValidate.validateRequestUser(userRequest);
        try {
            if (StringUtils.isBlank(userRequest.getId())) {
                userEntity = customerMapper.mapUserRequestToUserEntity(userRequest);
            } else {
                Optional<UserEntity> userEntityOld = userRepository.findById(userRequest.getId());
                if (!userEntityOld.isPresent()) {
                    throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
                }
                userEntity = customerMapper.mapUserRequestToUserEntity(userRequest, userEntityOld.get());
            }
            accountService.save(userEntity);
            userRepository.save(userEntity);
        } catch (Exception e) {
            logger.error("CustomerServiceImpl create error: {}", e.getMessage());
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return new ResStatus(Contants.SUCCESS);
    }

    @Override
    public UserResponse getInfo() {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        Optional<UserEntity> userEntity = userRepository.findById(userInfoDto.getUserId());
        if (!userEntity.isPresent()) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return customerMapper.mapUserEntityToUserResponse(userEntity.get());
    }

    @Override
    public CreateTransResponse changePassword(ChangePassword changePassword) {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        Optional<UserEntity> userEntity = userRepository.findById(userInfoDto.getUserId());
        if (!userEntity.isPresent()) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (!PasswordService.matchPassword(changePassword.getOldPassword(), userEntity.get().getPassword())) {
            throw new ProjectException("", "Mật khẩu hiện tại không đúng");
        }
        return requestUtils.responseCreateTrans(userInfoDto, RequestType.CHANGE_PASSWORD, changePassword);

    }

//    private RequestEntity saveRequest(ChangePassword request, UserInfoDto userInfoDto) {
//        RequestEntity requestEntity = new RequestEntity();
//        requestEntity.setId(UUID.randomUUID().toString());
//        requestEntity.setUserId(userInfoDto.getUserId());
//        requestEntity.setRequestType(RequestType.CHANGE_PASSWORD.name());
//        requestEntity.setRequestBody(ConvertUtils.toJson(request));
//        requestEntity.setStatus(TransactionStatus.DRAF.name());
//        requestEntity.setOtpId(UUID.randomUUID().toString());
//        requestEntity.setCreatedDate(new Date(System.currentTimeMillis()));
//        requestEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
//        requestRepository.save(requestEntity);
//        return requestEntity;
//    }
}
