package com.example.dbsservice.exception;

import com.example.dbsservice.model.entity.ExceptionEntity;
import com.example.dbsservice.model.repository.ExceptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MessageLoader {

    @Resource
    ExceptionRepository exceptionRepository;

    private static final Logger logger = LoggerFactory.getLogger(MessageLoader.class);

    private static Map<String, Message> errorMessageMap = new HashMap<>();

    private static final Message DEFAULT_MSG = new Message(ErrorCode.ERROR_MESSAGE_DEFAULT_VN, ErrorCode.ERROR_MESSAGE_DEFAULT_EN);

    @Scheduled(initialDelay = 1000, fixedDelay = 3600000)
    public void loadExceptionMessage() {
        if (CollectionUtils.isEmpty(errorMessageMap)) {
            logger.info("call exceptionRepository get Message Error");
            List<ExceptionEntity> list =  exceptionRepository.findAll();
            errorMessageMap = list.stream().collect(Collectors.toMap(ExceptionEntity::getErrorCode,
                    e-> new Message(e.getMessageVn(), e.getMessageVn())));
        }
    }

    public static Message getMessage(String errorCode) {
        try {
            Message msg = errorMessageMap.get(errorCode);
            if (Objects.isNull(msg)) {
                logger.warn("Not found error message with error code: " + errorCode);
                return DEFAULT_MSG;
            }
            return msg;
        } catch (Exception e) {
            logger.error("get message error failed: {}", e.getMessage());
        }
        return DEFAULT_MSG;
    }

}
