package com.example.demoeditimage.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.demoeditimage.R;

import org.apache.commons.codec.digest.DigestUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LinkToShopeeActivity extends AppCompatActivity {

    private String redirectURL = "";
    private String partnerKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_to_shopee);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnLinkToShopee)
    void callOverview() {
        callOverviewStoreActivity();
    }

    private void callOverviewStoreActivity() {
        Intent intent = new Intent(this, OverviewStoreActivity.class);
        startActivity(intent);
//        calToken(redirectURL,partnerKey);

//        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://google.com"));
//        startActivity(browserIntent);
    }

    @OnClick (R.id.btnBackToLogin)
    void backToLogin(){
        onBackPressed();
    }

    public static String calToken(String redirectURL, String partnerKey) {
        String baseStr = partnerKey + redirectURL;
        return DigestUtils.sha256Hex(baseStr);
    }
}