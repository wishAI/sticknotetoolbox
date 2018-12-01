package main.gui.notepane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.gui.events.CommandEvent;
import main.gui.handlers.DragMoveHandler;
import main.model.notes.*;


public class CommandBarController {

    @FXML private BorderPane root;
    @FXML private Button btn_record;
    @FXML private Button btn_items;
    @FXML private Button btn_close;


    Pane getRoot() {
        return root;
    }

    void updateCmdButtons(StickyNote note) {
        btn_record.setVisible(note instanceof Recordable);
        btn_items.setVisible(note instanceof ListStickyNote && !(note instanceof ClipNote));
        btn_close.setVisible(!(note instanceof CommandNote));
    }

    @FXML
    private void handleAddClick() {
        fireCommand("add");
    }

    @FXML
    private void handleColorClick() {
        fireCommand("color");
    }

    @FXML
    private void handleSwitchClick() {
        fireCommand("switch");
    }

    @FXML
    private void handleItemsClick() {
        fireCommand("items");
    }

    @FXML
    private void handleRecordClick() {
        fireCommand("record");
    }

    @FXML
    private void handlePinClick(ActionEvent actionEvent) {
        fireCommand("pin");
    }

    @FXML
    private void handleCloseClick() {
        fireCommand("close");
    }

    private void fireCommand(String cmd) {
        root.fireEvent(new CommandEvent(cmd));
    }
}
