package com.example.dbsservice.presentation.service;

import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.entity.WalletEntity;
import com.example.dbsservice.model.entity.WalletLinkEntity;
import com.example.dbsservice.model.request.account.CheckNameRequest;
import com.example.dbsservice.model.request.wallet.WalletLinkRequest;
import com.example.dbsservice.model.request.wallet.WalletRechargeRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.account.AccountResponse;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.model.response.wallet.WalletUserResponse;

import java.util.List;

public interface WalletService {

    List<WalletEntity> getAllWallet();

    ResStatus create(WalletEntity walletEntity);

    CreateTransResponse eWalletLink(WalletLinkRequest walletLinkRequest);

    List<WalletLinkEntity> getEWalletUserRegisted();

    List<WalletUserResponse> getAllEWalletUser();

    CreateTransResponse walletRecharge(WalletRechargeRequest request);
}
