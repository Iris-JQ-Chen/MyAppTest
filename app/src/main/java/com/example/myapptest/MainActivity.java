package com.example.myapptest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapptest.tests.FirstFragment;
import com.example.myapptest.tests.SecoundFragment;
import com.example.myapptest.tests.ThirdFragment;

public class MainActivity extends AppCompatActivity {
    private Button buttonReplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonReplace = (Button)findViewById(R.id.button_replace);
        buttonReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.control_frag_id,new ThirdFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        FirstFragment firstFragment = new FirstFragment();
        SecoundFragment secoundFragment = new SecoundFragment();
        ThirdFragment thirdFragment = new ThirdFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.control_frag_id,secoundFragment);

        fragmentTransaction.commit();
    }
}
