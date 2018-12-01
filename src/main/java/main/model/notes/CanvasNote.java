package main.model.notes;

import javafx.scene.image.WritableImage;
import main.gui.notepane.content.CanvasPane;

@View(value = CanvasPane.class)
public class CanvasNote extends StickyNote {

    private WritableImage canvasContent;


    public CanvasNote() {
        super();
        this.canvasContent = new WritableImage(100, 100);
    }

    public void setSize(int w, int h) {
        this.canvasContent = new WritableImage(w, h);
    }

    public void setCanvasContent(WritableImage canvasContent) {
        this.canvasContent = canvasContent;
    }

    public WritableImage getCanvasContent() {
        return canvasContent;
    }

}
