package com.example.demoeditimage.phung.testactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.phung.RequestAPI;
import com.example.demoeditimage.phung.model.request.UpdateItemImgRequest;
import com.example.demoeditimage.phung.model.response.GetItemDetailResponse;
import com.example.demoeditimage.phung.model.response.GetItemListResponse;
import com.example.demoeditimage.phung.model.response.Product;
import com.example.demoeditimage.phung.model.response.Shop;
import com.example.demoeditimage.phung.model.response.ShopGallery;
import com.example.demoeditimage.phung.model.response.UpdateItemImgResponse;
import com.example.demoeditimage.phung.utils.APIClient;
import com.example.demoeditimage.phung.utils.MyConst;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TestCallAPI extends AppCompatActivity {

    Button btnCall, btnSignIn;
    TextView tvStatus;

    String HOST_URL = MyConst.getHostAddr();
    Boolean more;
    int offset;
    Long shopid = 94115363L;

    List<Shop> mShops;
    List<Product> mProducts;
    List<ShopGallery> mGallery;

    Long item_id = 2625380256L;
    List<String> images;
    Long partner_id = MyConst.partner_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_call_api);

        mShops = new ArrayList<>();
        mProducts = new ArrayList<>();
        images = new ArrayList<>();
        mGallery = new ArrayList<>();

        tvStatus = findViewById(R.id.tvStatus);
        btnCall = findViewById(R.id.btnCallApi);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getShops();

//                getAllItem();

//                images.add("https://res.cloudinary.com/sapodecor/image/upload/v1565228778/op_lung_meo.jpg");
//                images.add("https://res.cloudinary.com/sapodecor/image/upload/v1563240575/samples/animals/cat.jpg");
//                updateItemImg();

//                String name = "Nguyen Du";
//                String phone = "0123456789";
//                updateUserInfo(name, phone);

//                String old_pass = "1234567";
//                String old_pass = "1234566";
//                String new_pass = "123456";
//                changePassword(old_pass, new_pass);

