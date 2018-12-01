package main.gui.notepane.content;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.gui.Injector;
import main.model.notes.Observer;
import main.model.notes.ClockNote;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class ClockPane extends ListContentPane<ClockNote, VBox, VBox> implements Observer {

    public static final int TIME_WIDTH = 150;
    public static final int TIME_HEIGHT = 40;
    public static final int BACK_SIZE = 190;


    @FXML private VBox wrapper_time;
    @FXML private Label lb_time;
    @FXML private Pane box_calendar;
    @FXML private Label lb_week;
    @FXML private Label lb_calendar;
    @FXML private VBox wrapper_back;
    @FXML private Canvas canvas_back;


    public ClockPane(ClockNote note) {
        super(note);
        Injector.inject(this, "Clock");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        wrapper_time.setMaxWidth(TIME_WIDTH);
        wrapper_time.setLayoutX((double) (WIDTH - TIME_WIDTH) / 2);
        wrapper_time.setLayoutY((double) (HEIGHT - TIME_HEIGHT) / 2 - 20);
        box_calendar.prefWidthProperty().bind(wrapper_time.widthProperty());
        wrapper_items.prefWidthProperty().bind(root.widthProperty().divide(2));
        wrapper_items.setLayoutY(0);
        wrapper_items.setLayoutX(0);
        wrapper_items.translateXProperty().bind(root.widthProperty().divide(2));

        wrapper_back.prefWidthProperty().bind(wrapper.widthProperty());
        wrapper_back.prefHeightProperty().bind(wrapper.heightProperty());
        canvas_back.setOpacity(0.3);
        canvas_back.setWidth(BACK_SIZE);
        canvas_back.setHeight(BACK_SIZE);
        drawBackground();

        note.addObserver(this);
    }

    @Override
    public void update(Object o) {
        lb_time.setText((String) o);
        lb_week.setText("  " + ClockNote.getWeekDay());
        lb_calendar.setText(ClockNote.getMonth() + "/" + ClockNote.getDay() + "  ");
    }

    @Override
    protected void onItemClick(Map<String, Object> item) {
        copyStringToClipBoard(item.get("time"));
    }

    @Override
    public void showListPane() {
        super.showListPane();
        wrapper.prefWidthProperty().bind(this.widthProperty().divide(2));
        wrapper.setVisible(true);
        wrapper_time.setMaxWidth(TIME_WIDTH / 2);
        lb_time.setStyle("-fx-font-size: 17;");
        canvas_back.setWidth(BACK_SIZE / 2 + 10);
        canvas_back.setHeight(BACK_SIZE / 2);
        drawBackground();
    }

    @Override
    protected void hideListPane() {
        super.hideListPane();
        wrapper.prefWidthProperty().bind(this.widthProperty());
        wrapper_time.setMaxWidth(TIME_WIDTH);
        lb_time.setStyle("-fx-font-size: 30;");
        canvas_back.setWidth(BACK_SIZE);
        canvas_back.setHeight(BACK_SIZE);
        drawBackground();
    }

    @Override
    protected Node makeItemBox(Map<String, Object> item) {
        HBox node = (HBox) makeItemBox(HBox.class, 50);
        node.getChildren().add(makeItemLabel(item, "time"));

        return node;
    }

    private void drawBackground() {
        var size = canvas_back.getHeight();
        var dayPercent = 1 - ClockNote.getHour() / 24.0 - ClockNote.getMinute() / 1440.0;
        assert dayPercent <= 1 && dayPercent >= 0;

        var gc = canvas_back.getGraphicsContext2D();
        gc.clearRect(0, 0, size + 10, size);
        int lw = 7;
        gc.setFill(new Color(0.5, 0.5, 0.5, 1));
        gc.setStroke(new Color(0.5, 0.5, 0.5, 1));
        gc.setLineWidth(lw);
        gc.strokeOval(lw, lw, size - lw * 2, size - lw * 2);
        gc.setLineWidth(2);
        gc.beginPath();
        gc.moveTo(size / 2, lw);
        gc.bezierCurveTo(dayPercent * size, lw / 2 , dayPercent * size, size - lw / 2, size / 2, size - lw / 2);
        gc.bezierCurveTo(size - lw / 2 + 23, size - lw / 2, size - lw / 2 + 23, lw / 2, size / 2, lw / 2);
        gc.closePath();
        gc.fill();
    }

}
