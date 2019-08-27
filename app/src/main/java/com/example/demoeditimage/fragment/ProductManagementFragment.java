package com.example.demoeditimage.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.demoeditimage.activity.AddProductActivity;
import com.example.demoeditimage.activity.ProductDetailActivity;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.adapter.StoreAdapter;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.interfaces.RequestAPI;
import com.example.demoeditimage.model.GetItemListResponse;
import com.example.demoeditimage.model.Product;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;


import com.example.demoeditimage.response.GetItemDetailResponse;
import com.example.demoeditimage.utils.MyConst;
import com.example.demoeditimage.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductManagementFragment extends Fragment {

    private static String HOST_URL = MyConst.getHostAddr();
    @BindView(R.id.rclProductList)
    RecyclerView rclProductList;


    @BindView(R.id.spnStoreName)
    Spinner spnStoreName;

    private ProgressDialog loadingbar;

    private Boolean more;

    private int offset;

    private long shopId;

    private List<Product> mProducts = new ArrayList<>();


    private List<ShopInfo> shopInfoList = new ArrayList<>();
//    private ProductItem productItem;

    private Product productItem;


    private StoreAdapter storeAdapter;

    private ProductItemAdapter productItemAdapter;


    public ProductManagementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_management, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rclProductList.setLayoutManager(layoutManager);

        productDetailItem();

        // chờ dữ liệu tải về
        loadingBarProcess();

//        // lấy danh sách sản phẩm
//        getProducts();


        // gán dữ liệu cho spinner
        getStoreList();


        // lấy danh sách gian hàng
        getShopList();


        return view;
    }

    private void loadingBarProcess() {
        loadingbar = new ProgressDialog(getActivity());
        loadingbar.setMessage("Đang tải dữ liệu...");
        loadingbar.show();
        loadingbar.setCancelable(false);
        loadingbar.setCanceledOnTouchOutside(false);
    }

    private void getAllItems() {
        mProducts.clear();
        more = true;
        offset = 0;
        int entries = 100;
        getItemList(entries);
    }

    private void getItemList(final int entries) {

        HOST_URL = MyConst.getHostAddr();

        Retrofit retrofit = RetrofitClient.getClient(HOST_URL);

        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();

        if (shopId == 0) {
            return;
        }
        callApi.getItemList(authorization, offset, entries, shopId).enqueue(new Callback<GetItemListResponse>() {
            @Override
            public void onResponse(Call<GetItemListResponse> call, Response<GetItemListResponse> response) {
                Log.d("TestCallAPI", response.toString());
                if (response.isSuccessful()) {
                    mProducts.addAll(response.body().getItems());
                    more = response.body().getMore();
                    offset += entries;

                    if (more) {
                        getItemList(entries);
                    } else {
                        productItemAdapter.notifyDataSetChanged();
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
        Retrofit retrofit = RetrofitClient.getClient(HOST_URL);
        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();

        for (int i = 0; i < mProducts.size(); i++) {
            final Product product = mProducts.get(i);
            final int finalI = i;
            callApi.getItemDetail(authorization, product.getItem_id(), product.getShopid()).enqueue(new Callback<GetItemDetailResponse>() {
                @Override
                public void onResponse(Call<GetItemDetailResponse> call, Response<GetItemDetailResponse> response) {
                    if (response.isSuccessful()) {
                        product.setName(response.body().getItem().getName());
                        product.setImages(response.body().getItem().getImages());
                        // die app onBackStack
                        mProducts.set(finalI, product);
                        productItemAdapter.notifyDataSetChanged();
                        loadingbar.cancel();
                    }
                }

                @Override
                public void onFailure(Call<GetItemDetailResponse> call, Throwable t) {

                }
            });
        }
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

    private void getStoreList() {
        storeAdapter = new StoreAdapter(getContext(), R.layout.spinner_row_store, shopInfoList);
        spnStoreName.setAdapter(storeAdapter);
        storeAdapter.notifyDataSetChanged();

        spnStoreName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shopId = shopInfoList.get(position).getShop_id();
                getAllItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void productDetailItem() {
        if (mProducts != null) {
            productItemAdapter = new ProductItemAdapter(mProducts, new CallProductlDetailListener() {
                @Override
                public void itemProductClick(int position) {
                    productItem = mProducts.get(position);
                    callProductDetailActivity();

                }
            });
            rclProductList.setAdapter(productItemAdapter);
            productItemAdapter.notifyDataSetChanged();

        }
    }

//    private void getProducts() {
//        HOST_URL = MyConst.getHostAddr();
//
//        Retrofit retrofit = RetrofitClient.getClient(HOST_URL);
//        RequestAPI callApi = retrofit.create(RequestAPI.class);
//
//        callApi.getProducts().enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
//                } else {
//                    productItemList.clear();
////                    productItemList.addAll(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//                Toast.makeText(getActivity(), "Mất kết nối", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    @OnClick(R.id.addProductBtn)
//    void clickToAddProduct() {
//        callAddProductActivity();
//    }


    private void callProductDetailActivity() {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);

        intent.putExtra("productItem", productItem);

        startActivity(intent);
    }

//    private void callAddProductActivity() {
//        Intent intent = new Intent(getActivity(), AddProductActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onResume() {
        super.onResume();

        loadingbar.show();
        getAllItems();


    }


}
