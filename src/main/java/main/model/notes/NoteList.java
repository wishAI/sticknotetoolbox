package main.model.notes;

import main.model.exceptions.NoteImpossibleItemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NoteList {

    private List<Map<String, Object>> items;


    public NoteList() {
        items = new ArrayList<>();
    }

    // EFFECTS: Return the item with the given index
    //          Throw NoteImpossibleItemException if index is not valid
    public Map<String, Object> get(int i) throws NoteImpossibleItemException {
        if(isInvalidIndex(i)) {
            throw new NoteImpossibleItemException(i);
        }
        return items.get(i);
    }

    public void add(Map<String, Object> item) {
        items.add(item);
    }

    // MODIFIES: this
    // EFFECTS: remove the item with given index
    //          Throw NoteImpossibleItemException if index is not valid
    protected void remove(int i) throws NoteImpossibleItemException {
        if(isInvalidIndex(i)) {
            throw new NoteImpossibleItemException(i);
        }
        items.remove(i);
    }

    protected void remove(Map<String, Object> item) throws NoteImpossibleItemException {
        items.remove(item);
    }

    // EFFECTS: Return size of items
    public int size() {
        return items.size();
    }

    public Map<String, Object>[] toArray() {
        return items.toArray(new HashMap[items.size()]);
    }

    private boolean isInvalidIndex(int i) {
        return i < 0 || i >= items.size();
    }

}
