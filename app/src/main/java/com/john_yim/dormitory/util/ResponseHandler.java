package com.john_yim.dormitory.util;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.entity.ResponseResult;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by MSI-PC on 2018/2/25.
 */

public class ResponseHandler<T> extends TextHttpResponseHandler {

    private ResponseResult<T> result;
    private ResponseResult<String> errorResult;
    private Context context;
    private boolean responseFlag;

    public ResponseHandler(ResponseResult<T> result, Context context) {
        this.result = result;
        this.context = context;
        responseFlag = false;
    }

    public ResponseResult<T> getResponseResult() {
        return result;
    }

    public ResponseResult<String> getError() {
        return errorResult;
    }

    public boolean getFlag() {
        return this.responseFlag;
    }

    @Override
    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

    }

    @Override
    public void onSuccess(int i, Header[] headers, String s) {
        if (i == 200) {
            try {
                result = ResponseUtil.getResult(s, new TypeToken<ResponseResult<T>>(){}.getType());
            } catch (JsonParseException e) {
                errorResult = ResponseUtil.getResult(s, new TypeToken<ResponseResult<String>>(){}.getType());
            } finally {
                responseFlag = true;
            }
        }
    }
}
