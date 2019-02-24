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

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final int RECORD_AUDIO = 1;

    private Uri audioUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.record_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getExternalCacheDir(),"output_audio.amr");
                try{
                    if (file.exists()){
                        file.delete();
                    }
                    file.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    audioUri = FileProvider.getUriForFile(MainActivity.this,"com.example.myapptest.fileprovider",file);
                }else {
                    audioUri = Uri.fromFile(file);
                }
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,audioUri);
                startActivityForResult(intent,RECORD_AUDIO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case RECORD_AUDIO:
                if (resultCode == RESULT_OK){
                    Log.d("MyAppTest",audioUri.toString());
                    Log.d("MyAppTest",data.getData().toString());
                    if (audioUri.equals(data.getData())){
                        Log.d("MyAppTest","SAME");
                    }else {
                        Log.d("MyAppTest","DIFFERENCE");
                    }
                    Log.d("MyAppTest",audioUri.getPath());
                    Log.d("MyAppTest",data.getData().getPath());
                }
                return;
            default:
                return;
        }
    }
}
