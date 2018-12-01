package main.service;

import main.model.notes.WeatherNote;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpService {

    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&appid=ef2b4a0da71f76b621535749a7ab54e1";


    private static HttpService httpService;


    private HttpService() {
    }

    public void requestWeather(WeatherNote note) throws Exception {
        var jsonObject = getJson(WEATHER_URL);
        String cache = jsonObject.getJSONArray("weather").get(0).toString();
        JSONObject weatherInfo = new JSONObject(cache);
        String weather = weatherInfo.getString("main");
        String description = weatherInfo.getString("description");
        double maxTemp = jsonObject.getJSONObject("main").getDouble("temp_max") - 273.15;
        double minTemp = jsonObject.getJSONObject("main").getDouble("temp_min") - 273.15;
        note.setWeatherInfo(weather, description, maxTemp, minTemp);
    }

    public static HttpService getInstance() {
        if (httpService == null) {
            httpService = new HttpService();
        }

        return httpService;
    }

    private JSONObject getJson(String urlStr) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        String response = result.toString();

        return new JSONObject(response);
    }

}
