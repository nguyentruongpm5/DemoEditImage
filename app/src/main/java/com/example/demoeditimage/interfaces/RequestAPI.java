package com.example.demoeditimage.interfaces;

import com.example.demoeditimage.model.GetItemListResponse;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;
import com.example.demoeditimage.model.User;
import com.example.demoeditimage.model.param.UserParam;
import com.example.demoeditimage.phung.model.response.Shop;
import com.example.demoeditimage.request.UpdateItemImgRequest;
import com.example.demoeditimage.response.GetItemDetailResponse;
import com.example.demoeditimage.response.UpdateItemImgResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAPI {

    @POST("login")
    Call<UserParam> login(@Body Map<String, Object> user);

    @POST("registration")
    Call<User> registration(@Body UserParam user1);

    @POST("user/user/changepassword")
    Call<User> changePassword(@Body UserParam userParam);


    @GET("shop")
    Call<List<ShopInfo>> getShops(@Header("Authorization") String token);

    @GET("test/getItemList")
    Call<GetItemListResponse> getItemList(@Header("Authorization") String token, @Query("offset") Integer offset, @Query("entries") Integer entries, @Query("shopid") Long shopid);

    @GET("test/getItemDetail")
    Call<GetItemDetailResponse> getItemDetail(@Header("Authorization") String token, @Query("item_id") Long item_id, @Query("shopid") Long shopid);

    @POST("test/updateItemImg")
    Call<UpdateItemImgResponse> updateItemImg(@Header("Authorization") String token, @Body UpdateItemImgRequest updateItemImgRequest);
}
