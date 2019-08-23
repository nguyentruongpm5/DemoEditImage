package com.example.demoeditimage.interfaces;

import com.example.demoeditimage.model.ProductItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CallLocalAPI {
    String BASE_URL_local = "https://my-json-server.typicode.com/missga97/product/";

    @POST("product")
        Call<List<ProductItem>> getProducts();
}
