package com.example.mike0.openweather.data.api;

import com.example.mike0.openweather.data.model.WeatherAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface for fetching weather data
 */
public interface WeatherService {

    // http://api.openweathermap.org/data/2.5/weather?q=Westervile&appid=f4b882e931891660d0a2ef0bd8d7686b

    final static String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    /**
     * Get the forecast for a given zip code using {@link Call}
     */
    @GET("weather")
    Call<WeatherAPI> forecastForCityCallable(@Query("q") String cityName, @Query("appid") String key);
}