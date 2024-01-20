package com.example.dbsservice.utils;

import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.model.dto.UserInfoDto;
import com.example.dbsservice.utils.validate.ConvertUtils;
import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserInfoService {
    public static UserInfoDto getUserInfo() {
        UserInfoDto userInfoDto = new UserInfoDto();
        // Lấy SecurityContext từ bối cảnh hiện tại
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // Lấy Authentication từ SecurityContext
        Authentication authentication = securityContext.getAuthentication();

        // Truy xuất thông tin người dùng từ Authentication
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy đối tượng UserDetails từ Principal
            String tokenJson = authentication.getName();
            userInfoDto = ConvertUtils.fromJson(tokenJson, UserInfoDto.class);
            // Truy xuất thông tin người dùng từ UserDetails
            String userId = userInfoDto.getUserId();
            String userName = userInfoDto.getUserName();
            String phoneNumber = userInfoDto.getPhoneNumber();
            userInfoDto.setUserId(userId);
            userInfoDto.setUserName(userName);
            // Truy xuất các thông tin khác từ UserDetails
            // ...
            return userInfoDto;
        }
        throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public static String maskPhoneNumber(String phoneNumber) {
        // Kiểm tra xem số điện thoại có ít nhất 6 ký tự hay không
        if (phoneNumber != null && phoneNumber.length() >= 6) {
            // Lấy 6 ký tự đầu tiên
            String prefix = phoneNumber.substring(0, 6);

            // Tạo chuỗi mask với số '*' có độ dài bằng với prefix
            StringBuilder maskBuilder = new StringBuilder();
            for (int i = 0; i < prefix.length(); i++) {
                maskBuilder.append("*");
            }
            String mask = maskBuilder.toString();

            // Thay thế 6 số đầu bằng chuỗi mask
            return phoneNumber.replaceFirst(prefix, mask);
        } else {
            // Trả về số điện thoại không thay đổi nếu không đủ ký tự
            return phoneNumber;
        }
    }
}
