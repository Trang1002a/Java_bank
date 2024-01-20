package com.example.dbsservice.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.dbsservice.exception.ErrorCode;
import com.example.dbsservice.exception.ProjectException;
import com.example.dbsservice.model.dto.UserInfoDto;
import com.example.dbsservice.token.DataToken;
import com.example.dbsservice.utils.Contants;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Component
public class TokenValidator {
    public UserInfoDto verifyToken(String token, String secret) {
        try {
            token = DataToken.decrypt(token, secret);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            assert token != null;
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Date date = decodedJWT.getExpiresAt();
            if (ObjectUtils.isEmpty(date) || date.before(new Date())) {
                throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
            return getUserInfo(decodedJWT);
        } catch (Exception e) {
            throw new ProjectException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private UserInfoDto getUserInfo(DecodedJWT decodedJWT) {
        UserInfoDto userInfoDto = new UserInfoDto();
        String userId = decodedJWT.getClaim(Contants.USER_ID).asString();
        String userName = decodedJWT.getClaim(Contants.USER_NAME).asString();
        String phoneNumber = decodedJWT.getClaim(Contants.PHONE_NUMBER).asString();
        userInfoDto.setUserId(userId);
        userInfoDto.setUserName(userName);
        userInfoDto.setPhoneNumber(phoneNumber);
        return userInfoDto;
    }
}
