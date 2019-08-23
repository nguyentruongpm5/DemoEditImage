package com.example.demoeditimage.utils;

import com.example.demoeditimage.interfaces.CallApiRegistration;
import com.example.demoeditimage.interfaces.CallLocalAPI;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public CallApiRegistration getCallApiRegistration() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallApiRegistration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CallApiRegistration.class);
    }

    public CallLocalAPI getCallLocalAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallLocalAPI.BASE_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CallLocalAPI.class);
    }

}
