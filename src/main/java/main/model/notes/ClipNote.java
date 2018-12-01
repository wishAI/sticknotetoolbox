package main.model.notes;


import main.gui.notepane.content.ClipPane;

import java.net.MalformedURLException;
import java.net.URL;


@View(value = ClipPane.class)
public class ClipNote extends ListStickyNote {

    public ClipNote() {
        super();
    }

    public void addClip(Object content) {
        var item = makeNewItem();
        if (isUrl(content.toString()))
            item.put("type", "url");
        else
            item.put("type", "text");
        item.put("clip", content);
        items.add(item);
    }

    public static boolean isUrl(String str) {
        try {
            new URL(str);
            return true;
        } catch (MalformedURLException e) {
        }

        return false;
    }

}
