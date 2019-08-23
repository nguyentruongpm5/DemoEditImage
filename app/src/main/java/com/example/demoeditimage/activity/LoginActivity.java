package com.example.demoeditimage.activity;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.interfaces.CallApiRegistration;
import com.example.demoeditimage.model.User;
import com.example.demoeditimage.utils.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.passwordEdt)
    EditText passwordEdt;

    @BindView(R.id.loginBtn)
    Button loginBtn;

    @BindView(R.id.unameEdt)
    EditText unameEdt;

    @BindView(R.id.text_input_username)
    TextInputLayout text_input_username;

    @BindView(R.id.text_input_password)
    TextInputLayout text_input_password;

    @BindView(R.id.txtForgetPassword)
    TextView txtForgetPassword;


    private RetrofitClient retrofitClient = new RetrofitClient();
//    @BindView(R.id.txtEmail)
//    TextView txtEmail;
//
//    @BindView(R.id.txtPassword)
//    TextView txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @OnTextChanged(value = R.id.unameEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void unameChanged() {
        if (!unameEdt.getText().toString().isEmpty()){
//            txtEmail.setVisibility(View.VISIBLE);
            text_input_username.setErrorEnabled(false);
        }

    }

    @OnTextChanged(value = R.id.passwordEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void passwordChanged() {
        if (!passwordEdt.getText().toString().isEmpty())
        {
//            txtPassword.setVisibility(View.VISIBLE);
            text_input_password.setErrorEnabled(false);
        }
    }

    // đăng nhập
    @OnClick(R.id.loginBtn)
    void onClicklogin() {
        if (unameEdt.getText().toString().matches(""))
            text_input_username.setError("Xin vui lòng nhập email/ số điện thoại");


        if (passwordEdt.getText().toString().matches(""))
            text_input_password.setError("Xin vui lòng nhập mật khẩu");

        getUsers();
    }

    private void getUsers() {

        String userName = unameEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();

        final Map<String, Object> user = new HashMap<>();
        user.put("email", userName);
        user.put("password", password);

        CallApiRegistration api = retrofitClient.getCallApiRegistration();


        // gửi request lên server
        Call<User> call = api.login(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
//                    Toast.makeText(getActivity(), "Sai tài khoản hoặc mật khẩu !!", Toast.LENGTH_SHORT).show();
//                    return;

//                }
//                else {
//                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công !!", Toast.LENGTH_SHORT).show();
                    callLinkToShopeeActivity();
                } else {
                    if (response.code() == 401)
                        Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu !!", Toast.LENGTH_SHORT).show();
                }
//                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call,@NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
            }
        });

    }



    // ẩn và hiển thị mật khẩu
//    @OnClick(R.id.btnShowPassWord)
//    void onClickShowPassWord() {
//        if (isShowPassword) {
//            passwordEdt.setTransformationMethod(new PasswordTransformationMethod());
//            btnShowPassWord.setText("Hiện");
//            isShowPassword = false;
//        } else {
//            passwordEdt.setTransformationMethod(null);
//            btnShowPassWord.setText("Ẩn");
//            isShowPassword = true;
//        }
//        passwordEdt.setSelection(passwordEdt.length());
//    }


    // nút back
    @OnClick(R.id.btnBackToLogin)
    void onClickBackToLogin() {
        super.onBackPressed();
    }


    // gọi fragment main chính
    private void callLinkToShopeeActivity() {
        Intent intent = new Intent(this, LinkToShopeeActivity.class);
        startActivity(intent);
    }



//    private void hideKeyBoard() {
//        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//    }
}
