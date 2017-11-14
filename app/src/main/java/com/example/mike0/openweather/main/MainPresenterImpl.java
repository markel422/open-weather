package com.example.mike0.openweather.main;

/**
 * Created by mike0 on 10/19/2017.
 */

public class MainPresenterImpl implements MainPresenter, WeatherApiInteractor.OnWeatherResponseListener {

    MainView mainView;

    WeatherApiInteractor interactor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;

        interactor = new WeatherApiInteractor();
    }

    public void init() {
        interactor.setOnWeatherResponseListener(this);
        interactor.getService();
    }

    @Override
    public void getWeather(String city) {
        interactor.getWeather(city);
    }

    @Override
    public void onWeatherResponseDone(String cityName, String icon, String description, String degrees, String lowDegrees, String highDegrees) {
        mainView.showWeather(cityName, icon, description, degrees, lowDegrees, highDegrees);
    }

    @Override
    public void onWeatherResponseError() {
        mainView.cityError();
    }
}
