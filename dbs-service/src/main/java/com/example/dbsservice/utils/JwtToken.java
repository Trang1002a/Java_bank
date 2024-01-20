package com.example.dbsservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.dbsservice.configuration.TokenConfig;
import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.token.DataToken;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken {
    public String generateToken(TokenConfig tokenConfig, UserEntity userEntity) {
        Algorithm algorithm = Algorithm.HMAC256(tokenConfig.getSecret());
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        long expiredDate = Long.parseLong(System.currentTimeMillis() + tokenConfig.getExpriedTime());
        String token = JWT.create()
                .withHeader(header)
                .withExpiresAt(new Date(expiredDate))
                .withClaim(Contants.USER_ID, userEntity.getId())
                .withClaim(Contants.USER_NAME, userEntity.getUserName())
                .withClaim(Contants.PHONE_NUMBER, userEntity.getPhoneNumber())
                .sign(algorithm);
        return DataToken.encrypt(token, tokenConfig.getSecret());
    }
}
