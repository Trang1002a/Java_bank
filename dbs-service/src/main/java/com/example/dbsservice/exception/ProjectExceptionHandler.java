package com.example.dbsservice.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ProjectExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ProjectExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handlerException(Throwable ex, HttpServletRequest request) {
        if (ex instanceof ProjectException) {
            MessageException exception = ((ProjectException) ex).getMessageException();
            logger.warn("Exception: {}, Message: {}", ex.getClass().getName(), ex.getMessage());
            //todo
            if (StringUtils.equalsIgnoreCase(exception.getErrorCode(), "CLV-TIMEOUT")) {
                return new ResponseEntity<>(exception, HttpStatus.GATEWAY_TIMEOUT);
            }
            return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.error("Process with an error: {}", ex.getMessage());
        return new ResponseEntity<>(new MessageException().builder()
        .errorCode("500")
        .message(new Message("", ex.getMessage()))
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
