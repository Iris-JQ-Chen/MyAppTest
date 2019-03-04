package com.example.myapptest.adapter;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapptest.R;

/**
 * Created by 蒲公英之流 on 2019-03-04.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mButton.setText("Item " + position);
        if(holder.mRecyclerView.getAdapter()==null) {
            holder.mRecyclerView.setAdapter(new GridAdapter());
        }/*else {
            holder.mRecyclerView.getAdapter().notifyDataSetChanged();
        }*/
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final AppCompatButton mButton;
        public final RecyclerView mRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mButton = (AppCompatButton) itemView.findViewById(R.id.button);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager manager = new GridLayoutManager(itemView.getContext(), 4);
            manager.setAutoMeasureEnabled(true);
            mRecyclerView.setLayoutManager(manager);
        }
    }
}