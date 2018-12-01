package main.model.notes;

import java.util.HashMap;
import java.util.Map;


public abstract class ListStickyNote extends StickyNote {

    protected NoteList items;


    // EFFECTS: create an new list note instance
    public ListStickyNote() {
        super();
        items = new NoteList();
    }

    public Map<String, Object>[] getItems() {
        return items.toArray();
    }

    public void removeItem(Map<String, Object> item) {
        items.remove(item);
    }

    protected Map<String, Object> makeNewItem() {
        return new HashMap<>();
    }

}
