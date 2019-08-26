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

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rclImage.setLayoutManager(layoutManager);

        rclImage.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        imageProductAdapter = new ImageProductAdapter(productImageList);

    }
}
