package com.example.demoeditimage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demoeditimage.R;
import com.example.demoeditimage.interfaces.CallProductlDetailListener;
import com.example.demoeditimage.model.Product;
import com.example.demoeditimage.model.ProductItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

    List<Product> productItemList;
    CallProductlDetailListener callProductlDetailListener;

    public ProductItemAdapter(List<Product> productItemList, CallProductlDetailListener callProductlDetailListener) {
        this.productItemList = productItemList;
        this.callProductlDetailListener = callProductlDetailListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.content_item_product,viewGroup,false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Product product= productItemList.get(i);

//        viewHolder.image_product.setImageBitmap();

        viewHolder.txtProductName.setText(product.getName());

        switch (product.getStatus()){
            case "NORMAL" :
                viewHolder.txtStatus.setText("Hiển thị");
                break;
            case "DELETED" :
                viewHolder.txtStatus.setText("Đã xóa");
                break;
            case "BANNED" :
                viewHolder.txtStatus.setText("Khóa");
                break;
            case "UNLIST" :
                viewHolder.txtStatus.setText("Ẩn");
                break;
        }

        if (product.getImages()!= null) {
            Picasso.get()
                    .load(product.getImages()[0])
                    .placeholder(R.mipmap.placeholder_images)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(viewHolder.image_product);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callProductlDetailListener.itemProductClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_product)
        CircleImageView image_product;

        @BindView(R.id.txtProductName)
        TextView txtProductName;

        @BindView(R.id.txtStatus)
        TextView txtStatus;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
