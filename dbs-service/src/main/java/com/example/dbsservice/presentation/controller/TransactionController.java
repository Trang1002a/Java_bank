package com.example.dbsservice.presentation.controller;

import com.example.dbsservice.model.entity.TransactionEntity;
import com.example.dbsservice.model.request.transaction.ConfirmRequest;
import com.example.dbsservice.model.request.transaction.CreateTransRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.customer.UserResponse;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.model.response.transaction.TransResponse;
import com.example.dbsservice.presentation.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Resource
    private TransactionService transactionService;

//    @GetMapping("/findAll")
//    public List<UserResponse> findAll() {
//        return userService.findAll();
//    }
//
//    @GetMapping("/user/{id}")
//    public UserResponse findById(@PathVariable("id") String id) {
//        return userService.findById(id);
//    }
//    @GetMapping("/info")
//    public UserResponse getInfo() {
//        return userService.getInfo();
//    }
//
    @PostMapping("/create")
    public CreateTransResponse create(@RequestBody CreateTransRequest request) {
        return transactionService.createTrans(request);
    }

//    @PostMapping("/create")
//    public CreateTransResponse create(@RequestBody CreateTransRequest request) {
//        return transactionService.createTrans(request);
//    }

    @PostMapping("/confirm")
    public ResStatus confirm(@RequestBody ConfirmRequest request) {
        return transactionService.confirm(request);
    }

    @GetMapping("/{sourceAccount}")
    public List<TransactionEntity> getAllTransaction(@PathVariable("sourceAccount") String sourceAccount) {
        return transactionService.getAllTransaction(sourceAccount);
    }

//    @PostMapping("/checkNameInternal")
//    public AccountResponse checkNameInternal(@RequestBody CheckNameRequest request) {
//        return accountService.checkName(request);
//    }
//
//    @PutMapping("/update")
//    public ResStatus update(@RequestBody UserRequest userRequest) {
//        return userService.save(userRequest);
//    }
}
