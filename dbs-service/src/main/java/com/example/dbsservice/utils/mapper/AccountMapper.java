package com.example.dbsservice.utils.mapper;

import com.example.dbsservice.jwt.PasswordService;
import com.example.dbsservice.model.entity.AccountEntity;
import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.request.account.CheckNameRequest;
import com.example.dbsservice.model.request.customer.UserRequest;
import com.example.dbsservice.model.response.account.AccountResponse;
import com.example.dbsservice.model.response.customer.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public AccountResponse mapToAccountResponse(AccountEntity request) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountName(request.getAccountName());
        accountResponse.setAccountNumber(request.getAccountNumber());
        return accountResponse;
    }

    public List<AccountResponse> mapListAccountResponse(List<AccountEntity> accountEntities) {
        return accountEntities.stream().map(this::mapAccountResponse).collect(Collectors.toList());
    }

    public AccountResponse mapAccountResponse(AccountEntity accountEntity) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(accountEntity.getId());
        accountResponse.setAccountName(accountEntity.getAccountName());
        accountResponse.setAccountNumber(accountEntity.getAccountNumber());
        accountResponse.setCif(accountEntity.getCif());
        accountResponse.setAmount(accountEntity.getAmount());
        accountResponse.setStatus(accountEntity.getStatus());
        accountResponse.setUserId(accountEntity.getUserId());
        accountResponse.setCreatedDate(accountEntity.getCreatedDate());
        accountResponse.setUpdatedDate(accountEntity.getUpdatedDate());
        return accountResponse;
    }
}
