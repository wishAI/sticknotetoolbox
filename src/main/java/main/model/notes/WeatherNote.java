package main.model.notes;

import main.gui.notepane.content.WeatherPane;


@View(value = WeatherPane.class)
public class WeatherNote extends StickyNote {

    private String weather;
    private String description;
    private double maxTemp;
    private double minTemp;


    public WeatherNote() {
        super();
        weather = "sunny";
        maxTemp = 0;
        minTemp = 0;
    }

    public void setWeatherInfo(String weather, String description, double maxTemp, double minTemp) {
        this.weather = weather;
        this.description = description;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getDescription() {
        return description;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public String getWeather() {
        return weather;
    }

}
