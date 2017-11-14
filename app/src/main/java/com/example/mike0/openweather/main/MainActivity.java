package com.example.mike0.openweather.main;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mike0.openweather.R;
import com.example.mike0.openweather.data.model.WeatherAPI;
import com.example.mike0.openweather.data.adapter.WeatherAdapter;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = WeatherAdapter.class.getSimpleName() + "_TAG";

    SharedPreferences weatherPref;

    RecyclerView weatherRecyclerView;
    WeatherAdapter weatherAdapter;

    WeatherAPI weatherAPI;

    MainPresenterImpl presenter;

    EditText cityET;
    Button searchBtn;
    TextView cityTV;
    ImageView weatherIcon;
    TextView descripTV;
    TextView tempTV;
    TextView minTempTV;
    TextView maxTempTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        weatherRecyclerView = (RecyclerView) findViewById(R.id.recycler_weather);
        weatherAdapter = new WeatherAdapter(weatherAPI);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        weatherRecyclerView.setLayoutManager(linearLayoutManager);

        weatherRecyclerView.setAdapter(weatherAdapter);

        presenter = new MainPresenterImpl(this);

        cityTV = (TextView) findViewById(R.id.cityname_tv);
        weatherIcon = (ImageView) findViewById(R.id.icon_iv);
        descripTV = (TextView) findViewById(R.id.description_tv);
        tempTV = (TextView) findViewById(R.id.temp_tv);
        minTempTV = (TextView) findViewById(R.id.mintemp_tv);
        maxTempTV = (TextView) findViewById(R.id.maxtemp_tv);

        cityET = (EditText) findViewById(R.id.city_et);

        /*SharedPreferences.Editor editor = weatherPref.edit();
        editor.remove("cityData");
        editor.remove("iconData");
        editor.remove("descriptionData");
        editor.remove("tempData");
        editor.remove("minTempData");
        editor.remove("maxTempData");
        editor.apply();*/

        if (weatherPref.getString("cityData", "") != "" && weatherPref.getString("tempData", "") != "" &&
                weatherPref.getString("iconData", "") != "" && weatherPref.getString("descriptionData", "") != "" &&
                weatherPref.getString("minTempData", "") != "" && weatherPref.getString("maxTempData", "") != "") {
            cityTV.setText(weatherPref.getString("cityData", ""));
            Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/" + weatherPref.getString("iconData", "") + ".png").resize(200, 200).into(weatherIcon);
            descripTV.setText(weatherPref.getString("descriptionData", ""));
            tempTV.setText(weatherPref.getString("tempData", ""));
            minTempTV.setText(weatherPref.getString("minTempData", ""));
            maxTempTV.setText(weatherPref.getString("maxTempData", ""));
        }

        searchBtn = (Button) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.init();
                presenter.getWeather(cityET.getText().toString());
                cityET.setText("");
            }
        });
    }

    @Override
    public void showWeather(String cityName, String icon, String description, String degrees, String lowDegrees, String highDegrees) {
        SharedPreferences.Editor editor = weatherPref.edit();
        cityTV.setText(cityName + "\n\nToday");
        editor.putString("cityData", cityName + "\n\nToday");
        Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/" + icon + ".png").resize(200, 200).into(weatherIcon);
        editor.putString("iconData", icon);
        descripTV.setText(description);
        editor.putString("descriptionData", description);
        tempTV.setText(degrees);
        editor.putString("tempData", degrees);
        minTempTV.setText(String.format(getString(R.string.mintemp), lowDegrees));
        editor.putString("minTempData", String.format(getString(R.string.mintemp), lowDegrees));
        maxTempTV.setText(String.format(getString(R.string.maxtemp), highDegrees));
        editor.putString("maxTempData", String.format(getString(R.string.maxtemp), highDegrees));
        editor.apply();
    }

    @Override
    public void cityError() {
        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
    }

    /*public void showWeather(List<Weather> weatherList) {
        weatherAdapter.updateDataSet(weatherList);
    }*/
}
