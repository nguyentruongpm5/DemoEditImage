package com.example.demoeditimage.interfaces;

import com.example.demoeditimage.model.User;
import com.example.demoeditimage.model.param.UserParam;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CallApiRegistration {
//    http://dev.superman-academy.com

    String BASE_URL = "http://172.104.47.79:8181/api/v1/";

    @POST("login")
    Call<UserParam> login(@Body Map<String, Object> user);

    @POST("registration")
    Call<User> registration(@Body UserParam user1);

    @POST("user/user/changepassword")
    Call<User> changePassword(@Body UserParam userParam);


}
