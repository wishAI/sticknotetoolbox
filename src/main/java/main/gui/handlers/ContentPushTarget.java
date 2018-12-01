package main.gui.handlers;

import javafx.scene.image.Image;

import java.io.File;

public interface ContentPushTarget {

    default void onUrlPush(String url) {
        onTextPush(url);
    }

    default void onTextPush(String string) {}

    default void onNonUrlTextPush(String text) {
        onTextPush(text);
    }

    default void onFilePush(File file) {}

    default void onImagePush(Image image) {}

}
