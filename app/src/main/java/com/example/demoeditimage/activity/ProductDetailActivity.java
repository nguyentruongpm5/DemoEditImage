package com.example.demoeditimage.activity;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.demoeditimage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.capture_image_layout)
    LinearLayout capture_image_layout;

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

    @OnClick (R.id.capture_image_layout)
    void detailProduct (){
        showDialog();
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có muốn thoát ra khỏi ứng dụng không ?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }
}
