package com.example.myapptest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int TAKE_PHOTO = 100;
    private static final int REQUEST_WE_STORAGE_PER = 1;
    private static final int REQUEST_RE_STORAGE_PER = 2;
    private static final int REQUEST_CAMERA_PER = 3;
    private Uri photoUri;
    private String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.take_photo)).setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WE_STORAGE_PER);
        }else if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_RE_STORAGE_PER);
        }else if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PER);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_photo:
                takePhoto();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    String filePath = PhotoBitmapUtils.amendRotatePhoto(fileName,MainActivity.this);
                    if (Build.VERSION.SDK_INT >= 24){
                        photoUri = FileProvider.getUriForFile(MainActivity.this,"com.example.myapptest",new File(filePath));
                    }else {
                        photoUri = Uri.fromFile(new File(filePath));
                    }

                    File file = new File(fileName);
                    if (file.exists()){
                        file.delete();
                    }

                    Bitmap bitmap= null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ((ImageView)findViewById(R.id.show_photo)).setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_WE_STORAGE_PER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"“写”内存权限已放开",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_RE_STORAGE_PER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"“读”内存权限已放开",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CAMERA_PER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"相机权限已开放",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileName = PhotoBitmapUtils.getPhotoFileName(MainActivity.this);
        File file = new File(fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24){
            fileUri = FileProvider.getUriForFile(MainActivity.this,"com.example.myapptest",file);
        }else {
            fileUri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }
}
