package main.gui.notepane.content;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.gui.Injector;
import main.model.notes.WeatherNote;
import main.service.HttpService;

import java.net.URL;
import java.util.ResourceBundle;

public class WeatherPane extends ContentPane<WeatherNote, VBox>  {

    private static final int BACK_SIZE = 145;


    private HttpService httpService;
    @FXML private Label lb_weather;
    @FXML private Label lb_description;
    @FXML private Label lb_temperature;
    @FXML private HBox wrapper_back;
    @FXML private Canvas canvas_back;


    public WeatherPane(WeatherNote note) {
        super(note);
        httpService = HttpService.getInstance();
        Injector.inject(this, "Weather");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        wrapper_back.prefWidthProperty().bind(wrapper.widthProperty());
        wrapper_back.prefHeightProperty().bind(wrapper.heightProperty().subtract(70));
        canvas_back.setWidth(BACK_SIZE);
        canvas_back.setHeight(BACK_SIZE);
        canvas_back.setOpacity(0.3);

        updateWeather();
    }

    private String getTempStr() {
        return "max: " + (int) note.getMaxTemp() + " C  " + "min: " + (int) note.getMinTemp() + " C";
    }

    private void updateWeather() {
        try {
            httpService.requestWeather(note);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // update labels
        lb_weather.setText(note.getWeather());
        lb_description.setText(note.getDescription());
        lb_temperature.setText(getTempStr());

        // update background
        drawBackground();
    }

    private void drawBackground() {
        var width = canvas_back.getWidth();
        var height = canvas_back.getHeight();
        var gc = canvas_back.getGraphicsContext2D();

        gc.scale(6, 6);
        gc.setFill(new Color(0.5, 0.5, 0.5, 1));
        if (note.getWeather().equals("Sunny") || note.getWeather().equals("Clear")) {
            gc.appendSVGPath("M6.76 4.84l-1.8-1.79-1.41 1.41 1.79 1.79 1.42-1.41zM4 10.5H1v2h3v-2zm9-9.95h-2V3.5h2V.55zm7.45 3.91l-1.41-1.41-1.79 1.79 1.41 1.41 1.79-1.79zm-3.21 13.7l1.79 1.8 1.41-1.41-1.8-1.79-1.4 1.4zM20 10.5v2h3v-2h-3zm-8-5c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6zm-1 16.95h2V19.5h-2v2.95zm-7.45-3.91l1.41 1.41 1.79-1.8-1.41-1.41-1.79 1.8z");
        } else {
            gc.appendSVGPath("M19.35 10.04C18.67 6.59 15.64 4 12 4 9.11 4 6.6 5.64 5.35 8.04 2.34 8.36 0 10.91 0 14c0 3.31 2.69 6 6 6h13c2.76 0 5-2.24 5-5 0-2.64-2.05-4.78-4.65-4.96z");
        }
        gc.fill();
    }

}
