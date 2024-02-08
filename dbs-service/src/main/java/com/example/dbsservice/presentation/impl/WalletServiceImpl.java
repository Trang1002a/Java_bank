package com.example.dbsservice.presentation.impl;

import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.model.dto.UserInfoDto;
import com.example.dbsservice.model.entity.WalletEntity;
import com.example.dbsservice.model.entity.WalletLinkEntity;
import com.example.dbsservice.model.repository.WalletLinkRepository;
import com.example.dbsservice.model.repository.WalletRepository;
import com.example.dbsservice.model.request.wallet.WalletLinkRequest;
import com.example.dbsservice.model.request.wallet.WalletRechargeRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.model.response.wallet.WalletUserResponse;
import com.example.dbsservice.presentation.service.WalletService;
import com.example.dbsservice.utils.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    @Resource
    WalletRepository walletRepository;

    @Resource
    WalletLinkRepository walletLinkRepository;

    @Resource
    RequestUtils requestUtils;

    @Override
    public List<WalletEntity> getAllWallet() {
        List<WalletEntity> walletEntities = walletRepository.findAllByStatus(Contants.ACTIVE);
        if (CollectionUtils.isEmpty(walletEntities)) {
            return new ArrayList<>();
        }
        return walletEntities;
    }

    @Override
    public ResStatus create(WalletEntity walletEntity) {
        if (StringUtils.isBlank(walletEntity.getWalletName()) || StringUtils.isBlank(walletEntity.getImageUrl())) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR, "Request không hợp lệ");
        }
        WalletEntity walletEntityRe = new WalletEntity();
        walletEntityRe.setId(UUID.randomUUID().toString());
        walletEntityRe.setWalletName(walletEntity.getWalletName());
        walletEntityRe.setImageUrl(walletEntity.getImageUrl());
        walletEntityRe.setStatus(Contants.ACTIVE);
        walletEntityRe.setCreatedDate(new Date(System.currentTimeMillis()));
        walletEntityRe.setUpdatedDate(new Date(System.currentTimeMillis()));
        walletRepository.save(walletEntityRe);
        return new ResStatus(Contants.SUCCESS);
    }

    @Override
    public CreateTransResponse eWalletLink(WalletLinkRequest walletLinkRequest) {
        if (StringUtils.isBlank(walletLinkRequest.getWalletId()) || StringUtils.isBlank(walletLinkRequest.getRequestType())
                || StringUtils.isBlank(walletLinkRequest.getAccountName()) || StringUtils.isBlank(walletLinkRequest.getAccountNumber())) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR, "Request không hợp lệ");
        }
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        return requestUtils.responseCreateTrans(userInfoDto, FunctionCode.E_WALLET_LINK, walletLinkRequest);
    }

    @Override
    public List<WalletLinkEntity> getEWalletUserRegisted() {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        List<WalletLinkEntity> linkEntities = walletLinkRepository.findAllByUserIdAndStatus(userInfoDto.getUserId(), Contants.ACTIVE);
        if (CollectionUtils.isEmpty(linkEntities)) {
            return new ArrayList<>();
        }
        return linkEntities;
    }

    @Override
    public List<WalletUserResponse> getAllEWalletUser() {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        List<WalletEntity> walletEntities = walletRepository.findAllByStatus(Contants.ACTIVE);
        if (CollectionUtils.isEmpty(walletEntities)) {
            return new ArrayList<>();
        }
        List<String> walletIds = walletEntities.stream().map(WalletEntity::getId).collect(Collectors.toList());
        List<WalletLinkEntity> linkEntities = walletLinkRepository.findAllByUserIdAndStatusAndWalletIdIn(userInfoDto.getUserId(), Contants.ACTIVE, walletIds);
        if (CollectionUtils.isEmpty(linkEntities)) {
            return new ArrayList<>();
        }
        return walletEntities.stream().map(walletEntity -> {
            WalletUserResponse walletUserResponse = new WalletUserResponse();
            walletUserResponse.setWalletId(walletEntity.getId());
            walletUserResponse.setWalletName(walletEntity.getWalletName());
            walletUserResponse.setImageUrl(walletEntity.getImageUrl());
            walletUserResponse.setStatus(RequestType.UNLINK.name());
            WalletLinkEntity walletLinkEntity = linkEntities.stream().filter(w -> StringUtils.equalsIgnoreCase(w.getWalletId(), walletEntity.getId()))
                    .findFirst().orElse(null);
            if (ObjectUtils.isNotEmpty(walletLinkEntity)) {
                walletUserResponse.setAccountNumber(walletLinkEntity.getAccountNumber());
                walletUserResponse.setAccountName(walletLinkEntity.getAccountName());
                walletUserResponse.setStatus(RequestType.LINK.name());
            }
            return walletUserResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public CreateTransResponse walletRecharge(WalletRechargeRequest request) {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        return requestUtils.responseCreateTrans(userInfoDto, FunctionCode.E_WALLET_RECHARGE, request);
    }
}
