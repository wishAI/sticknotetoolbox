package main.model.notes;


import javafx.application.Platform;
import main.gui.notepane.content.ClockPane;

import java.util.*;


@View(value = ClockPane.class, isUpdatable = true, isRecordable = true)
public class ClockNote extends RecordableStickNote {

    private static Calendar time;
    private Timer timer;


    public ClockNote() {
        super();

        if(time == null) {
            time = Calendar.getInstance();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    time.setTimeInMillis(System.currentTimeMillis());
                    notifyObservers(makeTimeStr());
                });
            }
        }, 100, 100);
    }

    public static int getHour() {
        return time.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return time.get(Calendar.MINUTE);
    }

    public static int getSecond() {
        return time.get(Calendar.SECOND);
    }

    public static String getWeekDay() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return days[time.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static int getMonth() {
        return time.get(Calendar.MONTH);
    }

    public static int getDay() {
        return time.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void record(Map<String, Object> item) {
        item.put("time", makeTimeStr());
    }

    @Override
    public void onRemove() {
        super.onRemove();
        timer.cancel();
        timer.purge();
    }

    private String makeTimeStr() {
        String hour = ((Integer) ClockNote.getHour()).toString();
        String minutes = ((Integer) ClockNote.getMinute()).toString();
        String second = ((Integer) ClockNote.getSecond()).toString();
        if(hour.length() < 2)
            hour = 0 + hour;
        if(minutes.length() < 2)
            minutes = 0 + minutes;
        if(second.length() < 2)
            second = 0 + second;

        return hour + ":" + minutes + ":" + second;
    }
}
