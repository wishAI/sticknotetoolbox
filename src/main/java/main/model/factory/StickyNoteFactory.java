package main.model.factory;

import main.model.Board;
import main.model.notes.StickyNote;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class StickyNoteFactory {

    private static final String MODEL_ROOT = "main.model.notes.";


    private static StickyNoteFactory factory;


    private StickyNoteFactory() {
    }

    private static StickyNoteFactory getInstance() {
        if(factory == null) {
            factory = new StickyNoteFactory();
        }
        return factory;
    }

    public static StickyNote getStickyNote(String type, Board board) {
        var note = getInstance().makeNoteModel(type);
        note.setBoard(board);
        return note;
    }

    public static StickyNote getStickyNote(String type, StickyNote proto) {
        var note = getStickyNote(type, proto.getBoard());
        note.setColor(proto.getColor());
        return note;
    }

    private StickyNote makeNoteModel(String name) {
        name += "Note";

        StickyNote note = null;
        try {
            Constructor c = Class.forName(MODEL_ROOT + name).getConstructor();
            note = (StickyNote) c.newInstance();
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return note;
    }

}
