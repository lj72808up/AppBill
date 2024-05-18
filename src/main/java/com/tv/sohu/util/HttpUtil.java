package com.tv.sohu.util;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class HttpUtil {
    private static final Logger Log = LoggerFactory.getLogger(HttpUtil.class);
    private static final OkHttpClient client;
    private static final String url = "http://10.33.128.5:5004/ws/v1/cluster/apps?states=finished";

    static {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectionPool(new ConnectionPool(1, 1, TimeUnit.MINUTES));
        client = builder.build();
    }

    public static String get() throws Exception {
        Log.info("visit: " + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            } else {
                Log.error("Error: " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            Log.error("访问出错：", e);
            throw e;
        }
        return "";
    }
}
