package com.example.myapptest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int CHOOSE_FILE = 100;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(CHOOSE_FILE)
                        .withIconStyle(Constant.ICON_STYLE_YELLOW)
                        .withMutilyMode(true)
                        .withTitle("选择文件")
                        .withBackgroundColor("#FFFFFF")
                        .withTitleColor("#000000")
                        .withFileFilter(new String[]{".txt",".doc",".docx"})
                        .withBackIcon(Constant.BACKICON_STYLEONE)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_FILE:
                if (resultCode == RESULT_OK){
                    List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
                    Toast.makeText(MainActivity.this,"选中了"+list.size()+"个文件",Toast.LENGTH_SHORT).show();

                    StringBuilder builder = new StringBuilder();
                    for (String str : list) {
                        builder.append(str+"\n");
                    }
                    textView.setText(builder.toString());
                }
                return;
            default:return;
        }
    }
}
