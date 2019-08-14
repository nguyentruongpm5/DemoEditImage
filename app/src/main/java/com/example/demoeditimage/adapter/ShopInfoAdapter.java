package com.example.demoeditimage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demoeditimage.R;
import com.example.demoeditimage.interfaces.CallShopDetailListener;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;
import com.example.demoeditimage.model.ShopInfo.ShopInfoOverview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopInfoAdapter extends RecyclerView.Adapter<ShopInfoAdapter.ViewHolder>{


    private List<ShopInfoOverview> shopInfoList;
    private CallShopDetailListener callShopDetailListener;

    public ShopInfoAdapter(List<ShopInfoOverview> shopInfoList, CallShopDetailListener callShopDetailListener) {
        this.shopInfoList = shopInfoList;
        this.callShopDetailListener = callShopDetailListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.content_item_store,viewGroup,false);
        ShopInfoAdapter.ViewHolder vh = new ShopInfoAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        ShopInfoOverview shopInfoOverview = shopInfoList.get(i);

        viewHolder.txtShopName.setText(shopInfoOverview.getShop_name());

        viewHolder.txtShopSite.setText(shopInfoOverview.getShop_site());

        viewHolder.txtShopStatus.setText(shopInfoOverview.getShop_status());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callShopDetailListener.itemShopClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtShopName)
        TextView txtShopName;

        @BindView(R.id.txtShopSite)
        TextView txtShopSite;

        @BindView(R.id.txtShopStatus)
        TextView txtShopStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
