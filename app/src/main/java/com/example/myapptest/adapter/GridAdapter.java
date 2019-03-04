package com.example.myapptest.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapptest.R;

import java.util.List;

/**
 * Created by 蒲公英之流 on 2019-03-04.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private List<String> stringList;

    public GridAdapter (List<String> stringList) { this.stringList = stringList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String string = stringList.get(position);
        holder.mTextView.setText("Grid "+string);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final AppCompatTextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView= (AppCompatTextView) itemView.findViewById(R.id.textView);
        }
    }
}
