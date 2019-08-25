package com.example.demoeditimage.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.adapter.ShopInfoAdapter;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.interfaces.CallShopDetailListener;
import com.example.demoeditimage.interfaces.RequestAPI;
import com.example.demoeditimage.model.ProductItem;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;
import com.example.demoeditimage.model.ShopInfo.ShopInfoOverview;
import com.example.demoeditimage.utils.MyConst;
import com.example.demoeditimage.utils.RetrofitClient;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.demoeditimage.R.layout.edt_addstore;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewStoreFragment extends Fragment {

    @BindView(R.id.rclShopList)
    RecyclerView rclShopList;

    private String HOST_URL = MyConst.getHostAddr();

    private ShopInfoAdapter shopInfoAdapter;
    private List<ShopInfoOverview> shopInfoOverviews = new ArrayList<>();

    private List<ShopInfo> shopList = new ArrayList<>();

    private ShopInfoOverview shopInfoOverview;
    public OverviewStoreFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview_store, container, false);

        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rclShopList.setLayoutManager(layoutManager);



        getShops();


        if(shopList != null){
            shopInfoAdapter = new ShopInfoAdapter(shopList,new CallShopDetailListener() {
                @Override
                public void itemShopClick(final int position) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    alert.setMessage("Bạn có muốn xóa gian hàng này không ?");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            shopList.remove(shopList.get(position));
                            shopInfoAdapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    alert.show();
                }
            });
            rclShopList.setAdapter(shopInfoAdapter);

            shopInfoAdapter.notifyDataSetChanged();


        }


//        shopInfoOverviews.add(new ShopInfoOverview("baotrang",null,
//                "Đã cập nhật","shopee.vn/baotrang99"));
//        shopInfoOverviews.add(new ShopInfoOverview("shopee",null,
//                "Đã cập nhật","shopee.vn/126468"));
//
//        shopInfoOverviews.add(new ShopInfoOverview("abc",null,
//                "Đã cập nhật","shopee.vn/126468"));

//        shopInfoOverviews.removeAll(shopInfoOverviews);
        return view;
    }

    private void getShops() {
        HOST_URL = MyConst.getHostAddr();

        Retrofit retrofit  = RetrofitClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();
        callApi.getShops(authorization).enqueue(new Callback<List<ShopInfo>>() {
            @Override
            public void onResponse(Call<List<ShopInfo>> call, Response<List<ShopInfo>> response) {
                Log.d("TestCallAPI",response.toString());
                if (response.isSuccessful()) {
                    shopList.clear();
                    shopList.addAll(response.body());

                    shopInfoAdapter.notifyDataSetChanged();
//                    DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
//                    Date date = new Date(shopList.get(0).getCreateDate());
//                    Log.d("TestCallAPI",simple.format(date));
//                    tvStatus.setText("Get shops successfully: " + mShops.size());

                    Toast.makeText(getActivity(), "Get shops successfully" + "\n" + shopList.size(), Toast.LENGTH_SHORT).show();
                    getShopAvatar();
                } else {
//                    tvStatus.setText("Failed: " + mShops.size());
                    Toast.makeText(getActivity(), "Something happened, cannot connect to the server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ShopInfo>> call, Throwable t) {
                Log.d("TestCallAPI","onFailure: " + t.toString());
                t.printStackTrace();
            }
        });
    }

    private void getShopAvatar() {
        HOST_URL = MyConst.getHostAddr();
        Retrofit retrofit  = RetrofitClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);
        String authorization = MyConst.getJwtToken();

        for (int i=0 ; i<shopList.size(); i++) {
            final ShopInfo shop = shopList.get(i);
            Map<String, Long> body = new HashMap<>();
            body.put("partner_id", MyConst.partner_id);
            body.put("shopid", shop.getShop_id());
            final int finalI = i;
            callApi.getShopInfo(authorization, body).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.isSuccessful()) {
                        List<String> images = (List<String>) response.body().get("images");
                        if (images!= null) {
                            shop.setAvatar(images.get(0));
                            shopList.set(finalI, shop);
                            shopInfoAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {

                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.addStoreBtn)
    void addStore (){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setMessage("Thêm gian hàng");

        View alertLayout =  View.inflate(getActivity(),R.layout.edt_addstore,null);
        alert.setView(alertLayout);
        final TextInputEditText edit = alertLayout.findViewById(R.id.storeNameEdt);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String shopName = edit.getText().toString();
                shopInfoOverviews.add(new ShopInfoOverview(shopName,null,null,null));
                Toast.makeText(getContext(), "Tên gian hàng vừa thêm: " + shopName, Toast.LENGTH_SHORT).show();

                shopInfoAdapter.notifyDataSetChanged();
            }
        });

        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getShops();
    }
}
