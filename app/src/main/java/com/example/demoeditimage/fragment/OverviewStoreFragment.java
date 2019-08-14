package com.example.demoeditimage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demoeditimage.R;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.adapter.ShopInfoAdapter;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.interfaces.CallShopDetailListener;
import com.example.demoeditimage.model.ProductItem;
import com.example.demoeditimage.model.ShopInfo.ShopInfoOverview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewStoreFragment extends Fragment {

    @BindView(R.id.rclShopList)
    RecyclerView rclShopList;

    private ShopInfoAdapter shopInfoAdapter;
    private List<ShopInfoOverview> shopInfoOverviews = new ArrayList<>();
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

        if(shopInfoOverviews != null){
            shopInfoAdapter = new ShopInfoAdapter(shopInfoOverviews,new CallShopDetailListener() {
                @Override
                public void itemShopClick(int position) {
                    Toast.makeText(getActivity(), "Hello world!", Toast.LENGTH_SHORT).show();
                }
            });
            rclShopList.setAdapter(shopInfoAdapter);

            shopInfoAdapter.notifyDataSetChanged();
        }


        shopInfoOverviews.add(new ShopInfoOverview("baotrang",null,
                "Đã cập nhật","shopee.vn/baotrang99"));
        shopInfoOverviews.add(new ShopInfoOverview("shopee",null,
                "Đã cập nhật","shopee.vn/126468"));

        shopInfoOverviews.add(new ShopInfoOverview("abc",null,
                "Đã cập nhật","shopee.vn/126468"));
        return view;
    }

}
