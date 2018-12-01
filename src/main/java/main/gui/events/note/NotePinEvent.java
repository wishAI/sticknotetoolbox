package main.gui.events.note;

import javafx.event.Event;
import javafx.event.EventType;
import main.gui.notepane.NotePaneController;


public class NotePinEvent extends Event {

    public static final EventType<main.gui.events.note.NotePinEvent> NOTE_PIN_EVENT = new EventType<>(ANY, "NOTE_PIN_EVENT");


    private NotePaneController controller;


    public NotePinEvent(NotePaneController controller) {
        super(NOTE_PIN_EVENT);
        this.controller = controller;
    }

    public NotePaneController getController() {
        return controller;
    }

}
