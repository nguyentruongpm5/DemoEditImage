package com.example.demoeditimage.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demoeditimage.R;
import com.example.demoeditimage.adapter.ImageProductAdapter;
import com.example.demoeditimage.adapter.ProductItemAdapter;
import com.example.demoeditimage.model.ProductImage;
import com.example.demoeditimage.model.ProductItem;
import com.example.demoeditimage.phung.CloudinaryConfig;
import com.example.demoeditimage.phung.model.response.Product;
import com.example.demoeditimage.phung.testactivity.TestUploadImage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {

    //    private static final int MY_CAMERA_PERMISSION_CODE = 100 ;
    private static final int CAMERA_REQUEST = 52;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int WRITE_REQUEST_CODE = 888;

    static final int GET_FROM_PHONE_REQUEST_CODE = 888;

    String currentPhotoPath;

    @BindView(R.id.capture_image_layout)
    LinearLayout capture_image_layout;
    private int GALLERY_RESULT = 2;


    String userId = "1";
    String shop_id = "2";
    String productCode = "3";

    private List<ProductImage> productImageList = new ArrayList<>();

    @BindView(R.id.productName_Edt)
    TextInputEditText productName_Edt;

    @BindView(R.id.skuCode_Edt)
    TextInputEditText skuCode_Edt;

    @BindView(R.id.rclImage)
    RecyclerView rclImage;

    public ProductDetailActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rclImage.setLayoutManager(layoutManager);

//        productImageList = productItem.getProduct_image_list();

        ProductItem productItem = (ProductItem) getIntent().getSerializableExtra("productItem");

        productName_Edt.setText(productItem.getProduct_name());



        String[] wordList = productItem.getProduct_image_list();

        for (String urlLink : wordList){
            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(urlLink);
            productImageList.add(productImage);
        }


        skuCode_Edt.setText(productItem.getProduct_sku());

        ImageProductAdapter imageProductAdapter = new ImageProductAdapter(productImageList);

        rclImage.setAdapter(imageProductAdapter);

        imageProductAdapter.notifyDataSetChanged();

    }



    @OnClick(R.id.btnBack)
    void clickToBack() {
        onBackPressed();
    }

    @OnClick(R.id.capture_image_layout)
    void detailProduct() {
        showDialog();
    }



    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ảnh sản phẩm");
        builder.setItems(new CharSequence[]
                        {"Chụp ảnh", "Chon từ điện thoại", "Chọn từ thư viện ảnh"},
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                break;
                            case 1:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    requestPermissions(permissions, WRITE_REQUEST_CODE);
                                }
                                dispatchTakePictureIntent();
                                break;
                            case 2:
                                Toast.makeText(getBaseContext(), "clicked 3", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        builder.setPositiveButton("Hủy", null);
        builder.create().show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.demoeditimage.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Uri uri = Uri.fromFile(image);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String id = UUID.randomUUID().toString().replace("-", "");
                new ProductDetailActivity.UploadToCloud().execute(userId,shop_id,productCode, id);

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                //finish();
            }
        } else if (requestCode == GET_FROM_PHONE_REQUEST_CODE ) {
            if (resultCode == RESULT_OK) {
                Uri selectedImgUri = data.getData();
                currentPhotoPath = getRealPathFromURI(selectedImgUri);

                String id = UUID.randomUUID().toString().replace("-", "");
                new ProductDetailActivity.UploadToCloud().execute(userId,shop_id,productCode, id);

            } else {

            }
        }
    }

    private String getRealPathFromURI(Uri selectedImgUri) {
        String result;
        Cursor cursor = getContentResolver().query(selectedImgUri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = selectedImgUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    private class UploadToCloud extends AsyncTask<String, Void, Void> {
        String img_url = null;
        @Override
        protected Void doInBackground(String... params) {
            //File to upload to cloudinary
            Cloudinary cloudinary = new Cloudinary(CloudinaryConfig.getConfig());
            try {
                String userId = params[0];
                String shop_id = params[1];
                String productCode = params[2];
                String id = params[3];
                StringBuilder path = new StringBuilder();
                path.append(userId);
                path.append("/");
                path.append(shop_id);
                path.append("/");
                path.append(productCode);
                path.append("/");
                path.append(id);
                Map options = ObjectUtils.asMap("public_id", path.toString());
                Map obj = cloudinary.uploader().upload(currentPhotoPath, options);
                img_url = obj.get("secure_url").toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... voids) {

        }

        @Override
        protected void onPostExecute(Void v) {
            if (img_url == null) {
                return;
            }
            Toast.makeText(ProductDetailActivity.this,"Upload completed!",Toast.LENGTH_LONG).show();
            Picasso.get()
                    .load(img_url)
                    .placeholder(R.mipmap.placeholder_images)
                    .error(R.drawable.ic_error_black_24dp)
//                    .fit()
                    /*.into(ivImage)*/;
        }
    }
}
