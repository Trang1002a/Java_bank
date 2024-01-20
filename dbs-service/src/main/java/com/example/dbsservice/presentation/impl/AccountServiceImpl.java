package com.example.dbsservice.presentation.impl;

import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.model.dto.UserInfoDto;
import com.example.dbsservice.model.entity.AccountEntity;
import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.repository.AccountRepository;
import com.example.dbsservice.model.request.account.CheckNameRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.account.AccountResponse;
import com.example.dbsservice.presentation.service.AccountService;
import com.example.dbsservice.utils.Contants;
import com.example.dbsservice.utils.Generator;
import com.example.dbsservice.utils.UserInfoService;
import com.example.dbsservice.utils.mapper.AccountMapper;
import com.example.dbsservice.utils.mapper.CustomerMapper;
import com.example.dbsservice.utils.validate.CustomerValidate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    AccountRepository accountRepository;

    private final AccountMapper accountMapper = new AccountMapper();

    @Override
    public ResStatus save(UserEntity userEntity) {
        Optional<AccountEntity> accountEntityCheck = accountRepository.findFirstByUserIdOrderByCreatedDateDesc(userEntity.getId());
        AccountEntity accountEntity = new AccountEntity();
        if (!accountEntityCheck.isPresent()) {
//            String cif = Generator.generateCif();
            accountEntity.setAccountNumber(Generator.generateAccountNumber(userEntity.getUserName(), false, null));
        } else {
            accountEntity.setAccountNumber(Generator.generateAccountNumber(accountEntityCheck.get().getCif(), true, accountEntityCheck.get().getAccountNumber()));
        }
        accountEntity.setCif(userEntity.getUserName());
        accountEntity.setId(UUID.randomUUID().toString());
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setAccountName(userEntity.getFullName());
        accountEntity.setAmount(new BigDecimal("9999999999"));
        accountEntity.setStatus(Contants.ACTIVE);
        accountEntity.setCreatedDate(new Date(System.currentTimeMillis()));
        accountEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        accountRepository.save(accountEntity);
        return new ResStatus(Contants.SUCCESS);
    }

    @Override
    public AccountResponse checkName(CheckNameRequest request) {
        if (StringUtils.isBlank(request.getAccountNumber())) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (StringUtils.equalsAnyIgnoreCase(request.getType(), Contants.INTERNAL)) {
            Optional<AccountEntity> accountEntity = accountRepository.findFirstByAccountNumberAndStatus(request.getAccountNumber(), Contants.ACTIVE);
            if (!accountEntity.isPresent()) {
                throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
            return accountMapper.mapToAccountResponse(accountEntity.get());
        }
        return null;
    }

    @Override
    public List<AccountResponse> getInfo() {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        List<AccountEntity> accountEntities = accountRepository.findByUserIdAndStatus(userInfoDto.getUserId(), Contants.ACTIVE);
        if (CollectionUtils.isEmpty(accountEntities)) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return accountMapper.mapListAccountResponse(accountEntities);
    }

//    @Override
//    public List<UserResponse> findAll() {
//        List<UserEntity> userEntities = userRepository.findAll();
//        return userEntities.stream()
//                .map(customerMapper::mapUserEntityToUserResponse).collect(Collectors.toList());
//    }
//
//    @Override
//    public UserResponse findById(String id) {
//        Optional<UserEntity> userEntity = userRepository.findById(id);
//        if (!userEntity.isPresent()) {
//            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//        return customerMapper.mapUserEntityToUserResponse(userEntity.get());
//    }
//
//
//
//    @Override
//    public ResStatus save(UserRequest userRequest) {
//        UserEntity userEntity;
//        customerValidate.validateRequestUser(userRequest);
//        try {
//            if (StringUtils.isBlank(userRequest.getId())) {
//                userEntity = customerMapper.mapUserRequestToUserEntity(userRequest);
//            } else {
//                Optional<UserEntity> userEntityOld = userRepository.findById(userRequest.getId());
//                if (!userEntityOld.isPresent()) {
//                    throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
//                }
//                userEntity = customerMapper.mapUserRequestToUserEntity(userRequest, userEntityOld.get());
//            }
//            userRepository.save(userEntity);
//        } catch (Exception e) {
//            logger.error("CustomerServiceImpl create error: {}", e.getMessage());
//            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//        return ResponseEntity.ok("SUCCESS");
//    }
//
//    @Override
//    public UserResponse getInfo() {
//        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
//        Optional<UserEntity> userEntity = userRepository.findById(userInfoDto.getUserId());
//        if (!userEntity.isPresent()) {
//            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//        return customerMapper.mapUserEntityToUserResponse(userEntity.get());
//    }


}
