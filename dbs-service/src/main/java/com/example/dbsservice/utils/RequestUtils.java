package com.example.dbsservice.utils;

import com.example.dbsservice.model.dto.UserInfoDto;
import com.example.dbsservice.model.entity.RequestEntity;
import com.example.dbsservice.model.repository.RequestRepository;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.utils.validate.ConvertUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Component
public class RequestUtils {
    @Resource
    RequestRepository requestRepository;

    public CreateTransResponse responseCreateTrans(UserInfoDto userInfoDto, FunctionCode functionCode, Object data) {
        RequestEntity requestEntity = saveRequest(data, userInfoDto, functionCode);
        CreateTransResponse createTransResponse = new CreateTransResponse();
        createTransResponse.setRequestId(requestEntity.getId());
        createTransResponse.setOtpId(requestEntity.getOtpId());
        createTransResponse.setPhoneNumber(UserInfoService.maskPhoneNumber(userInfoDto.getPhoneNumber()));
        return createTransResponse;
    }

    private RequestEntity saveRequest(Object data, UserInfoDto userInfoDto, FunctionCode functionCode) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setId(UUID.randomUUID().toString());
        requestEntity.setUserId(userInfoDto.getUserId());
        requestEntity.setRequestType(functionCode.name());
        requestEntity.setRequestBody(ConvertUtils.toJson(data));
        requestEntity.setStatus(TransactionStatus.DRAF.name());
        requestEntity.setOtpId(UUID.randomUUID().toString());
        requestEntity.setCreatedDate(new Date(System.currentTimeMillis()));
        requestEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        requestRepository.save(requestEntity);
        return requestEntity;
    }
}
