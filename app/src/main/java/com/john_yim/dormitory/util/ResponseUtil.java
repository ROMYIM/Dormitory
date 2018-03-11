package com.john_yim.dormitory.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.john_yim.dormitory.entity.ResponseResult;

import java.lang.reflect.Type;

/**
 * Created by MSI-PC on 2018/2/15.
 */

public class ResponseUtil {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();

    public static <T> ResponseResult<T> getResult(String responseString, Type type) throws JsonParseException {
        ResponseResult<T> responseResult = gson.fromJson(responseString, type);
        return responseResult;
    }

    public static <T> ResponseResult<T> getResult(String responseString, Class<ResponseResult<T>> resultClass)
        throws JsonParseException {
        ResponseResult<T> responseResult = gson.fromJson(responseString, resultClass);
        return responseResult;
    }

    public static <T> ResponseResult<T> getResult(String responseString, Type type, Gson gson) throws JsonParseException {
        ResponseResult<T> responseResult = gson.fromJson(responseString, type);
        return responseResult;
    }

}
