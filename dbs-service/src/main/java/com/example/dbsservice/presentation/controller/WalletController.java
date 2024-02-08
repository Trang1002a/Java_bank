package com.example.dbsservice.presentation.controller;

import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.entity.WalletEntity;
import com.example.dbsservice.model.entity.WalletLinkEntity;
import com.example.dbsservice.model.request.account.CheckNameRequest;
import com.example.dbsservice.model.request.transaction.PhoneRechargeRequest;
import com.example.dbsservice.model.request.wallet.WalletLinkRequest;
import com.example.dbsservice.model.request.wallet.WalletRechargeRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.account.AccountResponse;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.model.response.wallet.WalletUserResponse;
import com.example.dbsservice.presentation.service.AccountService;
import com.example.dbsservice.presentation.service.WalletService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Resource
    private WalletService walletService;

    @GetMapping("/getAll")
    public List<WalletEntity> getAllWallet() {
        return walletService.getAllWallet();
    }

    @GetMapping("/getEWalletUserRegisted")
    public List<WalletLinkEntity> getEWalletUserRegisted() {
        return walletService.getEWalletUserRegisted();
    }

    @GetMapping("/getAllEWalletUser")
    public List<WalletUserResponse> getAllEWalletUser() {
        return walletService.getAllEWalletUser();
    }

    @PostMapping("/createWallet")
    public ResStatus createWallet(@RequestBody WalletEntity walletEntity) {
        return walletService.create(walletEntity);
    }

    @PostMapping("/eWalletLink")
    public CreateTransResponse eWalletLink(@RequestBody WalletLinkRequest walletLinkRequest) {
        return walletService.eWalletLink(walletLinkRequest);
    }
    @PostMapping("/walletRecharge")
    public CreateTransResponse walletRecharge(@RequestBody WalletRechargeRequest request) {
        return walletService.walletRecharge(request);
    }

}
