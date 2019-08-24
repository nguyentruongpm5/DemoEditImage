package com.example.demoeditimage.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.interfaces.CallApiRegistration;
import com.example.demoeditimage.model.User;
import com.example.demoeditimage.model.param.UserParam;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {


    @BindView(R.id.recentPasswordEdt)
    EditText recentPasswordEdt;

    @BindView(R.id.newPasswordEdt)
    EditText newPasswordEdt;

    @BindView(R.id.confirmPasswordEdt)
    EditText confirmPasswordEdt;

    @BindView(R.id.text_input_recentPassword)
    TextInputLayout text_input_recentPassword;

    @BindView(R.id.text_input_newPassword)
    TextInputLayout text_input_newPassword;

    @BindView(R.id.text_input_confirmPassword)
    TextInputLayout text_input_confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ButterKnife.bind(this);

    }

    @OnTextChanged(value = R.id.recentPasswordEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void recentPasswordEdtAfterChange() {
        if (!recentPasswordEdt.getText().toString().isEmpty()) {
            text_input_recentPassword.setErrorEnabled(false);
        }
    }


    @OnTextChanged(value = R.id.newPasswordEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void passwordEdtAfterChange() {
        if (!newPasswordEdt.getText().toString().isEmpty()) {
            text_input_newPassword.setErrorEnabled(false);
        }
    }


    @OnTextChanged(value = R.id.confirmPasswordEdt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void confirmPasswordEdtAfterChange() {
        if (!confirmPasswordEdt.getText().toString().isEmpty()) {
            text_input_confirmPassword.setErrorEnabled(false);
        }
    }

    @OnClick(R.id.btnBack)
    void clickToBack(){
        super.onBackPressed();
    }

    @OnClick(R.id.btnSavePassword)
    void onClicksignUp() {
        if (recentPasswordEdt.getText().toString().matches("")) {
            text_input_recentPassword.setError("Vui lòng mật khẩu hiện tại");
//            text_input_fullname.setHintEnabled(false);
//            fullNameEdt.setHintTextColor(getResources().getColor(R.color.Warning_TextView));
        }


        if (newPasswordEdt.getText().toString().matches("")) {
            text_input_newPassword.setError("Vui lòng nhập mật khẩu mới");
//            text_input_phoneNumber.setHintEnabled(false);
//            phoneNumberEdt.setHintTextColor(getResources().getColor(R.color.Warning_TextView));
        }


        if (confirmPasswordEdt.getText().toString().matches("")) {
            text_input_confirmPassword.setError("Vui lòng xác nhận lại mật khẩu mới");
//            text_input_email.setHintEnabled(false);
//            emailEdt.setHintTextColor(getResources().getColor(R.color.Warning_TextView));
        }
        
        else
            changePassword();

    }

    private void changePassword() {
        String recentPassword = recentPasswordEdt.getText().toString();
        String newPassword = newPasswordEdt.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallApiRegistration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallApiRegistration api = retrofit.create(CallApiRegistration.class);

        UserParam userParam = new UserParam(null,null,null,null,recentPassword,newPassword,null);

        Call<User> call = api.changePassword(userParam);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


}
