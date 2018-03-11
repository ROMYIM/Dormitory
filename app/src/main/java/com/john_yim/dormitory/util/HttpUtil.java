package com.john_yim.dormitory.util;

import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.RequestType;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by MSI-PC on 2018/2/14.
 */

public class HttpUtil {
    private static String baseUri = Constant.SERVICE_URI_NUBIA;
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();

    public static AsyncHttpClient getAsyncHttpClient() {
        return asyncHttpClient;
    }

    public static SyncHttpClient getSyncHttpClient() {
        return syncHttpClient;
    }

    private static String getAbsoluteUrl(String requestUrl) {
        return baseUri + requestUrl;
    }

    public static void asyncGet(String requestUrl, RequestParams params,
                             AsyncHttpResponseHandler responseHandler, PersistentCookieStore cookieStore) {
        System.out.println(getAbsoluteUrl(requestUrl));
        asyncHttpClient.setCookieStore(cookieStore);
        asyncHttpClient.get(getAbsoluteUrl(requestUrl), params, responseHandler);
    }

    public static void asyncPost(String requestUrl, RequestParams params,
                              AsyncHttpResponseHandler responseHandler, PersistentCookieStore cookieStore) {
        System.out.println(getAbsoluteUrl(requestUrl));
        asyncHttpClient.setCookieStore(cookieStore);
        asyncHttpClient.post(getAbsoluteUrl(requestUrl), params, responseHandler);
    }

    public static void addHeader(String key, String value) {
        asyncHttpClient.addHeader(key, value);
    }

    public static void setCookieStore(PersistentCookieStore cookieStore, AsyncHttpClient httpClient) {
        httpClient.setCookieStore(cookieStore);
    }

    public static <T> void syncGet(String requestUrl, RequestParams params,
                                   ResponseHandler<T> responseHandler, PersistentCookieStore cookieStore) {
        System.out.println(getAbsoluteUrl(requestUrl));
        syncHttpClient.setCookieStore(cookieStore);
        new Thread(new ResponseGetter(requestUrl, params, responseHandler, RequestType.GET)).start();
    }

    public static <T> void syncPost(String requestUrl, RequestParams params,
                                   ResponseHandler<T> responseHandler, PersistentCookieStore cookieStore) {
        System.out.println(getAbsoluteUrl(requestUrl));
        syncHttpClient.setCookieStore(cookieStore);
        new Thread(new ResponseGetter(requestUrl, params, responseHandler, RequestType.POST)).start();
    }

    private static class ResponseGetter<T> implements Runnable {
        String url;
        RequestParams params;
        ResponseHandler<T> responseHandler;
        RequestType type;

        ResponseGetter(String url, RequestParams params, ResponseHandler<T> responseHandler,
                       RequestType type) {
            this.url = url;
            this.params = params;
            this.responseHandler = responseHandler;
            this.type = type;
        }

        @Override
        public void run() {
            System.out.println("syncHttpClient is running");
            switch (type) {
                case GET:
                    RequestHandle requestHandle = syncHttpClient.get(getAbsoluteUrl(url), params, responseHandler);
                    if (requestHandle.isFinished()) {
                        System.out.println("访问结束");
                    }
                    break;
                case POST:
                    syncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
            }
        }
    }
}
