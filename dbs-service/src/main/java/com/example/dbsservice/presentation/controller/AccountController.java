package com.example.dbsservice.presentation.controller;

import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.request.account.CheckNameRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.account.AccountResponse;
import com.example.dbsservice.presentation.service.AccountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService accountService;

    @GetMapping("/info")
    public List<AccountResponse> getInfo() {
        return accountService.getInfo();
    }

    @PostMapping("/create")
    public ResStatus create(@RequestBody UserEntity userEntity) {
        return accountService.save(userEntity);
    }

    @PostMapping("/checkNameInternal")
    public AccountResponse checkNameInternal(@RequestBody CheckNameRequest request) {
        return accountService.checkName(request);
    }
//
//    @PutMapping("/update")
//    public ResStatus update(@RequestBody UserRequest userRequest) {
//        return userService.save(userRequest);
//    }
}
