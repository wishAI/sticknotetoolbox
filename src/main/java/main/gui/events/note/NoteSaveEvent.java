package main.gui.events.note;

import javafx.event.Event;
import javafx.event.EventType;


public class NoteSaveEvent extends Event {

    public static final EventType<NoteSaveEvent> NOTE_SAVE_EVENT = new EventType<>(ANY, "NOTE_SAVE_EVENT");


    public NoteSaveEvent() {
        super(NOTE_SAVE_EVENT);
    }

}
