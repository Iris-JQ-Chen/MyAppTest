package com.example.myapptest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int TAKE_VIDEO = 1;

    private static final int REQUEST_PERMISSION_WE_STORAGE = 100;

    private VideoView videoView;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView)findViewById(R.id.activity1_video1);
        findViewById(R.id.take_video).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.replay).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_video:
                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_WE_STORAGE);
                }else {
                    takeVideoAndSave();
                }
                break;
            case R.id.play:
                if (!videoView.isPlaying()){
                    videoView.start();
                    Log.d("MyAppTest","videoView is playing");
                }
                break;
            case R.id.pause:
                if (videoView.isPlaying()){
                    videoView.pause();
                    Log.d("MyAppTest","videoView has been paused");
                }
                break;
            case R.id.replay:
                if (videoView.isPlaying()){
                    videoView.resume();
                    Log.d("MyAppTest","videoView resumed");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_VIDEO:
                if (resultCode == RESULT_OK){
//                    videoView.setVideoPath(data.getData().getPath());
//                    videoView.setVideoURI(data.getData());
//                    File file = new File(getExternalCacheDir(),"output_video.mp4");
                    File file = new File(Environment.getExternalStorageDirectory(),"output_video.mp4");
                    videoView.setVideoPath(file.getPath());
//                    Log.d("MyAppTest",data.getData().getPath());
                    Log.d("MyAppTest",file.getPath());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_WE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takeVideoAndSave();
                }else {
                    Toast.makeText(MainActivity.this,"权限不足",Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    protected void onDestroy() {
        if (videoView != null){
            videoView.suspend();
        }else {
            Log.d("MyAppTest","videoView is null");
        }
        super.onDestroy();
    }

    private void takeVideoAndSave(){
        File outputVideoFile = new File(getExternalCacheDir(),"output_video.mp4");
        try{
            if (outputVideoFile.exists()){
                outputVideoFile.delete();
            }
            outputVideoFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24){
            videoUri = FileProvider.getUriForFile(MainActivity.this,"com.example.myapptest",outputVideoFile);
        }else {
            videoUri = Uri.fromFile(outputVideoFile);
        }

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,videoUri);
        startActivityForResult(intent,TAKE_VIDEO);
    }
}
