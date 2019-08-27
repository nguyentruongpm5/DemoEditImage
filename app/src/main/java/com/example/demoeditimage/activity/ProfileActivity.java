package com.example.demoeditimage.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.demoeditimage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.phoneNumberEdt)
    EditText phoneNumberEdt;

    @BindView(R.id.text_input_phoneNumber)
    TextInputLayout text_input_phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

    }



    @OnClick(R.id.btnBackToLogin)
    void clickToBack (){
        super.onBackPressed();
    }

    @OnClick(R.id.changePassword_layout)
    void clicktoChangePassword() {
        callChangePasswordActivity();
    }

    private void callChangePasswordActivity() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
