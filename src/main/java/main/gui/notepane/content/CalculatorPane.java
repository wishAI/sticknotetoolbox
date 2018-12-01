package main.gui.notepane.content;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.gui.ClipboardManager;
import main.gui.ColorManager;
import main.gui.Injector;
import main.gui.handlers.ContentPushTarget;
import main.model.exceptions.CalculationErrorException;
import main.model.exceptions.WrongOperatorException;
import main.model.exceptions.WrongOperatorUsageException;
import main.model.notes.CalculatorNote;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class CalculatorPane extends ListContentPane<CalculatorNote, VBox, VBox> implements ContentPushTarget {

    private static final int VALUE_HEIGHT = 60;


    @FXML
    private GridPane wrapper_btn;
    @FXML
    private Label lb_value;


    public CalculatorPane(CalculatorNote note) {
        super(note);
        Injector.inject(this, "Calculator");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        wrapper_btn.prefWidthProperty().bind(root.widthProperty());
        wrapper_btn.prefHeightProperty().bind(root.heightProperty().subtract(VALUE_HEIGHT));

        lb_value.prefWidthProperty().bind(root.widthProperty());
        lb_value.setPrefHeight(VALUE_HEIGHT);
        lb_value.setAlignment(Pos.CENTER_RIGHT);

        for(Node n: wrapper_btn.getChildren()) {
            Control c = (Control) n;
            ((Control) n).setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }
    }

    @FXML
    public void handleClick(ActionEvent e) {
        // get sign and push to note
        char sign = ((Button) e.getSource()).getText().charAt(0);
        try {
            if(sign == '0') {
                note.pushOperand(0);
            } else if(sign >= '1' && sign <= '9') {
                note.pushOperand(sign - '1' + 1);
            } else {
                note.pushOperator(sign);
            }
        } catch (WrongOperatorException | CalculationErrorException | WrongOperatorUsageException ex) {
            showNotification(ex.getMessage());
        } finally {
            // change the value label
            lb_value.setText(((Integer) note.getValue()).toString());
        }
    }

    @Override
    public void onNonUrlTextPush(String text) {
        if (text.matches("\\d+")) {
            for (char c : text.toCharArray()) {
                if (c == '0')
                    note.pushOperand(0);
                else
                    note.pushOperand(c - '1' + 1);
            }
            lb_value.setText(((Integer) note.getValue()).toString());
        }
    }

    @Override
    public void changeColor() {
        super.changeColor();
        for (var btn : wrapper_btn.getChildren()) {
            ColorManager.setHoverColorToNode(btn, note.getColor(), new Color(0, 0, 0, 0));
        }
    }

    @Override
    protected void onItemClick(Map<String, Object> item) {
        copyStringToClipBoard(item.get("result"));
    }

    @Override
    protected Node makeItemBox(Map<String, Object> item) {
        HBox node = (HBox) makeItemBox(HBox.class, 50);
        node.getChildren().add(makeItemLabel(item, "result"));

        return node;
    }

}
