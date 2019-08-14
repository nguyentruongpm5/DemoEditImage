package com.example.demoeditimage.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demoeditimage.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    void onClickLogin() {
        callLoginActivity();
    }

    @OnClick(R.id.btnSignup)
    void onClickSignup() {
        callSignUpActivity();
    }


    // gọi fragment đăng nhập
    private void callLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // gọi fragment đăng kí
    private void callSignUpActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {

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
