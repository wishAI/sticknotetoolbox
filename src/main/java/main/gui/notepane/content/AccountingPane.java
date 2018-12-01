package main.gui.notepane.content;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.gui.Injector;
import main.model.exceptions.NumFormatException;
import main.model.notes.AccountingNote;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class AccountingPane extends ListContentPane<AccountingNote, VBox, VBox> {

    @FXML private VBox wrapper;
    @FXML private TextField txt_name;
    @FXML private TextField txt_trans;


    public AccountingPane(AccountingNote note) {
        super(note);
        Injector.inject(this, "Accounting");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWrapper();

        if(note.getItems().length > 0) {
            showListPane();
        }
    }

    @FXML
    private void handleAddClick(ActionEvent e) {
        try {
            note.addAccounting(txt_name.getText(), txt_trans.getText());
            showListPane();
        } catch (NumFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    protected void onItemClick(Map<String, Object> item) {
        copyStringToClipBoard(item.get("name") + "  $" + item.get("transfer"));
    }

    @Override
    protected Node makeItemBox(Map<String, Object> item) {
        BorderPane node = (BorderPane) makeItemBox(BorderPane.class, 50);
        node.prefWidthProperty().bind(this.widthProperty());
        Label name = makeItemLabel(item, "name");
        Insets insets = new Insets(10);
        name.setStyle("-fx-font-size: 15");
        BorderPane.setAlignment(name, Pos.CENTER_LEFT);
        BorderPane.setMargin(name, insets);
        Label transfer = makeItemLabel(item, "transfer");
        transfer.setStyle("-fx-font-size: 15");
        BorderPane.setAlignment(transfer, Pos.CENTER_RIGHT);
        BorderPane.setMargin(transfer, insets);
        node.setLeft(name);
        node.setRight(transfer);

        return node;
    }

}

