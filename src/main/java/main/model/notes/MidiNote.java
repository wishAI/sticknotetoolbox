package main.model.notes;

import javafx.application.Platform;
import main.gui.notepane.content.MidiPane;

import java.util.*;

@View(value = MidiPane.class)
public class MidiNote extends RecordableStickNote {

    public static final int INSTRUMENTS_NUM = 10;


    private List<Integer> pushedKeys;
    private int instrument;
    private int velocity;


    public MidiNote() {
        super();
        this.pushedKeys = new ArrayList<>();
        this.instrument = 0;
        this.velocity = 100;
    }

    public class MidiInfo {
        public int instrument;
        public int noteNum;
        public int velocity;
        public int idx;

        public MidiInfo(int noteNum, int velocity, int instrument) {
            this.noteNum = noteNum;
            this.velocity = velocity;
            this.instrument = instrument;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MidiInfo midiInfo = (MidiInfo) o;
            return instrument == midiInfo.instrument &&
                    noteNum == midiInfo.noteNum &&
                    velocity == midiInfo.velocity;
        }

        @Override
        public int hashCode() {
            return Objects.hash(instrument, noteNum, velocity);
        }

    }

    public MidiInfo pushMidiKey(int keyIdx) {
        MidiInfo info = new MidiInfo(noteNum(keyIdx), velocity, instrument);
        pushedKeys.add(keyIdx);
        return info;
    }

    public void playMidiSeqence(List<Integer> keys) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (count >= keys.size()) {
                        timer.cancel();
                        timer.purge();
                    } else {
                        MidiInfo info = new MidiInfo(noteNum(keys.get(count)), velocity, instrument);
                        info.idx = keys.get(count);
                        notifyObservers(info);
                        count ++;
                    }
                });
            }
        }, 500, 500);
    }

    public void setNextInstrument() {
        if(instrument < INSTRUMENTS_NUM - 1)
            instrument ++;
        else
            instrument = 0;
    }

    public void setPrevInstrument() {
        if(instrument > 0)
            instrument --;
        else
            instrument = INSTRUMENTS_NUM - 1;
    }

    public int getInstrument() {
        return instrument;
    }

    @Override
    protected void record(Map<String, Object> item) {
        item.put("index", items.size() + 1);
        item.put("keys", pushedKeys);
        pushedKeys = new ArrayList<>();
    }

    private int noteNum(int keyIdx) {
        return (keyIdx + 1) * 10;
    }

}

