package com.appletesting.hp.appletesting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private FragmentItemList mContext;
    private List<Forecast> mValues;
    private ItemClickListener itemClickListener;

    // If you don't use mContext, please remove it. It's unnecessary. T.T
    // I noticed you have empty files, remove it if you don't use it.
    public MyItemRecyclerViewAdapter(FragmentItemList context, List<Forecast> items, ItemClickListener itemClickListener ) {
        this.mContext = context;
        this.mValues = items;
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onTransferPosition(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_forecastdate.setText(mValues.get(position).getForecastDate());
        holder.tv_forecasttemp.setText(mValues.get(position).getForecastTemp());
        holder.tv_forecasttext.setText(mValues.get(position).getForecastText());

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instead of passing position, you can just pass the mvalues.get(position)
                // otherwise, you still have to do the JSONArray.get something something.
                // Please don't make your life miserable.
                itemClickListener.onTransferPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_forecastdate;
        private TextView tv_forecasttemp;
        private TextView tv_forecasttext;
        // There are cases when you just want part of your item list clickable, however in this case it's the whole item list that's clickable.
        // So please use View instead of LinearLayout, granted they make no difference, however you are importing the LinearLayout Library. Which is also a waste of resources.
        private LinearLayout item_layout;

        public ViewHolder(View view) {
            super(view);

            tv_forecastdate = view.findViewById(R.id.tv_forecastdate);
            tv_forecasttemp = view.findViewById(R.id.tv_forecasttemp);
            tv_forecasttext = view.findViewById(R.id.tv_forecasttext);
            item_layout = view.findViewById(R.id.item_layout);

        }
    }
}
