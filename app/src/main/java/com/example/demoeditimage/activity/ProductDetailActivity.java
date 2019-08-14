package com.example.demoeditimage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demoeditimage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnBack)
    void clickToBack (){
        onBackPressed();
    }
}
