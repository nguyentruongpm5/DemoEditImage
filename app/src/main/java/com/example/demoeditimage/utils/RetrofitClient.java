package com.example.demoeditimage.utils;

import com.example.demoeditimage.interfaces.CallApiRegistration;
import com.example.demoeditimage.interfaces.CallLocalAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(final String baseUrl) {

        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();

        return retrofit;
    }

    public CallApiRegistration getCallApiRegistration() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CallApiRegistration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CallApiRegistration.class);
    }

    public CallLocalAPI getCallLocalAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CallLocalAPI.BASE_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CallLocalAPI.class);
    }

}
