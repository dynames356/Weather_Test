package com.appletesting.hp.appletesting;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentItemList extends Fragment {
    private List<Forecast> myItem;
    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter myForecastAdapter;
    private MyItemRecyclerViewAdapter.ItemClickListener itemListener;
    private String forecast = "";

    public FragmentItemList(){

    }

    public FragmentItemList newInstance(String foreCast){
        FragmentItemList fragmentItemList = new FragmentItemList();
        this.forecast = foreCast;
        return fragmentItemList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myItem = new ArrayList<Forecast>();

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        try {
            JSONArray forecastArray = new JSONArray(forecast);

            for (int i = 0; i < forecastArray.length(); i++) {
                JSONObject forcastItem = forecastArray.getJSONObject(i);
                Forecast forecast1 = new Forecast();
                forecast1.setForecastDate(forcastItem.getString("date") + ", " + forcastItem.getString("day"));
                forecast1.setForecastTemp(forcastItem.getString("low") + "- " + forcastItem.getString("high"));
                forecast1.setForecastText(forcastItem.getString("text"));
                myItem.add(forecast1);
            }

            recyclerView =  view.findViewById(R.id.recyclerlist);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myForecastAdapter = new MyItemRecyclerViewAdapter(this, myItem, itemListener);
            recyclerView.setAdapter(myForecastAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyItemRecyclerViewAdapter.ItemClickListener) {
            itemListener = (MyItemRecyclerViewAdapter.ItemClickListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyItemRecyclerViewAdapter.ItemClickListener");
        }
    }
}