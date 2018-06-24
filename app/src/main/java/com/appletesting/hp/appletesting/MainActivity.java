package com.appletesting.hp.appletesting;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements  MyItemRecyclerViewAdapter.ItemClickListener{
    TextView tv_location, tv_codition, tv_conditionTemp, tv_conditionWeather;
    RequestQueue mQueue;
    JSONArray forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_location = findViewById(R.id.tv_location);
        tv_codition = findViewById(R.id.tv_condition);
        tv_conditionTemp = findViewById(R.id.tv_conditiontemp);
        tv_conditionWeather = findViewById(R.id.tv_conditionweather);

        mQueue = Volley.newRequestQueue(this);
        mQueue.start();
    }
    @Override
    protected void onResume() {
        super.onResume();

        if(findViewById(R.id.fragment_container) != null){
            String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22singapore%2C%20sg%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Sometimes creating more variables can make your application slow. Try to minimize the creation of new variable if possible.
                                // However, do note that you have to balance between readability and the refactoring of your code.
                                // For example below, you can simply get the channel directly using response.getJSONObject("query").getJSONObject("results").getJSONObject("channel");
                                // Also please give meaninful variable name, you're not the only one who read the code. T.T
                                JSONObject jsonObject= response.getJSONObject("query");

                                JSONObject results = jsonObject.getJSONObject("results");
                                JSONObject channel = results.getJSONObject("channel");

                                JSONObject location = channel.getJSONObject("location");
                                String city = location.getString("city");
                                String country = location.getString("country");

                                JSONObject item = channel.getJSONObject("item");
                                JSONObject condition = item.getJSONObject("condition");
                                String date = condition.getString("date");
                                String temp = condition.getString("temp");
                                String text = condition.getString("text");

                                tv_location.setText(city + ", " + country);
                                tv_codition.setText(date);
                                tv_conditionTemp.setText(temp);
                                tv_conditionWeather.setText(text);

                                forecast = item.getJSONArray("forecast");
                                
                                // Why you need a bundle??
                                Bundle bundle = new Bundle();
                                bundle.putString("forecast", forecast.toString());
                                FragmentItemList fragmentItemList = new FragmentItemList();
                                // Dear Jia Ping, instead of passing a string, you can just simply pass the JSONArray into the newInstance or better yet create the List<Forecast> myItem here.
                                // Why you want to make your life so miserable. T.T
                                fragmentItemList.newInstance(forecast.toString());
                                // Why you ADD FRAGMENT instead of REPLACE!?? FML, I'll explain to you at the Office.
                                FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentItemList,null);
                                fragmentTransaction.commit();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(jsonObjectRequest);
        }
    }


    @Override
    public void onTransferPosition(int position) {
        long startTime = System.currentTimeMillis();
        JSONObject forcastItem = null;
        tv_codition.setText("");
        tv_conditionTemp.setText("");
        tv_conditionWeather.setText("");
        try {
            // You know, instead of using getJSONObject, why not when you get the JSONArray, you convert it to List<Forecast> myItem instead.
            forcastItem = forecast.getJSONObject(position);
            int temp = Integer.parseInt(forcastItem.getString("low"))+ Integer.parseInt(forcastItem.getString("high"));
            tv_codition.setText(forcastItem.getString("date")  + ", " + forcastItem.getString("day"));
            tv_conditionTemp.setText(String.valueOf(temp/2));
            tv_conditionWeather.setText(forcastItem.getString("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("MainActivity", "onTransferPosition time: " + (System.currentTimeMillis() - startTime));
    }
}
