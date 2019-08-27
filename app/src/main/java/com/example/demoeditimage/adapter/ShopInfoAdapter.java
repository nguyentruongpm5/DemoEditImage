package com.example.demoeditimage.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoeditimage.R;
import com.example.demoeditimage.interfaces.CallShopDetailListener;
import com.example.demoeditimage.model.ShopInfo.ShopInfo;
import com.example.demoeditimage.model.ShopInfo.ShopInfoOverview;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShopInfoAdapter extends RecyclerView.Adapter<ShopInfoAdapter.ViewHolder>{


    private List<ShopInfo> shopInfoList;
    private CallShopDetailListener callShopDetailListener;
    private Context context;
    public ShopInfoAdapter(List<ShopInfo> shopInfoList, CallShopDetailListener callShopDetailListener) {
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        ShopInfo shopInfo = shopInfoList.get(i);

        if (shopInfo.getAvatar() != null) {
            Picasso.get()
                    .load(shopInfo.getAvatar())
                    .placeholder(R.mipmap.placeholder_images)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(viewHolder.civAvatar);
        }

        viewHolder.txtShopName.setText(shopInfo.getName());

        viewHolder.txtShopSite.setText("htttps://shopee.vn/shop/" + shopInfo.getShop_id());
//        setTextViewHTML(viewHolder.txtShopSite,"htttps://shopee.vn/shop/" + shopInfo.getShop_id());

        viewHolder.txtShopStatus.setText(shopInfo.getStatus()==1?"Đang kết nối":"Ngừng kết nối");



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setTitle("Thông báo")
                        .setMessage("Bạn có muốn truy cập shop không ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Linkify.addLinks(viewHolder.txtShopSite, Linkify.WEB_URLS);
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callShopDetailListener.itemShopClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopInfoList  == null? 0 : shopInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgShop)
        CircleImageView civAvatar;

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

    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }
    protected void setTextViewHTML(TextView text, String html)
    {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
