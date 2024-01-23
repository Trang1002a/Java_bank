package com.example.dbsservice.presentation.controller;

import com.example.dbsservice.model.request.customer.ChangePassword;
import com.example.dbsservice.model.request.customer.UserRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.customer.UserResponse;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.presentation.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService userService;

    @GetMapping("/findAll")
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public UserResponse findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }
    
    @GetMapping("/info")
    public UserResponse getInfo() {
        return userService.getInfo();
    }

    @PostMapping("/create")
    public ResStatus create(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @PutMapping("/update")
    public ResStatus update(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @PostMapping("/changePassword")
    public CreateTransResponse changePassword(@RequestBody ChangePassword changePassword) {
        return userService.changePassword(changePassword);
    }
}
