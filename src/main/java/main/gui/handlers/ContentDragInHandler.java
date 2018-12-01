package main.gui.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import main.gui.notepane.content.ContentPane;
import main.model.notes.StickyNote;

import java.io.File;


public class ContentDragInHandler {

    private Pane proto;
    private ContentPushTarget target;
    private ContentPane pane;
    private StickyNote note;


    public ContentDragInHandler(Pane proto, ContentPushTarget target) {
        super();
        this.proto = proto;
        this.target = target;
        proto.setOnDragOver(new DragOverHandler());
        proto.setOnDragDropped(new DragDroppedHandler());
    }

    public static void setSource(Pane proto, ContentPushTarget target) {
        new ContentDragInHandler(proto, target);
    }

    public void setContentBase(ContentPane pane) {
        this.pane = pane;
        this.note = pane.getNote();
    }

    private class DragOverHandler implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            if(event.getGestureSource() != proto) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        }

    }

    private class DragDroppedHandler implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            Dragboard board = event.getDragboard();

            if(board.hasFiles()) {
                for(File file: board.getFiles()) {
                    System.out.println("File: " + file.getName());
                    target.onFilePush(file);
                }
            } else if (board.hasUrl()) {
                target.onUrlPush(board.getUrl());
            } else if(board.hasString()) {
                System.out.println("String: " + board.getString());
                target.onNonUrlTextPush(board.getString());
            } else if(board.hasImage()) {
                System.out.println("Image");
                target.onImagePush(board.getImage());
            }
        }

    }

}
