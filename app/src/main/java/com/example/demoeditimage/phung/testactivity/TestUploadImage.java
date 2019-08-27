package com.example.demoeditimage.phung.testactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demoeditimage.R;
import com.example.demoeditimage.phung.CloudinaryConfig;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class TestUploadImage extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int WRITE_REQUEST_CODE = 888;
    static final int GET_FROM_PHONE_REQUEST_CODE = 888;

    static final String TAG = TestUploadImage.class.getSimpleName();

    TextView tvAddFromCamera, tvAddFromPhone;
    ImageView ivImage;

    String currentPhotoPath;

    String userId = "1";
    String shop_id = "2";
    String productCode = "3";

//    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//            "cloud_name", "sapodecor",
//            "api_key", "744836913578885",
//            "api_secret", "NelBzUIFN9StEnrxysN44rUpeH4"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upload_image);

        tvAddFromPhone = findViewById(R.id.tvAddFromPhone);
        tvAddFromCamera = findViewById(R.id.tvAddFromCamera);
        ivImage = findViewById(R.id.ivImage);

        tvAddFromPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, WRITE_REQUEST_CODE);
                }
                takePictureFromPhone();
            }
        });

        tvAddFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, WRITE_REQUEST_CODE);
                }
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Granted.
                    Log.d(TAG, "PERMISSION_GRANTED");
                }
                else{
                    //Denied.
                    Log.d(TAG, "PERMISSION_DENIED");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String id = UUID.randomUUID().toString().replace("-", "");
                new UploadToCloud().execute(userId,shop_id,productCode, id);

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                //finish();
            }
        } else if (requestCode == GET_FROM_PHONE_REQUEST_CODE ) {
            if (resultCode == RESULT_OK) {
                Uri selectedImgUri = data.getData();

                currentPhotoPath = getRealPathFromURI(selectedImgUri);

                String id = UUID.randomUUID().toString().replace("-", "");
                new UploadToCloud().execute(userId,shop_id,productCode, id);

            } else {

            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void takePictureFromPhone() {
        Intent getPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPictureIntent, GET_FROM_PHONE_REQUEST_CODE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
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


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        Uri uri = Uri.fromFile(image);
        return image;
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
            Toast.makeText(TestUploadImage.this,"Upload completed!",Toast.LENGTH_LONG).show();
            Picasso.get()
                    .load(img_url)
                    .placeholder(R.mipmap.placeholder_images)
                    .error(R.drawable.ic_error_black_24dp)
//                    .fit()
                    .into(ivImage);
        }
    }


}


