package com.example.demoeditimage.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.activity.AddProductActivity;
import com.example.demoeditimage.activity.ProductDetailActivity;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.interfaces.CallLocalAPI;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.model.ProductItem;
import com.example.demoeditimage.utils.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductManagementFragment extends Fragment {

    @BindView(R.id.rclProductList)
    RecyclerView rclProductList;

    private List<ProductItem> productItemList = new ArrayList<>();
    private ProductItem productItem;

    private RetrofitClient retrofitClient = new RetrofitClient();

    public ProductManagementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_management, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rclProductList.setLayoutManager(layoutManager);

        productDetailItem();

        getProducts();


        productItemList.add(new ProductItem(1,"husky","SKU1",
                "Cập nhật thành công",null,null,null,
                null, 0,0,0,0,0));
        productItemList.add(new ProductItem(2,"Doraemon","SKU2",
                "Cập nhật thành công",null,null,null,
                null, 0,0,0,0,0));

        productItemList.add(new ProductItem(3,"Sapo","SKU3",
                "Cập nhật thành công",null,null,null,
                null, 0,0,0,0,0));

//        String json = new Gson().toJson(productItem);

        return view;
    }

    private void productDetailItem() {
        if (productItemList != null) {
            ProductItemAdapter productItemAdapter = new ProductItemAdapter(productItemList, new CallProductlDetailListener() {
                @Override
                public void itemProductClick(int position) {
                    productItem = productItemList.get(position);

                    callProductDetailActivity();

                }
            });
            rclProductList.setAdapter(productItemAdapter);

            productItemAdapter.notifyDataSetChanged();
        }
    }

    private void getProducts() {
//        Map<String,String> parameter = new HashMap<>();
//        parameter.put("product_id","1");


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(CallLocalAPI.BASE_URL_local)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        CallLocalAPI api = retrofit.create(CallLocalAPI.class);

        CallLocalAPI api = retrofitClient.getCallLocalAPI();

        Call<List<ProductItem>> call = api.getProducts();
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductItem>> call, @NonNull Response<List<ProductItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                } else {
                    productItemList = response.body();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductItem>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Mất kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.addProductBtn)
    void clickToAddProduct() {
        callAddProductActivity();
    }


    public static String LoadImageFromWebOperations(String url) {
        try {
            final int THUMBNAIL_SIZE = 64;
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");

            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(url), THUMBNAIL_SIZE, THUMBNAIL_SIZE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();



            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }



    private void callProductDetailActivity() {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);

        intent.putExtra("productItem", productItem);

        startActivity(intent);
    }

    private void callAddProductActivity() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        startActivity(intent);
    }

}
