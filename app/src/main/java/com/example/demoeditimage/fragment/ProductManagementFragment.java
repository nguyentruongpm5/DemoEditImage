package com.example.demoeditimage.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoeditimage.R;
import com.example.demoeditimage.activity.AddProductActivity;
import com.example.demoeditimage.activity.ProductDetailActivity;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.model.ProductItem;

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


        productItems.add(new ProductItem(5,"husky",null,
                "Cập nhật thành công",null,null,null,
                0,0,0,0,0));
        productItems.add(new ProductItem(2,"Doraemon",null,
                "Cập nhật thành công",null,null,null,
                0,0,0,0,0));

        productItems.add(new ProductItem(3,"Sapo",null,
                "Cập nhật thành công",null,null,null,
                0,0,0,0,0));

        return view;
    }

    @OnClick(R.id.addProductBtn)
    void clickToAddProduct (){
        callAddProductActivity();
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
