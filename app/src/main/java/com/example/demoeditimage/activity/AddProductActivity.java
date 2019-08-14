package com.example.demoeditimage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demoeditimage.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ButterKnife.bind(this);
    }

    @OnClick (R.id.btnBack)
    void clickToBack (){
        onBackPressed();
    }
}
