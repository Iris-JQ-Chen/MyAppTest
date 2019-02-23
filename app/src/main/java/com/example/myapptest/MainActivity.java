package com.example.myapptest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_PERMISSION_WE_STORAGE = 100;

    private static final int OPEN_FILE_FOR_URI = 200;

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.buttonPanel);
        textView = (TextView)findViewById(R.id.text);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPanel:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_WE_STORAGE);
                }else {
                    openSpecialFile();
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
                    openSpecialFile();
                }else {
                    Toast.makeText(MainActivity.this,"权限不足无法打开文件夹",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case OPEN_FILE_FOR_URI:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    textView.setText(uri.toString());
                }
                break;
            default:
                break;
        }
    }

    private void openSpecialFile(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
        intent.setType("audio/*");
//        intent.setType("video/*");
        startActivityForResult(intent,OPEN_FILE_FOR_URI);
    }
}
