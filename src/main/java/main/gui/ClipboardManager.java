package main.gui;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import main.gui.handlers.ContentPushTarget;

public class ClipboardManager {

    public static void replace(Object o) {
        ClipboardContent content = new ClipboardContent();
        content.putString(o.toString());
        Clipboard.getSystemClipboard().setContent(content);
    }

    public static String getText() {
        var clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString())
            return clipboard.getString();
        else if (clipboard.hasUrl())
            return clipboard.getUrl();

        return "";
    }

    public static Clipboard getClipboard() {
        return Clipboard.getSystemClipboard();
    }

    public static boolean pushContentTo(ContentPushTarget target) {
        var clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasFiles())
            target.onFilePush(clipboard.getFiles().get(0));
        else if (getText().length() > 0)
            target.onTextPush(getText());
        else if (clipboard.hasImage())
            target.onImagePush(clipboard.getImage());
        else
            return false;

        return true;
    }

}
