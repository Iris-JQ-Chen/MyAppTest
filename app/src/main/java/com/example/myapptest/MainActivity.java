package com.example.myapptest;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView imageView = (ImageView)findViewById(R.id.image);
//        TextView textView = (TextView)findViewById(R.id.text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setTitle("滑动页面的名字");
        Glide.with(this).load(R.drawable.pic).into(imageView);
//        textView.setText("按实际发牢骚");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        MyAdapter adapter = new MyAdapter(getStringList("阿斯蒂芬加拉斯柯达"));
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private List<String> getStringList(String s){
        List<String> stringList = new ArrayList<>();
        for (int i = 0;i < 15; i++){
            StringBuilder builder = new StringBuilder();
            for (int j = 0;j < new Random().nextInt(13) + 3; j++){
                builder.append(s);
            }
            stringList.add(builder.toString());
        }
        return stringList;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;

    public MyAdapter (List<String> list){
        this.mList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        TextView mTextView;
        public ViewHolder(View itemView){
            super(itemView);
            mCardView = (CardView)itemView;
            mTextView = (TextView)(itemView.findViewById(R.id.text));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        MyAdapter.ViewHolder holder = new MyAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s = mList.get(position);
        holder.mTextView.setText(s);
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }
}
