package com.example.demoeditimage.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.activity.AddProductActivity;
import com.example.demoeditimage.activity.ProductDetailActivity;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.adapter.ShopInfoAdapter;
import com.example.demoeditimage.adapter.StoreAdapter;
import com.example.demoeditimage.interfaces.CallLocalAPI;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.interfaces.CallShopDetailListener;
import com.example.demoeditimage.interfaces.RequestAPI;
import com.example.demoeditimage.model.GetItemListResponse;
import com.example.demoeditimage.model.Product;
import com.example.demoeditimage.model.ProductImage;
import com.example.demoeditimage.model.ProductItem;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;


import com.example.demoeditimage.response.GetItemDetailResponse;
import com.example.demoeditimage.utils.MyConst;
import com.example.demoeditimage.utils.RetrofitClient;

import java.io.ByteArrayOutputStream;
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

    private Boolean more;

    private int offset;

    private long shopId;

    private List<ProductItem> productItemList = new ArrayList<>();

    private GetItemListResponse getItemListResponseList;

    private List<Product> mProducts = new ArrayList<>();

    private ShopInfoAdapter shopInfoAdapter;

    private List<ShopInfo> shopInfoList = new ArrayList<>();
//    private ProductItem productItem;

    private Product productItem;


    private StoreAdapter storeAdapter;

    private ProductItemAdapter productItemAdapter;

    private RetrofitClient retrofitClient = new RetrofitClient();

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

        getProducts();


        getStoreList();

        getShopList();

//        getAllItems(shopId);

//        productItemList.add(new ProductItem(1, "husky", "SKU1",
//                "Cập nhật thành công", null, null,
//                new String[]{"https://gamepedia.cursecdn.com/lolesports_gamepedia_en/d/d5/LNG_Esportslogo_square.png",
//                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwl4vk1O61TsNOfjTY1DjpmnKbNHT0xVH3an1hEjp7JeSDTkIp",
//                        "https://www.nestle.com.au/sites/g/files/pydnoa356/files/asset-library/publishingimages/miloarticle.jpg"},
//                null, 0, 0, 0, 0, 0));
//        productItemList.add(new ProductItem(2, "Doraemon", "SKU2",
//                "Cập nhật thành công", null, null,
//                new String[]{"https://gamepedia.cursecdn.com/lolesports_gamepedia_en/d/d5/LNG_Esportslogo_square.png",
//                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwl4vk1O61TsNOfjTY1DjpmnKbNHT0xVH3an1hEjp7JeSDTkIp",
//                        "https://www.nestle.com.au/sites/g/files/pydnoa356/files/asset-library/publishingimages/miloarticle.jpg"},
//                null, 0, 0, 0, 0, 0));
//
//        productItemList.add(new ProductItem(3, "Sapo", "SKU3",
//                "Cập nhật thành công", null, null,
//                new String[]{"https://gamepedia.cursecdn.com/lolesports_gamepedia_en/d/d5/LNG_Esportslogo_square.png",
//                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwl4vk1O61TsNOfjTY1DjpmnKbNHT0xVH3an1hEjp7JeSDTkIp",
//                        "https://www.nestle.com.au/sites/g/files/pydnoa356/files/asset-library/publishingimages/miloarticle.jpg"},
//                null, 0, 0, 0, 0, 0));

//        String json = new Gson().toJson(productItem);

        return view;
    }

    private void getAllItems() {
        mProducts.clear();
        more = true;
        offset = 0;
        int entries = 100;
        getItemList(entries);
    }

    private void getItemList(final int entries) {
//        int i = 0;

        HOST_URL = MyConst.getHostAddr();

        Retrofit retrofit = RetrofitClient.getClient(HOST_URL);

        RequestAPI callApi = retrofit.create(RequestAPI.class);

        String authorization = MyConst.getJwtToken();


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
//                            tvStatus.setText("Get Item List successfully: " + mProducts.size());
                        Toast.makeText(getActivity(), "Get shops successfully" + "\n" + mProducts.size(), Toast.LENGTH_SHORT).show();
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
        HOST_URL = MyConst.getHostAddr();

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
                        mProducts.set(finalI, product);
                        productItemAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<GetItemDetailResponse> call, Throwable t) {

                }
            });
        }
    }


    private void getShopList() {
        HOST_URL = MyConst.getHostAddr();

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
//                    DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
//                    Date date = new Date(shopList.get(0).getCreateDate());
//                    Log.d("TestCallAPI",simple.format(date));
//                    tvStatus.setText("Get shops successfully: " + mShops.size());

                    Toast.makeText(getActivity(), "Get shops successfully" + "\n" + shopInfoList.size(), Toast.LENGTH_SHORT).show();
                } else {
//                    tvStatus.setText("Failed: " + mShops.size());
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
//                    productItem = mProducts.get(position);
                    Toast.makeText(getActivity(), "ahii", Toast.LENGTH_SHORT).show();
//                    callProductDetailActivity();

                }
            });
            rclProductList.setAdapter(productItemAdapter);

            productItemAdapter.notifyDataSetChanged();
        }
    }

    private void getProducts() {
//        Map<String,String> parameter = new HashMap<>();
//        parameter.put("product_id","1");


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(CallLocalAPI.BASE_URL_local)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        CallLocalAPI api = retrofit.create(CallLocalAPI.class);

        CallLocalAPI api = retrofitClient.getCallLocalAPI();

        Call<List<ProductItem>> call = api.getProducts();
        call.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductItem>> call, @NonNull Response<List<ProductItem>> response) {
                if (!response.isSuccessful()) {
//                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                } else {
                    productItemList = response.body();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductItem>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Mất kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.addProductBtn)
    void clickToAddProduct() {
        callAddProductActivity();
    }


    public static String LoadImageFromWebOperations(String url) {
        try {
            final int THUMBNAIL_SIZE = 64;
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");

            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(url), THUMBNAIL_SIZE, THUMBNAIL_SIZE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();


            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }


    private void callProductDetailActivity() {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);

//        intent.putExtra("productItem", productItem);

        startActivity(intent);
    }

    private void callAddProductActivity() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        startActivity(intent);
    }

}
