package com.example.demoeditimage.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.demoeditimage.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {

//    private static final int MY_CAMERA_PERMISSION_CODE = 100 ;
    private static final int CAMERA_REQUEST = 52;

    @BindView(R.id.capture_image_layout)
    LinearLayout capture_image_layout;
    private int GALLERY_RESULT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
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
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(intent, GALLERY_RESULT);
                                } else {
                                    Toast.makeText(getBaseContext(),
                                            "No Gallery APP installed",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                image_view.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getBaseContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