//                String email = "kaitokid16121@gmail.com";
//                resetPasswordWithEmail(email);

                getGallery();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogIn();
            }
        });

    }

    private void getShops() {
        HOST_URL = MyConst.getHostAddr();

        Retrofit retrofit  = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();
        callApi.getShops(authorization).enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                Log.d("TestCallAPI",response.toString());
                if (response.isSuccessful()) {
                    mShops = response.body();
                    DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
                    Date date = new Date(mShops.get(0).getCreateDate());
                    Log.d("TestCallAPI",simple.format(date));
                    tvStatus.setText("Get shops successfully: " + mShops.size());
                    Toast.makeText(TestCallAPI.this, "Get shops successfully" + "\n" + mShops.size(), Toast.LENGTH_SHORT).show();
                } else {
                    tvStatus.setText("Failed: " + mShops.size());
                    Toast.makeText(TestCallAPI.this, "Something happened, cannot connect to the server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.d("TestCallAPI","onFailure: " + t.toString());
                t.printStackTrace();
            }
        });
    }

    private void testLogIn() {
        String email = "kaitokid1612@gmail.com";
        String password = "123456";


        Map<String, Object> credentials= new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        Retrofit retrofit  = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);


        // Test whether we can use Response as a Map( don't need to cast to a User Object)
        callApi.signIn(credentials).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                Log.d("MainActivity",response.toString());
                if (response.isSuccessful()) {

                }
                if (response.code()==200) {
                    // login successfully
                    // display the success field in the body
                    tvStatus.setText(response.body().get("success").toString());
                    mShops = (List<Shop>) response.body().get("shop");
                    Toast.makeText(TestCallAPI.this,"Login success " + mShops.size(),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestCallAPI.this,"Wrong email or password!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("MainActivity",t.getMessage());
            }
        });
    }

    private void getAllItem() {
        mProducts.clear();
        more = true;
        offset = 0;
        int entries = 100;
        getItemList(entries);

    }

    private void getItemList(final int entries) {

        HOST_URL = MyConst.getHostAddr();

        Retrofit retrofit  = APIClient.getClient(HOST_URL);

        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();
        callApi.getItemList(authorization, offset, entries, shopid).enqueue(new Callback<GetItemListResponse>() {
            @Override
            public void onResponse(Call<GetItemListResponse> call, Response<GetItemListResponse> response) {
                Log.d("TestCallAPI",response.toString());
                if (response.isSuccessful()) {
                    mProducts.addAll(response.body().getItems());
                    more = response.body().getMore();
                    offset += entries;

                    if(more) {
                        getItemList(entries);
                    } else {
                        tvStatus.setText("Get Item List successfully: " + mProducts.size());
                        Toast.makeText(TestCallAPI.this, "Get shops successfully" + "\n" + mProducts.size(), Toast.LENGTH_SHORT).show();
                        getItemDetails();
                    }
                } else {
//                    tvStatus.setText("Failed: " + mShops.size());
//                    Toast.makeText(TestCallAPI.this, "Something happened, cannot connect to the server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetItemListResponse> call, Throwable t) {

            }
        });
    }

    private void getItemDetails() {
        HOST_URL = MyConst.getHostAddr();

        Retrofit retrofit  = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();

        for (int i=0; i<mProducts.size(); i++) {
            final Product product = mProducts.get(i);
            final int finalI = i;
            callApi.getItemDetail(authorization, product.getItem_id(), product.getShopid()).enqueue(new Callback<GetItemDetailResponse>() {
                @Override
                public void onResponse(Call<GetItemDetailResponse> call, Response<GetItemDetailResponse> response) {
                    if (response.isSuccessful()) {
                        product.setName(response.body().getItem().getName());
                        product.setImages(response.body().getItem().getImages());
                        mProducts.set(finalI,product);
                    }
                }

                @Override
                public void onFailure(Call<GetItemDetailResponse> call, Throwable t) {

                }
            });
        }

    }

    private void updateItemImg() {
        HOST_URL = MyConst.getHostAddr();
        Retrofit retrofit  = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);
        String authorization = MyConst.getJwtToken();

        callApi.updateItemImg(authorization, new UpdateItemImgRequest(item_id, images, partner_id, shopid)).enqueue(new Callback<UpdateItemImgResponse>() {
            @Override
            public void onResponse(Call<UpdateItemImgResponse> call, Response<UpdateItemImgResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String msg = response.body().getMsg();
                    if (msg == null || !(msg.equals("Update item image success") || msg.equals("Nothing change for images"))) {
                        tvStatus.setText("Updated failed!");
                        Toast.makeText(TestCallAPI.this, "Updated failed!" + "\n" + msg, Toast.LENGTH_SHORT).show();
                    } else {
                        tvStatus.setText("Update successfully!");
                        Toast.makeText(TestCallAPI.this, "Update successfully!" + "\n" + msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateItemImgResponse> call, Throwable t) {
                Toast.makeText(TestCallAPI.this, "Call API failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void updateUserInfo(String name, String phone) {
        HOST_URL = MyConst.getHostAddr();
        Retrofit retrofit  = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);
        String authorization = MyConst.getJwtToken();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", name);
        requestBody.put("phone", phone);

        callApi.updateInfo(authorization, requestBody).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TestCallAPI.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Toast.makeText(TestCallAPI.this, "Không thể cập nhật thông tin, xin vui òng thử lại sau", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void changePassword(String old_pass, String new_pass) {
        if (old_pass.equals(new_pass)) {
            Toast.makeText(TestCallAPI.this, "Mật khẩu mới không được trùng với mật khẩu cũ, xin vui lòng nhập lại", Toast.LENGTH_LONG).show();
            return;
        }

        HOST_URL = MyConst.getHostAddr();
        Retrofit retrofit  = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);
        String authorization = MyConst.getJwtToken();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("old_password", old_pass);
        requestBody.put("new_password", new_pass);

        callApi.changePassord(authorization, requestBody).enqueue(new Callback<Map<String, Integer>>() {
            @Override
            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                if (response.isSuccessful()) {
                    int success = response.body().get("success");
                    if (success == 1) {
                        Toast.makeText(TestCallAPI.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    } else if (success == 0) {
                        Toast.makeText(TestCallAPI.this, "Thay đổi mật khẩu thất bại, vui lòng kiểm tra lại mật khẩu cũ", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {

            }
        });
    }

    private void resetPasswordWithEmail(String email) {

        HOST_URL = MyConst.getHostAddr();
        Retrofit retrofit = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);
        String authorization = MyConst.getJwtToken();

        callApi.resetPassword(authorization, email).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (response.isSuccessful()) {
                    Double success = (Double) response.body().get("success");
                    if (success.intValue() == 1) {
                        Toast.makeText(TestCallAPI.this, "OK", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TestCallAPI.this, "Không tồn tại tài khoản với email này", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {

            }
        });
    }

    private void getGallery() {
        HOST_URL = MyConst.getHostAddr();
        Retrofit retrofit = APIClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);
        String authorization = MyConst.getJwtToken();

        callApi.getGallery(authorization).enqueue(new Callback<List<ShopGallery>>() {
            @Override
            public void onResponse(Call<List<ShopGallery>> call, Response<List<ShopGallery>> response) {
                if (response.isSuccessful()) {
                    mGallery.clear();
                    mGallery.addAll(response.body());
                    Toast.makeText(TestCallAPI.this, "Ok nà" + mGallery.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ShopGallery>> call, Throwable t) {

            }
        });
    }
}
