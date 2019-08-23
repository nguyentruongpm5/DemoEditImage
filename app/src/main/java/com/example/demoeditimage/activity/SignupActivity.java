package com.example.demoeditimage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.interfaces.CallApiRegistration;
import com.example.demoeditimage.model.User;
import com.example.demoeditimage.model.param.UserParam;
import com.example.demoeditimage.utils.RetrofitClient;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {


//    @BindViews({ R.id.passwordEdt, R.id.fullNameEdt, R.id.phoneNumberEdt, R.id.emailEdt})
//    List<EditText> listEditText;


    @BindView(R.id.passwordEdt)
    EditText passwordEdt;

    @BindView(R.id.fullNameEdt)
    EditText fullNameEdt;

    @BindView(R.id.phoneNumberEdt)
    EditText phoneNumberEdt;

    @BindView(R.id.emailEdt)
    EditText emailEdt;

    @BindView(R.id.text_input_fullname)
    TextInputLayout text_input_fullname;

    @BindView(R.id.text_input_phoneNumber)
    TextInputLayout text_input_phoneNumber;

    @BindView(R.id.text_input_email)
    TextInputLayout text_input_email;

    @BindView(R.id.text_input_password)
    TextInputLayout text_input_password;

    private RetrofitClient retrofitClient = new RetrofitClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

    }


    @OnClick(R.id.btnBackToLogin)
    void onClickBackToLogin() {
        super.onBackPressed();
    }

    @OnTextChanged(value = R.id.fullNameEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void unameAfterChange() {
        if (!fullNameEdt.getText().toString().isEmpty()) {
            text_input_fullname.setErrorEnabled(false);
        }
    }


    @OnTextChanged(value = R.id.phoneNumberEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void phoneNumberAfterChange() {
        if (!phoneNumberEdt.getText().toString().isEmpty()) {
            text_input_phoneNumber.setErrorEnabled(false);
        }
    }


    @OnTextChanged(value = R.id.emailEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void emailAfterChange() {
        if (!emailEdt.getText().toString().isEmpty()) {
            text_input_email.setErrorEnabled(false);
        }
    }


    @OnTextChanged(value = R.id.passwordEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void passwordAfterChange() {
        if (!passwordEdt.getText().toString().isEmpty()) {
            text_input_password.setErrorEnabled(false);
        }
    }


    @OnClick(R.id.signUpBtn)
    void onClicksignUp() {

        if (fullNameEdt.getText().toString().matches("")) {
            text_input_fullname.setError("Vui lòng nhập tên");
//            text_input_fullname.setHintEnabled(false);
//            fullNameEdt.setHintTextColor(getResources().getColor(R.color.Warning_TextView));
        }


        if (phoneNumberEdt.getText().toString().matches("")) {
            text_input_phoneNumber.setError("Vui lòng nhập số điện thoại");
//            text_input_phoneNumber.setHintEnabled(false);
//            phoneNumberEdt.setHintTextColor(getResources().getColor(R.color.Warning_TextView));
        }


        if (emailEdt.getText().toString().matches("")) {
            text_input_email.setError("Vui lòng nhập Email");
//            text_input_email.setHintEnabled(false);
//            emailEdt.setHintTextColor(getResources().getColor(R.color.Warning_TextView));
        }

        if (passwordEdt.getText().toString().matches("")) {
            text_input_password.setError("Vui lòng nhập mật khẩu");
//            text_input_password.setHintEnabled(false);
//            passwordEdt.setHintTextColor(getResources().getColor(R.color.Warning_TextView));
        } else
            createUsers();

    }


    private void createUsers() {

        String fullName = fullNameEdt.getText().toString().trim();
        String phoneNumber = phoneNumberEdt.getText().toString().trim();
        String email = emailEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();

        CallApiRegistration api = retrofitClient.getCallApiRegistration();

        UserParam user1 = new UserParam(fullName, phoneNumber, email, password, null, null);

        Call<User> call = api.registration(user1);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (response.code() == 200) {
                        User user = response.body();
                        assert user != null;
                        if (user.getSuccess() == 1) {
                            Toast.makeText(getApplicationContext(), "Đăng kí thành công !!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (user.getSuccess() == 0)
                                Toast.makeText(getApplicationContext(), "Email đã tồn tại !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
