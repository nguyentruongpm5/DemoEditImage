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
import com.example.demoeditimage.model.param.UserParam;
import com.example.demoeditimage.utils.MyConst;
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
        if (!unameEdt.getText().toString().isEmpty()) {
//            txtEmail.setVisibility(View.VISIBLE);
            text_input_username.setErrorEnabled(false);
        }

    }

    @OnTextChanged(value = R.id.passwordEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void passwordChanged() {
        if (!passwordEdt.getText().toString().isEmpty()) {
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

//        callLinkToShopeeActivity();

        getUsers();
    }

    @OnClick(R.id.txtForgetPassword)
    void clickToGetPassword(){
        callForgotPasswordActivity();
    }

    private void getUsers() {

        String userName = unameEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();

        final Map<String, Object> user = new HashMap<>();

        //hello@gmail.com (123456)
        user.put("email", "hello@gmail.com");
        user.put("password", "123456");

        CallApiRegistration api = retrofitClient.getCallApiRegistration();


        // gửi request lên server
        Call<UserParam> call = api.login(user);
        call.enqueue(new Callback<UserParam>() {
            @Override
            public void onResponse(Call<UserParam> call, Response<UserParam> response) {
                UserParam userParam = response.body();
                if (response.code() == 200) {
                    MyConst.setJwtToken(userParam.getToken());
                    MyConst.setUserid(userParam.getId());
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công !!", Toast.LENGTH_SHORT).show();
                    callOverviewStoreActivity();
                } else
                    Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserParam> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Mất kết nối Internet", Toast.LENGTH_SHORT).show();

            }
        });

    }





//                }

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
    private void callOverviewStoreActivity() {
        Intent intent = new Intent(this, OverviewStoreActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    private void callForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }


//    private void hideKeyBoard() {
//        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//    }
}
