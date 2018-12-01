package main.model.exceptions;


import main.model.notes.StickyNote;

public abstract class StickyNoteException extends Exception {

    public StickyNoteException(String message, StickyNote note) {
        super("The sticky note with type " + note.getClass() + " runs into an error: \n" + message);
    }

}
