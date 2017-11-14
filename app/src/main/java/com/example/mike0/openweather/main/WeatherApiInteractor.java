package com.example.mike0.openweather.main;

import android.util.Log;

import com.example.mike0.openweather.data.api.WeatherService;
import com.example.mike0.openweather.data.model.WeatherAPI;
import com.example.mike0.openweather.data.adapter.WeatherAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mike0 on 10/19/2017.
 */

public class WeatherApiInteractor {

    private static final String TAG = WeatherAdapter.class.getSimpleName() + "_TAG";

    final static String API_KEY = "f4b882e931891660d0a2ef0bd8d7686b";

    private OnWeatherResponseListener listener;

    WeatherService service;

    public interface OnWeatherResponseListener {
        void onWeatherResponseDone(String cityName, String icon, String description, String degrees, String lowDegrees, String highDegrees);

        void onWeatherResponseError();
    }

    public void setOnWeatherResponseListener(OnWeatherResponseListener listener) {
        this.listener = listener;
    }

    public void getService() {
        service = new Retrofit.Builder()
                .baseUrl(WeatherService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService.class);
    }

    public void getWeather(String cityName) {
        service.forecastForCityCallable(cityName, API_KEY).enqueue(new Callback<WeatherAPI>() {
            @Override
            public void onResponse(Call<WeatherAPI> call, Response<WeatherAPI> response) {
                if (response.isSuccessful()) {
                    String cityResponse = response.body().getName() + ", " + response.body().getSys().getCountry();

                    String weatherIcon = response.body().getWeather().get(0).getIcon();
                    String descriptRes = response.body().getWeather().get(0).getDescription();

                    double kelToFah = (9.0 / 5.0) * (response.body().getMain().getTemp() - 273.15) + 32;
                    int fahrenheit = (int) kelToFah;
                    String tempDegrees = String.valueOf(fahrenheit) + "\u00B0";
                    double kelToFahMin = (9.0 / 5.0) * (response.body().getMain().getTempMin() - 273.15) + 32;
                    fahrenheit = (int) kelToFahMin;
                    String tempDegreesMin = String.valueOf(fahrenheit) + "\u00B0";
                    double kelToFahMax = (9.0 / 5.0) * (response.body().getMain().getTempMax() - 273.15) + 32;
                    fahrenheit = (int) kelToFahMax;
                    String tempDegreesMax = String.valueOf(fahrenheit) + "\u00B0";

                    listener.onWeatherResponseDone(cityResponse, weatherIcon, descriptRes, tempDegrees, tempDegreesMin, tempDegreesMax);
                    //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
                }
            }

            @Override
            public void onFailure(Call<WeatherAPI> call, Throwable t) {
                listener.onWeatherResponseError();
                t.printStackTrace();
            }
        });
    }

}
