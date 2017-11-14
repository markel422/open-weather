package com.example.mike0.openweather.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mike0.openweather.R;
import com.example.mike0.openweather.data.model.WeatherAPI;

/**
 * Created by mike0 on 10/17/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private WeatherAPI weatherList;

    public WeatherAdapter(WeatherAPI weatherList) {
        this.weatherList = weatherList;
    }

    public void updateDataSet(WeatherAPI resultList) {
        this.weatherList = resultList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.weather_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cityName.setText(weatherList.getName());
        holder.temperature.setText(weatherList.getMain().getTemp().toString());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cityName;
        TextView temperature;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_tv);
            temperature = (TextView) itemView.findViewById(R.id.degrees_tv);
        }
    }
}
