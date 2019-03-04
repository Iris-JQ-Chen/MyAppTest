package com.example.myapptest.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapptest.R;

/**
 * Created by 蒲公英之流 on 2019-03-04.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText("Grid "+position);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final AppCompatTextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView= (AppCompatTextView) itemView.findViewById(R.id.textView);
        }
    }
}
