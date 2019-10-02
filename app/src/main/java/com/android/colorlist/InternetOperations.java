package com.android.colorlist;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InternetOperations {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String SERVER_URL = "https://reqres.in/api/";

    public static String get(String url) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String post(String url, String json) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).post(body).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
