package com.example.dbsservice.utils.validate;

import com.example.dbsservice.model.dto.UserInfoDto;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class ConvertUtils {
    private static final Gson gson = new Gson();

    // Chuyển đối tượng thành chuỗi JSON
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    // Chuyển từ chuỗi JSON thành đối tượng
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
