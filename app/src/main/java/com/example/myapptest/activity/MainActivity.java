package com.example.myapptest.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myapptest.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                new AlertDialog.Builder(this, R.style.AppMDTheme_Lignt_DarkActionBar)
                        .setTitle("Title")
                        .setMessage("Message")
                        .setPositiveButton("Ok", null)
                        .show();
        }
    }
}
