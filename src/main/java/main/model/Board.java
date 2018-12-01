package main.model;

import main.model.notes.Editable;
import main.model.notes.StickyNote;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Board implements Saveable, Loadable{

    private List<StickyNote> notes;


    // EFFECTS: construct a Board with empty note list
    public Board() {
        notes = new ArrayList<>();
    }

	// MODIFIES: this
	// EFFECTS: add note to this.notes if not null
    public void addNote(StickyNote note) {
        // insert sticky note to the list
        if(!contains(note) && note != null) {
            notes.add(note);
            note.setBoard(this);
        }
    }

    public void replaceNote(StickyNote old, StickyNote note) {
        notes.set(notes.indexOf(old), note);
    }

    // REQUIRES: id represents a valid index for notes
    // MODIFIES: this
    // EFFECTS: remove the TextNote with id in notes
	public void removeNote(StickyNote note) {
        // remove the sticky note if id is not out of range
        if(notes.contains(note) && note != null) {
            notes.remove(note);
            note.removeFromBoard();
        }
	}

	// MODIFIES: this
    // EFFECTS: remove all the TextNote in notes
    public void empty() {
        notes.clear();
    }

    // EFFECTS: Return notes
    public List<StickyNote> getNotes() {
        return notes;
    }

    @Override
    public void loadContent(String content) {
        String[] strs = content.split(";");
        StickyNote note = null;
        for(int i = 0; i < strs.length; i += 3) {
            try {
                note = (StickyNote) Class.forName(strs[i]).getConstructor().newInstance();
                note.setColor(strs[i + 1]);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            if(note instanceof Editable) {
                ((Editable) note).setContent(strs[i + 2]);
            }
            addNote(note);
        }
    }

    @Override
    public String saveContent() {
        StringBuilder content = new StringBuilder();
        for(StickyNote note: notes) {
            content.append(note.getClass().getName()).append(";");
            content.append(note.getColor()).append(";");
            if(note instanceof Editable) {
                content.append(((Editable) note).getContent());
            } else {
                content.append(" ");
            }
            content.append(";");
        }

        return content.toString();
    }

    private boolean contains(StickyNote note) {
        return notes.contains(note);
    }

    // EFFECTS: Return true if id is a valid index for notes, false if not
    private boolean isIndexValid(int id) {
        return id < notes.size() && id >= 0;
    }

}
