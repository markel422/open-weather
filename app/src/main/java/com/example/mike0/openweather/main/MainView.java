package com.example.mike0.openweather.main;

/**
 * Created by mike0 on 10/19/2017.
 */

public interface MainView {

    void showWeather(String cityName, String icon, String description, String degrees, String minDegrees, String maxDegrees);

    void cityError();
}
