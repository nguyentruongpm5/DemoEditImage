package com.example.demoeditimage.phung;

import com.example.demoeditimage.phung.model.request.UpdateItemImgRequest;
import com.example.demoeditimage.phung.model.response.GetItemDetailResponse;
import com.example.demoeditimage.phung.model.response.GetItemListResponse;
import com.example.demoeditimage.phung.model.response.Product;
import com.example.demoeditimage.phung.model.response.Shop;
import com.example.demoeditimage.phung.model.response.UpdateItemImgResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAPI {
    @POST("login")
    Call<Map> signIn(@Body Map<String, Object> user);

    @GET("shop")
    Call<List<Shop>> getShops(@Header("Authorization") String token);

    @GET("test/getItemList")
    Call<GetItemListResponse> getItemList(@Header("Authorization") String token, @Query("offset") Integer offset, @Query("entries") Integer entries, @Query("shopid") Long shopid);

    @GET("test/getItemDetail")
    Call<GetItemDetailResponse> getItemDetail(@Header("Authorization") String token, @Query("item_id") Long item_id, @Query("shopid") Long shopid);

    @POST("test/updateItemImg")
    Call<UpdateItemImgResponse> updateItemImg(@Header("Authorization") String token, @Body UpdateItemImgRequest updateItemImgRequest);
}
