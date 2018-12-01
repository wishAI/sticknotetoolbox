package main.gui.notepane.content;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.gui.Injector;
import main.gui.events.CommandEvent;
import main.model.notes.CommandNote;
import main.service.StoreService;

import java.net.URL;
import java.util.ResourceBundle;

public class CommandPane extends ContentPane<CommandNote, Pane> {

    public static final String[] NOTE_TYPES = {"Text", "Accounting", "Calculator", "Clock", "ColorPicker", "Midi", "Weather"};



    @FXML private GridPane wrapper_function;
    @FXML private TextField txt_input;


    public CommandPane(CommandNote note) {
        super(note);
        Injector.inject(this, "Command");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        for (Node n: wrapper.getChildren()) {
            if (n instanceof  Button) {
                ((Button) n).setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                n.setStyle("-fx-alignment: BOTTOM_CENTER");
            }
        }

        bindWrapper(wrapper_function);
        for (Node n : wrapper_function.getChildren()) {
            ((Button) n).setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            ((Button) n).setOnAction(event -> {
                root.fireEvent(new CommandEvent("addFunction", ((Button) n).getText()));
                switchFunctionPane();
            });
        }
    }

    @FXML
    private void handleAddEmpty() {
        fireCommand("add");
    }

    @FXML
    private void handleAddFunction() {
        switchFunctionPane();
    }

    @FXML
    private void handleSave() {
        fireCommand("save");
    }

    @FXML
    private void submitCommand() {
        String txt = txt_input.getText();
        if (txt.equals("addFunction")) {
            switchFunctionPane();
            showNotification("Success");
        } else if (txt.equals("add") || txt.equals("save")) {
            fireCommand(txt_input.getText());
            showNotification("Success");
        } else {
            showNotification("Invalid command");
        }
    }

    @FXML
    private void switchFunctionPane() {
        wrapper_function.setVisible(!wrapper_function.isVisible());
        wrapper.setVisible(!wrapper_function.isVisible());
    }

    private void fireCommand(String cmd) {
        root.fireEvent(new CommandEvent(cmd));
    }

}
