package com.example.demoeditimage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.adapter.ImageLibraryAdapter;
import com.example.demoeditimage.adapter.ImageProductAdapter;
import com.example.demoeditimage.adapter.StoreAdapter;
import com.example.demoeditimage.interfaces.RequestAPI;
import com.example.demoeditimage.model.Product;
import com.example.demoeditimage.model.ProductImage;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;
import com.example.demoeditimage.utils.MyConst;
import com.example.demoeditimage.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 */
public class ImageLibraryFragment extends Fragment {


    @BindView(R.id.spnStoreName)
    Spinner spnStoreName;

    @BindView(R.id.rclImage)
    RecyclerView rclImage;
    private static String HOST_URL = MyConst.getHostAddr();
    private List<ShopInfo> shopInfoList = new ArrayList<>();

    private List<Product> mProducts = new ArrayList<>();

    private long shopId;

    private StoreAdapter storeAdapter;

    private ImageLibraryAdapter imageLibraryAdapter;

    private List<ProductImage> productImageList = new ArrayList<>();

    private ProductImage productImage;

    private int numberOfColumns = 5;

    public ImageLibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_library, container, false);

        ButterKnife.bind(this,view);

        init();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rclImage.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        imageLibraryAdapter = new ImageLibraryAdapter(productImageList);

        rclImage.setAdapter(imageLibraryAdapter);

        imageLibraryAdapter.notifyDataSetChanged();

        getStoreList();

        getShopList();

        testMultiSelect();

        return view;
    }

    private void testMultiSelect() {
        String text = "";
        int i = 0;
        for (ProductImage productImage : productImageList) {
            if (productImage.isSelected()) {
                i++;
                text += i;
            }

            Log.d("TAG","Output : " + text);
        }
    }

    private void getStoreList() {
        storeAdapter = new StoreAdapter(getContext(), R.layout.spinner_row_store, shopInfoList);
        spnStoreName.setAdapter(storeAdapter);
        storeAdapter.notifyDataSetChanged();

        spnStoreName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shopId = shopInfoList.get(position).getShop_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getShopList() {

        Retrofit retrofit = RetrofitClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();
        callApi.getShops(authorization).enqueue(new Callback<List<ShopInfo>>() {
            @Override
            public void onResponse(Call<List<ShopInfo>> call, Response<List<ShopInfo>> response) {
                Log.d("TestCallAPI", response.toString());
                if (response.isSuccessful()) {
                    shopInfoList.clear();
                    shopInfoList.addAll(response.body());
                    storeAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "Something happened, cannot connect to the server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ShopInfo>> call, Throwable t) {
                Log.d("TestCallAPI", "onFailure: " + t.toString());
                t.printStackTrace();
            }
        });
    }


    private void init() {
        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/73edcdfb72eb9958aad86329f73b0ff7",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));


        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));


        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));


        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

        productImageList.add(new ProductImage("https://banhang.shopee.vn/api/v1/cdn_proxy/03d8288f1fbeb9a5929656685e89be18",false));

    }


}
