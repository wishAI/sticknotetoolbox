package main.model.notes;

import main.gui.notepane.content.CommandPane;
import main.model.Board;
import main.model.factory.StickyNoteFactory;

@View(value = CommandPane.class)
public class CommandNote extends StickyNote {

    private static Board target;


    public static void setTarget(Board board) {
        target = board;
    }

    public static void addEmptyNote() {
        StickyNoteFactory.getStickyNote("Text", target);
    }

    public static void addFunctionNote(String type) {
        addFunctionNote(type, StickyNote.COLORS[0]);
    }

    public static void addFunctionNote(String type, String color) {
        StickyNoteFactory.getStickyNote(type, target).setColor(color);
    }

}
