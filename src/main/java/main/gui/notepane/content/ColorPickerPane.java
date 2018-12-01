package main.gui.notepane.content;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import main.gui.ColorManager;
import main.gui.Injector;
import main.gui.handlers.ContentPushTarget;
import main.model.notes.ColorPickerNote;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ColorPickerPane extends ListContentPane<ColorPickerNote, BorderPane, VBox> implements ContentPushTarget {

    private static final int COLOR_SIZE = 130;
    private static final int COLOR_BAR_WIDTH = 20;

    @FXML private HBox wrapper_color;
    @FXML private Canvas canvas_color;
    @FXML private Canvas canvas_hue;
    @FXML private Canvas canvas_saturation;
    @FXML private Canvas canvas_brightness;
    @FXML private Button btn_copy;
    @FXML private Pane wrapper_back;


    public ColorPickerPane(ColorPickerNote note) {
        super(note);
        Injector.inject(this, "ColorPicker");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWrapper();

        wrapper_color.prefWidthProperty().bind(wrapper.widthProperty());

        canvas_color.setWidth(COLOR_SIZE);
        canvas_color.setHeight(COLOR_SIZE);
        drawColor();

        setupColorBar(canvas_hue);
        drawHue();
        canvas_hue.setOnMouseDragged(event -> {
            double hue = event.getY() / COLOR_SIZE * 360;
            note.setHue(hue);
            updateColor();
        });

        setupColorBar(canvas_saturation);
        drawSaturation();
        canvas_saturation.setOnMouseDragged(event -> {
            double saturation = event.getY() / COLOR_SIZE;
            note.setSaturation(saturation);
            updateColor();
        });

        setupColorBar(canvas_brightness);
        drawBrightness();
        canvas_brightness.setOnMouseDragged(event -> {
            double brightness= event.getY() / COLOR_SIZE;
            note.setBrightness(brightness);
            updateColor();
        });

        btn_copy.prefWidthProperty().bind(wrapper.widthProperty());
        btn_copy.setPrefHeight(40);

        wrapper_back.prefWidthProperty().bind(wrapper.widthProperty());
    }

    @Override
    public void changeColor() {
        super.changeColor();
        ColorManager.setColorToNode(wrapper_back, note.getColor());
    }

    @FXML
    public void handleCopy() {
        copyStringToClipBoard(note.getHexValue());
    }

    @Override
    public void onNonUrlTextPush(String hex) {
        hex = hex.trim();
        if (ColorPickerNote.isHexCode(hex)) {
            note.setHexValue(hex);
            updateColor();
        }
    }

    @Override
    protected Node makeItemBox(Map<String, Object> item) {
        BorderPane node = (BorderPane) makeItemBox(BorderPane.class, 50);
        node.prefWidthProperty().bind(this.widthProperty());
        Label name = makeItemLabel(item, "hex");
        Insets insets = new Insets(10);
        name.setStyle("-fx-font-size: 15");
        BorderPane.setAlignment(name, Pos.CENTER_LEFT);
        BorderPane.setMargin(name, insets);
        Color color = (Color) item.get("color");
        Pane pane = new Pane();
        pane.setPrefWidth(50);
        ColorManager.setColorToNode(pane, color);
        node.setLeft(name);
        node.setRight(pane);

        return node;
    }

    @Override
    protected void showListPane() {
        super.showListPane();
        wrapper_back.setVisible(false);
    }

    @Override
    protected void hideListPane() {
        super.hideListPane();
        wrapper_back.setVisible(true);
    }

    @Override
    protected void onItemClick(Map<String, Object> item) {
        note.setHexValue((String) item.get("hex"));
        updateColor();
        switchListPane();
    }

    private void updateColor() {
        drawColor();
        drawSaturation();
        drawBrightness();
    }

    private void drawColor() {
        GraphicsContext gc = canvas_color.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, COLOR_SIZE, COLOR_SIZE);
        gc.setFill(Color.BLACK);

        gc.beginPath();
        gc.lineTo(0, 0);
        gc.lineTo(0, COLOR_SIZE);
        gc.lineTo(COLOR_SIZE, COLOR_SIZE);
        gc.closePath();
        gc.fill();

        gc.setFill(note.getCurColor());
        gc.fillRect(20, 20, COLOR_SIZE - 40, COLOR_SIZE - 40);
    }

    private void drawHue() {
        GraphicsContext gc = canvas_hue.getGraphicsContext2D();
        gc.setLineWidth(1);
        for (int i = 0; i < COLOR_SIZE; i ++) {
            Color fill = Color.hsb(i * (360.0 / COLOR_SIZE), 1, 1);
            gc.setStroke(fill);
            gc.setLineWidth(1);
            gc.strokeLine(0, i, COLOR_BAR_WIDTH, i);
        }
    }

    private void drawSaturation() {
        GraphicsContext gc = canvas_saturation.getGraphicsContext2D();
        Color stopColor0 = Color.hsb(note.getCurColor().getHue(), 0, note.getCurColor().getBrightness());
        Color stopColor1 = Color.hsb(note.getCurColor().getHue(), 1, note.getCurColor().getBrightness());
        Stop[] stops = {new Stop(0, stopColor0), new Stop(1, stopColor1)};
        LinearGradient gradient = new LinearGradient(COLOR_BAR_WIDTH / 2, 0, COLOR_BAR_WIDTH / 2, COLOR_SIZE, false, CycleMethod.NO_CYCLE, stops);
        gc.setFill(gradient);
        gc.fillRect(0, 0, COLOR_BAR_WIDTH, COLOR_SIZE);
    }

    private void drawBrightness() {
        GraphicsContext gc = canvas_brightness.getGraphicsContext2D();
        Color stopColor0 = Color.hsb(note.getCurColor().getHue(), note.getCurColor().getSaturation(), 0);
        Color stopColor1 = Color.hsb(note.getCurColor().getHue(), note.getCurColor().getSaturation(), 1);
        Stop[] stops = {new Stop(0, stopColor0), new Stop(1, stopColor1)};
        LinearGradient gradient = new LinearGradient(COLOR_BAR_WIDTH / 2, 0, COLOR_BAR_WIDTH / 2, COLOR_SIZE, false, CycleMethod.NO_CYCLE, stops);
        gc.setFill(gradient);
        gc.fillRect(0, 0, COLOR_BAR_WIDTH, COLOR_SIZE);
    }

    private void setupColorBar(Canvas canvas) {
//        canvas.setFocusTraversable(true);
        canvas.setHeight(COLOR_SIZE);
        canvas.setWidth(COLOR_BAR_WIDTH);
        canvas.prefHeight(COLOR_SIZE);
        canvas.prefWidth(COLOR_BAR_WIDTH);
    }

}
