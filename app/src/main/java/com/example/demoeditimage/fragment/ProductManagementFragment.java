package com.example.demoeditimage.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoeditimage.R;
import com.example.demoeditimage.activity.AddProductActivity;
import com.example.demoeditimage.activity.ProductDetailActivity;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.model.ProductItem;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductManagementFragment extends Fragment {

    @BindView(R.id.rclProductList)
    RecyclerView rclProductList;

    private ProductItemAdapter productItemAdapter;
    private List<ProductItem> productItems = new ArrayList<>();
    private ProductItem productItem;
    public ProductManagementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_management, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rclProductList.setLayoutManager(layoutManager);

        if(productItems != null){
            productItemAdapter = new ProductItemAdapter(productItems, new CallProductlDetailListener() {
                @Override
                public void itemProductClick(int position) {
                    productItem = productItems.get(position);
                    callProductDetailActivity();
                }
            });
            rclProductList.setAdapter(productItemAdapter);

            productItemAdapter.notifyDataSetChanged();
        }


        productItems.add(new ProductItem(1,"husky",null,
                "Cập nhật thành công",null,null,null,
                null, 0,0,0,0,0));
        productItems.add(new ProductItem(2,"Doraemon",null,
                "Cập nhật thành công",null,null,null,
                null, 0,0,0,0,0));

        productItems.add(new ProductItem(3,"Sapo",null,
                "Cập nhật thành công",null,null,null,
                null, 0,0,0,0,0));

//        String json = new Gson().toJson(productItem);

        return view;
    }

    @OnClick(R.id.addProductBtn)
    void clickToAddProduct (){
        callAddProductActivity();
    }


    public static String LoadImageFromWebOperations(String url) {
        try {
            final int THUMBNAIL_SIZE = 64;
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");

            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(url), THUMBNAIL_SIZE, THUMBNAIL_SIZE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbImage.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    private void callProductDetailActivity() {
        Intent intent = new Intent(getActivity(),ProductDetailActivity.class);
        startActivity(intent);
    }

    private void callAddProductActivity() {
        Intent intent = new Intent(getActivity(), AddProductActivity.class);
        startActivity(intent);
    }

}
