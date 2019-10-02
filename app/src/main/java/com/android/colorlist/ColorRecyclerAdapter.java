package com.android.colorlist;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Color> arrayList = new ArrayList<>();
    Context mContext;
    Activity mActivity;

    public ColorRecyclerAdapter(Context mContext, ArrayList<Color> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(mContext).inflate(R.layout.item_color_list, parent, false);
            return new SimpleViewHolder(v);

    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llColor;
        TextView tvColor;

        public SimpleViewHolder(View view) {
            super(view);
            llColor = view.findViewById(R.id.llColor);
            tvColor = view.findViewById(R.id.tvColor);
        }
    }



    @Override public void onBindViewHolder(final RecyclerView.ViewHolder Holder, final int pos) {

        try {


            if (Holder instanceof SimpleViewHolder) {


                SimpleViewHolder holder = ((SimpleViewHolder) Holder);

                holder.llColor.setBackgroundColor(android.graphics.Color.parseColor(arrayList.get(pos).getColorCode()));

                holder.tvColor.setText(arrayList.get(pos).getColorName());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override public int getItemCount() {

            return arrayList.size();

    }

    public void updateList(ArrayList<Color> arraylist){
        this.arrayList = arraylist;
        notifyDataSetChanged();
    }


}

