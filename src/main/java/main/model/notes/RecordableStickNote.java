package main.model.notes;


import java.util.Map;

public abstract class RecordableStickNote extends ListStickyNote implements Recordable {

    public void record() {
        var item = makeNewItem();
        record(item);
        items.add(item);
    }

    protected abstract void record(Map<String, Object> item);
}
