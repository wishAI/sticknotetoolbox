package main.model.exceptions;

import main.model.notes.StickyNote;

public class NumFormatException extends StickyNoteException{

    public NumFormatException(String message, StickyNote note) {
        super("The format of number is not right:\n" + message, note);
    }

}
