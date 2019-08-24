package com.example.demoeditimage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demoeditimage.R;
import com.example.demoeditimage.interfaces.CallShopDetailListener;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreAdapter extends BaseAdapter {

    private Context context;
    private int spnStoreLayout;
    private List<ShopInfo> shopInfoList;

    @BindView(R.id.txtStoreName)
    TextView txtStoreName;

    public StoreAdapter(Context context, int spnStoreLayout, List<ShopInfo> shopInfoList) {
        this.context = context;
        this.spnStoreLayout = spnStoreLayout;
        this.shopInfoList = shopInfoList;

    }

    @Override
    public int getCount() {
        return shopInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(spnStoreLayout,null);

        ButterKnife.bind(this,convertView);

        txtStoreName.setText(shopInfoList.get(position).getName());

        return convertView;
    }


}
