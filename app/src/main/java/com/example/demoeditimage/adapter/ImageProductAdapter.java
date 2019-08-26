package com.example.demoeditimage.adapter;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.demoeditimage.R;
import com.example.demoeditimage.model.ProductImage;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageProductAdapter extends RecyclerView.Adapter<ImageProductAdapter.ViewHolder> {


    List<ProductImage> productImages;
    private ProgressDialog loadingbar;

    public ImageProductAdapter(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_item_image,viewGroup, false);

        ViewHolder vh = new ViewHolder(itemView);

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProductImage productImage = productImages.get(i);

        new DownloadImageTask(viewHolder.imgItemProduct).execute(productImage.getImageUrl());

    }

    @Override
    public int getItemCount() {
        return productImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgItemProduct)
        ImageView imgItemProduct;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
