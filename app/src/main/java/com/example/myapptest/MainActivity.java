package com.example.myapptest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_CAMERA = 100;
    public static final int REQUEST_PERMISSION_WE_STORAGE = 101;

    private Uri outputFileUri;

    private static final int RECORD_VIDEO = 0;
    private static final int RECORD_VIDEO_SAVE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.take_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                }else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WE_STORAGE);
                }else {
                    takevideo();
                }
            }
        });

        findViewById(R.id.take_video_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                }else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WE_STORAGE);
                }else {
                    takevideo_save();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"相机权限已经开启",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"相机权限开启失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_PERMISSION_WE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"内存权限已经开启",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"内存权限开启失败",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void takevideo() {
        //生成Intent.
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //启动摄像头应用程序
        startActivityForResult(intent, RECORD_VIDEO);
    }

    //使用一个intent请求录像,视频存储在指定位置
    public void takevideo_save() {
        //创建输出文件
//        File file = new File(Environment.getExternalStorageDirectory(),"test.mp4");  //存放在sd卡的根目录下
        File file = new File(getExternalCacheDir(),"test.mp4");                     //放在data目录下

        if(Build.VERSION.SDK_INT >= 24){
            outputFileUri = FileProvider.getUriForFile(MainActivity.this,"com.example.cameraalbumtest.fileprovider",file);
        }else {
            outputFileUri = Uri.fromFile(file);
        }

        //生成Intent.
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        //启动摄像头应用程序
        startActivityForResult(intent, RECORD_VIDEO_SAVE);
    }

    //1、获取录制视频使用VideoView播放，2、获取视频存储地址
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        //读取直接返回的视频数据
        if (requestCode == RECORD_VIDEO) {
            VideoView videoView = (VideoView)findViewById(R.id.activity1_video1);
            Uri uri=data.getData();
            videoView.setVideoURI(uri);
            videoView.start();
            Log.d("系统录像", "直接返回视频数据"+uri.getPath()+"\t"+uri.toString());
        }
        //读取指定路径的视频文件
        else if (requestCode == RECORD_VIDEO_SAVE) {
            VideoView videoView = (VideoView)findViewById(R.id.activity1_video2);
            videoView.setKeepScreenOn(true);
            String path = outputFileUri.getPath();
            String path1=getExternalCacheDir()+"/test.mp4";
            videoView.setVideoPath(path1);
            Log.d("系统录像", path+"读取"+path1+"下的视频文件"+"\t"+data.getData().toString());
        }
    }
}
