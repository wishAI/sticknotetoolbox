package main.gui.notepane.content;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.gui.ColorManager;
import main.model.notes.ListStickyNote;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


public abstract class ListContentPane<N extends ListStickyNote, W extends Pane, I extends Pane> extends ContentPane<N, W> {

    protected I wrapper_items;


    public ListContentPane(N note) {
        super(note);
    }

    public void switchListPane() {
        if(wrapper_items.isVisible()) {
            hideListPane();
        } else if(note.getItems().length > 0) {
            showListPane();
        }
    }

    @Override
    public void changeColor() {
        super.changeColor();

        // change color of list items
        if (wrapper_items.isVisible()) {
            switchListPane();
            switchListPane();
        }
    }

    @Override
    protected void setWrapper() {
        super.setWrapper();
        wrapper_items = (I) lookup("#wrapper_items");
        bindWrapper(wrapper_items);
    }

    protected void showListPane() {
        wrapper_items.getChildren().clear();

        wrapper_items.setVisible(true);
        wrapper.setVisible(false);

        boolean isItemLight = true;
        for (Map<String, Object> item: note.getItems()) {
            Node node = makeItemBox(item);
            node.getStyleClass().add("lb-item");
            node.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    removeItem(item);
                } else {
                    onItemClick(item);
                }
            });

            var colorHover = new Color(0, 0, 0, 0.1);
            if (isItemLight) {
                Color color = new Color(0, 0, 0,0);
                ColorManager.setHoverColorToNode(node, color, colorHover);
            } else {
                ColorManager.setHoverColorToNode(node, note.getColor(), colorHover);
            }
            isItemLight = !isItemLight;

            wrapper_items.getChildren().add(node);
        }
    }

    protected void hideListPane() {
        wrapper_items.getChildren().clear();

        wrapper_items.setVisible(false);
        wrapper.setVisible(true);
    }

    protected abstract void onItemClick(Map<String, Object> item);

    protected abstract Node makeItemBox(Map<String, Object> item);

    protected Pane makeItemBox(Class type, int height) {
        Object o = null;
        try {
            o = type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if(!(o instanceof Pane) || o == null) {
            throw new RuntimeException("The item box is initialized unproperly. ");
        }

        Pane pane = (Pane) o;
        pane.setPrefHeight(height);
        pane.prefWidthProperty().bind(wrapper_items.widthProperty());

        if (pane instanceof HBox) {
            HBox p = (HBox) pane;
            p.setAlignment(Pos.CENTER_LEFT);
        }

        return pane;
    }

    protected Label makeItemLabel(Map<String, Object> item, String name) {
        Label label = new Label(item.get(name).toString());
        label.getStyleClass().add("lb-item-" + name);
        label.setStyle("-fx-font-size: 15px");
        label.setStyle("-fx-label-padding: 0 10px 0 10px");
        return label;
    }

    private void removeItem(Map<String, Object> item) {
        note.removeItem(item);
        showListPane();
    }

}
