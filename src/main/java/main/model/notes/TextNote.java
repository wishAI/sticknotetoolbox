package main.model.notes;

import main.gui.notepane.content.TextPane;


@View(value = TextPane.class, isEditable = true)
public class TextNote extends StickyNote implements Editable {


    private String content;


	// EFFECT: construct a new sticky note with color and empty content
    public TextNote() {
        // setup content and color
        super();
        this.content = "";
    }

    // REQUIRES: color should be able to find in this.colors
	// EFFECT: construct a new sticky note with color and content
    public TextNote(String content) {
        // setup content and color
        super();
        this.content = content;
    }

    // REQUIRES: content must be a String
    // MODIFIES: this
    // EFFECTS: change content of this to parameter
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    // EFFECTS: Return the content of sticky note
    @Override
    public String getContent() {
        return content;
    }

}
