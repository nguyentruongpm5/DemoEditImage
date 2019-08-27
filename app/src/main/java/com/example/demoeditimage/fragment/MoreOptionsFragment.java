package com.example.demoeditimage.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.activity.ChangePasswordActivity;
import com.example.demoeditimage.activity.IntroduceActivity;
import com.example.demoeditimage.activity.ProfileActivity;
import com.thekhaeng.pushdownanim.PushDown;
import com.thekhaeng.pushdownanim.PushDownAnim;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreOptionsFragment extends Fragment {


//    @BindView(R.id.profile_layout)
//    LinearLayout profile_layout;

    @BindView(R.id.profile_layout)
    LinearLayout profile_layout;

    @BindView(R.id.support_layout)
    LinearLayout support_layout;

    @BindView(R.id.signOut_layout)
    LinearLayout signOut_layout;

    public MoreOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_options, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.profile_layout)
    void clickToProfile() {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.support_layout)
    void clickToSupport() {
        Toast.makeText(getActivity(), "Support", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.signOut_layout)
    void clickToSignout(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Thông báo")
                .setMessage("Bạn có muốn thoát ra khỏi ứng dụng không ?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(),IntroduceActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

}
