package com.example.myapptest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int TAKE_VIDEO = 1;

    private VideoView videoView;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView)findViewById(R.id.activity1_video1);
        findViewById(R.id.take_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_VIDEO:
                if (resultCode == RESULT_OK){
                    videoView.setVideoPath(data.getData().getPath());
                    Log.d("MyAppTest",data.getData().getPath());
                    if (!videoView.isPlaying()){
                        videoView.start();
                    }else {
                        Log.d("MyAppTest","视频正在播放");
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        videoView.suspend();
        super.onDestroy();
    }
}
