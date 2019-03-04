package com.example.myapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapptest.adapter.MainRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerVeiw = (RecyclerView)findViewById(R.id.main_recycler_view);
        MainRecyclerAdapter adapter = new MainRecyclerAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mainRecyclerVeiw.setAdapter(adapter);
        mainRecyclerVeiw.setLayoutManager(layoutManager);
    }
}
