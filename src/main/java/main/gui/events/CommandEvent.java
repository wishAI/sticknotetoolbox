package main.gui.events;

import javafx.event.Event;
import javafx.event.EventType;


public class CommandEvent extends Event {

    public static final EventType<CommandEvent> COMMAND = new EventType<>(ANY, "COMMAND");


    private String command;
    private Object arg;


    public CommandEvent(String cmd) {
        super(COMMAND);
        this.command = cmd;
    }

    public CommandEvent(String cmd, Object arg) {
        this(cmd);
        this.arg = arg;
    }

    public String getCommand() {
        return command;
    }

    public Object getArg() {
        return arg;
    }

}
