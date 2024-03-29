package com.example.dbsservice.presentation.impl;

import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.jwt.PasswordService;
import com.example.dbsservice.model.dto.UserInfoDto;
import com.example.dbsservice.model.entity.*;
import com.example.dbsservice.model.repository.*;
import com.example.dbsservice.model.request.account.CheckNameRequest;
import com.example.dbsservice.model.request.customer.ChangePassword;
import com.example.dbsservice.model.request.transaction.ConfirmRequest;
import com.example.dbsservice.model.request.transaction.CreateTransRequest;
import com.example.dbsservice.model.request.transaction.PhoneRechargeRequest;
import com.example.dbsservice.model.request.wallet.WalletLinkRequest;
import com.example.dbsservice.model.request.wallet.WalletRechargeRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.account.AccountResponse;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.presentation.service.AccountService;
import com.example.dbsservice.presentation.service.TransactionService;
import com.example.dbsservice.utils.*;
import com.example.dbsservice.utils.validate.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Resource
    AccountRepository accountRepository;

    @Resource
    RequestRepository requestRepository;

    @Resource
    TransactionRepository transactionRepository;

    @Resource
    AccountService accountService;

    @Resource
    UserRepository userRepository;

    @Resource
    RequestUtils requestUtils;

    @Resource
    WalletLinkRepository walletLinkRepository;


    @Override
    public CreateTransResponse createTrans(CreateTransRequest request) {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        validateRequestCreateTrans(request);
        List<AccountEntity> accountEntities = accountRepository.findByUserIdAndStatus(userInfoDto.getUserId(), Contants.ACTIVE);
        if (CollectionUtils.isEmpty(accountEntities)) {
            throw new ProjectException("", "Tài khoản nguồn không đúng");
        }
        AccountEntity a = accountEntities.stream().filter(accountEntity -> StringUtils.equalsIgnoreCase(accountEntity.getAccountNumber(), request.getSourceAccount()))
                .findFirst().orElse(null);
        if (Objects.isNull(a)) {
            throw new ProjectException("", "Tài khoản nguồn không đúng");
        }
        try {
            BigDecimal amountRequest = new BigDecimal(request.getAmount());
            if (a.getAmount().compareTo(amountRequest) < 0) {
                throw new ProjectException("", "Số tiền không hợp lệ");
            }
        } catch (Exception e) {
            throw new ProjectException("", "Số tiền không hợp lệ");
        }
        CheckNameRequest checkNameRequest = new CheckNameRequest();
        checkNameRequest.setAccountNumber(request.getDebtorAccountNumber());
        checkNameRequest.setType(Contants.INTERNAL);
        AccountResponse accountResponse = accountService.checkName(checkNameRequest);
        if (Objects.isNull(accountResponse)) {
            throw new ProjectException("", "Tài khoản nhận không hợp lệ");
        }
        if (!StringUtils.equalsAnyIgnoreCase(accountResponse.getAccountName(), request.getDebtorAccountName())) {
            throw new ProjectException("", "Tài khoản nhận không hợp lệ");
        }
        RequestEntity requestEntity = saveRequest(request, userInfoDto);
        CreateTransResponse createTransResponse = new CreateTransResponse();
        createTransResponse.setRequestId(requestEntity.getId());
        createTransResponse.setOtpId(requestEntity.getOtpId());
        createTransResponse.setPhoneNumber(UserInfoService.maskPhoneNumber(userInfoDto.getPhoneNumber()));
        return createTransResponse;
    }

    @Override
    @Transactional
    public ResStatus confirm(ConfirmRequest request) {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        validatConfirmRequest(request);
        Optional<RequestEntity> requestEntityDB = requestRepository.findByIdAndStatus(request.getRequestId(), TransactionStatus.DRAF.name());
        if (!requestEntityDB.isPresent()) {
            throw new ProjectException("", "Thông tin request không hợp lệ");
        }
        if (!StringUtils.equalsAnyIgnoreCase(request.getOtp(), Contants.OTP)) {
            throw new ProjectException("", "Mã OTP không đúng");
        }
        RequestEntity requestEntity = requestEntityDB.get();
        try {
            FunctionCode functionCode = FunctionCode.valueOf(requestEntity.getRequestType());
            switch (functionCode) {
                case FT_INTERNAL:
                    processInternal(userInfoDto, requestEntity);
                    break;
                case CHANGE_PASSWORD:
                    processChangePassword(userInfoDto, requestEntity);
                    break;
                case PHONE_RECHARGE:
                    processPhoneRecharge(userInfoDto, requestEntity);
                    break;
                case E_WALLET_LINK:
                    processEWalletLink(userInfoDto, requestEntity);
                    break;
                case E_WALLET_RECHARGE:
                    processEWalletRecharge(userInfoDto, requestEntity);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logger.error("confirm error: {}", e.getMessage());
            requestEntity.setStatus(TransactionStatus.FAILED.name());
            requestRepository.save(requestEntity);
        }
        return new ResStatus(Contants.SUCCESS);
    }

    private void processEWalletRecharge(UserInfoDto userInfoDto, RequestEntity requestEntity) {
        WalletRechargeRequest rqBody = ConvertUtils.fromJson(requestEntity.getRequestBody(), WalletRechargeRequest.class);
        Optional<AccountEntity> accountEntity = accountRepository.findByAccountNumberAndStatus(rqBody.getSourceAccount(), Contants.ACTIVE);
        if (!accountEntity.isPresent()) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.randomUUID().toString());
        transactionEntity.setAmount(rqBody.getAmount());
        transactionEntity.setSourceAccount(rqBody.getSourceAccount());
        transactionEntity.setStatus(Contants.ACTIVE);
        transactionEntity.setUserId(userInfoDto.getUserId());
        transactionEntity.setUserName(userInfoDto.getUserName());
        transactionEntity.setRequestType(FunctionCode.E_WALLET_RECHARGE.name());
        transactionEntity.setDescription("Nap tien vi dien tu");
        transactionEntity.setCreatedDate(new Date(System.currentTimeMillis()));
        transactionEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        AccountEntity acc = accountEntity.get();
        BigDecimal amount = acc.getAmount();
        BigDecimal amoutRecharge = new BigDecimal(rqBody.getAmount());
        BigDecimal result = amount.subtract(amoutRecharge);
        acc.setAmount(result);
        accountRepository.save(acc);
        requestEntity.setStatus(TransactionStatus.SUCCESS.name());
        requestRepository.save(requestEntity);
        transactionRepository.save(transactionEntity);
    }

    private void processEWalletLink(UserInfoDto userInfoDto, RequestEntity requestEntity) {
        WalletLinkRequest rqBody = ConvertUtils.fromJson(requestEntity.getRequestBody(), WalletLinkRequest.class);
        String status = StringUtils.equalsIgnoreCase(rqBody.getRequestType(), RequestType.LINK.name()) ? Contants.ACTIVE : Contants.DEACTIVE;
        Optional<WalletLinkEntity> walletLinkEntity = walletLinkRepository.findAllByWalletIdAndAccountNumber(rqBody.getWalletId(), rqBody.getAccountNumber());
        if (!walletLinkEntity.isPresent()) {
            WalletLinkEntity linkEntity = new WalletLinkEntity();
            linkEntity.setId(UUID.randomUUID().toString());
            linkEntity.setUserId(userInfoDto.getUserId());
            linkEntity.setAccountNumber(rqBody.getAccountNumber());
            linkEntity.setAccountName(rqBody.getAccountName());
            linkEntity.setWalletId(rqBody.getWalletId());
            linkEntity.setStatus(status);
            linkEntity.setCreatedDate(new Date(System.currentTimeMillis()));
            linkEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
            walletLinkRepository.save(linkEntity);
        } else {
            walletLinkEntity.get().setStatus(status);
            walletLinkEntity.get().setUpdatedDate(new Date(System.currentTimeMillis()));
            walletLinkRepository.save(walletLinkEntity.get());
        }
    }

    private void processPhoneRecharge(UserInfoDto userInfoDto, RequestEntity requestEntity) {
        PhoneRechargeRequest rqBody = ConvertUtils.fromJson(requestEntity.getRequestBody(), PhoneRechargeRequest.class);
        Optional<AccountEntity> accountEntity = accountRepository.findByAccountNumberAndStatus(rqBody.getSelectedAccount(), Contants.ACTIVE);
        if (!accountEntity.isPresent()) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.randomUUID().toString());
        transactionEntity.setAmount(rqBody.getDenomination());
        transactionEntity.setSourceAccount(rqBody.getSelectedAccount());
        transactionEntity.setStatus(Contants.ACTIVE);
        transactionEntity.setUserId(userInfoDto.getUserId());
        transactionEntity.setUserName(userInfoDto.getUserName());
        transactionEntity.setRequestType(FunctionCode.PHONE_RECHARGE.name());
        transactionEntity.setDescription("Nap the dien thoai");
        transactionEntity.setCreatedDate(new Date(System.currentTimeMillis()));
        transactionEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        AccountEntity acc = accountEntity.get();
        BigDecimal amount = acc.getAmount();
        BigDecimal amoutRecharge = new BigDecimal(rqBody.getDenomination());
        BigDecimal result = amount.subtract(amoutRecharge);
        acc.setAmount(result);
        accountRepository.save(acc);
        requestEntity.setStatus(TransactionStatus.SUCCESS.name());
        requestRepository.save(requestEntity);
        transactionRepository.save(transactionEntity);
    }

    private void processChangePassword(UserInfoDto userInfoDto, RequestEntity requestEntity) {
        ChangePassword rqBody = ConvertUtils.fromJson(requestEntity.getRequestBody(), ChangePassword.class);
        String passwordNew = PasswordService.encodePassword(rqBody.getNewPassword());
        Optional<UserEntity> userEntity = userRepository.findById(userInfoDto.getUserId());
        if (!userEntity.isPresent()) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        userEntity.get().setPassword(passwordNew);
        requestEntity.setStatus(TransactionStatus.SUCCESS.name());
        userRepository.save(userEntity.get());
        requestRepository.save(requestEntity);
    }

    private void processInternal(UserInfoDto userInfoDto, RequestEntity requestEntity) {
        TransactionEntity transactionEntity = saveTransaction(userInfoDto, requestEntity);
        List<String> accountNumbers = Arrays.asList(transactionEntity.getSourceAccount(), transactionEntity.getDebtorAccountNumber());
        List<AccountEntity> accountEntities = accountRepository.findByAccountNumberInAndStatus(accountNumbers, Contants.ACTIVE);
        if (accountEntities.size() != 2) {
            logger.error("accountEntities size: {}", accountEntities.size());
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        List<AccountEntity> list = accountEntities.stream().peek(accountEntity -> {
            BigDecimal amount = accountEntity.getAmount();
            if (StringUtils.equalsIgnoreCase(accountEntity.getAccountNumber(), transactionEntity.getSourceAccount())) {
                amount = accountEntity.getAmount().subtract(new BigDecimal(transactionEntity.getAmount()));
            } else if (StringUtils.equalsIgnoreCase(accountEntity.getAccountNumber(), transactionEntity.getDebtorAccountNumber())) {
                amount = accountEntity.getAmount().add(new BigDecimal(transactionEntity.getAmount()));
            }
            accountEntity.setAmount(amount);
        }).collect(Collectors.toList());
        transactionEntity.setStatus(TransactionStatus.SUCCESS.name());
        requestEntity.setStatus(TransactionStatus.SUCCESS.name());
        accountRepository.saveAll(list);
        transactionRepository.save(transactionEntity);
        requestRepository.save(requestEntity);
    }

    @Override
    public List<TransactionEntity> getAllTransaction(String sourceAccount) {
        if (StringUtils.isBlank(sourceAccount)) {
            throw new ProjectException("Tài khoản không hợp lệ");
        }
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        List<AccountEntity> accountEntities = accountRepository.findByUserIdAndStatus(userInfoDto.getUserId(), Contants.ACTIVE);
        if (CollectionUtils.isEmpty(accountEntities)) {
            throw new ProjectException("Tài khoản không hợp lệ");
        }
        AccountEntity accountEntity = accountEntities.stream()
                .filter(a -> StringUtils.equalsIgnoreCase(a.getAccountNumber(), sourceAccount))
                .findFirst().orElse(null);
        if (Objects.isNull(accountEntity)) {
            throw new ProjectException("Tài khoản không hợp lệ");
        }
        List<TransactionEntity> list;
        list = transactionRepository.findBySourceAccountOrDebtorAccountNumberOrderByCreatedDateDesc(sourceAccount, sourceAccount);
        if (!CollectionUtils.isEmpty(list)) {
            list = list.stream().peek(transactionEntity -> {
                transactionEntity.setAmount(StringUtils.replace(transactionEntity.getAmount(), ".00", ""));
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public CreateTransResponse phoneRecharge(PhoneRechargeRequest request) {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        return requestUtils.responseCreateTrans(userInfoDto, FunctionCode.PHONE_RECHARGE, request);
    }

    @Override
    public List<RequestEntity> getAllRequest() {
        UserInfoDto userInfoDto = UserInfoService.getUserInfo();
        List<RequestEntity> list = requestRepository.findByUserIdAndStatusOrderByCreatedDateDesc(userInfoDto.getUserId(), TransactionStatus.SUCCESS.name());
        return list;
    }

    private TransactionEntity saveTransaction(UserInfoDto userInfoDto, RequestEntity requestEntity) {
        CreateTransRequest rqBody = ConvertUtils.fromJson(requestEntity.getRequestBody(), CreateTransRequest.class);
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(UUID.randomUUID().toString());
        transactionEntity.setUserId(userInfoDto.getUserId());
        transactionEntity.setUserName(userInfoDto.getUserName());
        transactionEntity.setRequestType(FunctionCode.FT_INTERNAL.name());
        transactionEntity.setSourceAccount(rqBody.getSourceAccount());
        transactionEntity.setAmount(rqBody.getAmount());
        transactionEntity.setDebtorAccountNumber(rqBody.getDebtorAccountNumber());
        transactionEntity.setDebtorAccountName(rqBody.getDebtorAccountName());
        transactionEntity.setDescription(rqBody.getDescription());
        transactionEntity.setStatus(TransactionStatus.DRAF.name());
        transactionEntity.setCreatedDate(new Date(System.currentTimeMillis()));
        transactionEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        transactionRepository.save(transactionEntity);
        return transactionEntity;
    }

    private void validatConfirmRequest(ConfirmRequest request) {
        if (StringUtils.isBlank(request.getRequestId()) || StringUtils.isBlank(request.getOtp()) ||
                StringUtils.isBlank(request.getOtpId())) {
            throw new ProjectException("", "Thông tin request không hợp lệ");
        }
    }

    private RequestEntity saveRequest(CreateTransRequest request, UserInfoDto userInfoDto) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setId(UUID.randomUUID().toString());
        requestEntity.setUserId(userInfoDto.getUserId());
        requestEntity.setRequestType(FunctionCode.FT_INTERNAL.name());
        requestEntity.setRequestBody(ConvertUtils.toJson(request));
        requestEntity.setStatus(TransactionStatus.DRAF.name());
        requestEntity.setOtpId(UUID.randomUUID().toString());
        requestEntity.setCreatedDate(new Date(System.currentTimeMillis()));
        requestEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        requestRepository.save(requestEntity);
        return requestEntity;
    }

    private void validateRequestCreateTrans(CreateTransRequest request) {
        if (StringUtils.isBlank(request.getSourceAccount()) || StringUtils.isBlank(request.getAmount()) ||
                StringUtils.isBlank(request.getDebtorAccountName()) || StringUtils.isBlank(request.getDebtorAccountNumber()) ||
                StringUtils.isBlank(request.getDescription())) {
            throw new ProjectException("", "Thông tin request không hợp lệ");
        }
    }
}
