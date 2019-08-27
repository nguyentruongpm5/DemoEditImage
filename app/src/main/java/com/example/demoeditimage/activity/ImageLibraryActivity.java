package com.example.demoeditimage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.demoeditimage.R;
import com.example.demoeditimage.adapter.ImageProductAdapter;
import com.example.demoeditimage.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageLibraryActivity extends AppCompatActivity {

    @BindView(R.id.rclImage)
    RecyclerView rclImage;

    private ImageProductAdapter imageProductAdapter;

    private List<ProductImage> productImageList = new ArrayList<>();

    private ProductImage productImage;
    private int numberOfColumns = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_library);

        ButterKnife.bind(this);

        init();
        rclImage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rclImage.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        imageProductAdapter = new ImageProductAdapter(productImageList);



    }

    private void init() {
        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/73edcdfb72eb9958aad86329f73b0ff7",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));


        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));


        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));


        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

    }
}
