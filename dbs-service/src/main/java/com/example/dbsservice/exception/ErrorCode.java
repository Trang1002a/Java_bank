package com.example.dbsservice.exception;

public interface ErrorCode {
    String ERROR_MESSAGE_DEFAULT_VN = "Đã có lỗi xảy ra trong quá trình kết nối. Quý khách vui lòng thử lại sau";
    String ERROR_MESSAGE_DEFAULT_EN = "An error occurred during the connection. Please try again later";
    String INTERNAL_SERVER_ERROR = "CLV-00-000";
    String USERNAME_CANNOT_BE_EMPTY = "CLV-00-001";
    String PASSWORD_CANNOT_BE_EMPTY = "CLV-00-002";
    String INCORRECT_ACCOUNT_OR_PASSWORD = "CLV-00-003";
    String EXIST_USER_WITH_PHONENUMBER = "CLV-00-004";
    String EXIST_USER_WITH_USERNAME = "CLV-00-005";
    String EXIST_USER_WITH_EMAIL = "CLV-00-006";
    String EXIST_USER_WITH_CARDNUMBER = "CLV-00-007";
}
