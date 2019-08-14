package com.example.demoeditimage.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.demoeditimage.R;
import com.example.demoeditimage.activity.ChangePasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.phoneNumberEdt)
    EditText phoneNumberEdt;

    @BindView(R.id.text_input_phoneNumber)
    TextInputLayout text_input_phoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);


        return view;
    }


    @OnClick(R.id.btnChangePassword)
    void clicktoChangePassword() {
        callChangePasswordActivity();
    }

    private void callChangePasswordActivity() {
        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        startActivity(intent);
    }
}
