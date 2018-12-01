package main.model.exceptions;


import main.model.notes.StickyNote;

public class WrongOperatorUsageException extends StickyNoteException {

    public WrongOperatorUsageException(StickyNote note) {
        super("Please don't push a calculating operator twice. ", note);
    }

}
