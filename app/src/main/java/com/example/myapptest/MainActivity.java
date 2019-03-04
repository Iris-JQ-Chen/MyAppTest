package com.example.myapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapptest.adapter.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mainRecyclerVeiw = (RecyclerView)findViewById(R.id.main_recycler_view);
        MainRecyclerAdapter adapter = new MainRecyclerAdapter(getStringList(10));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mainRecyclerVeiw.setAdapter(adapter);
        mainRecyclerVeiw.setLayoutManager(layoutManager);
    }

    /**
     * @param num
     * @return
     */
    private List<String> getStringList(int num){
        List<String> list = new ArrayList<>();
        for (int i = 0;i<num;i++){
            list.add("第"+i+"个MainRecyclerAdapter");
        }
        return list;
    }
}
