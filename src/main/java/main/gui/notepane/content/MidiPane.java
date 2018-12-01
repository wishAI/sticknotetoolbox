package main.gui.notepane.content;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.gui.ColorManager;
import main.gui.Injector;
import main.model.notes.MidiNote;
import main.model.notes.Observer;

import javax.sound.midi.*;
import java.net.URL;
import java.util.*;


public class MidiPane extends ListContentPane<MidiNote, HBox, VBox> implements Observer {

    public static final int KEY_NUM = 10;


    @FXML private HBox box_adjustment;
    @FXML private HBox box_keyboard;
    @FXML private Label lb_instrumentIdx;

    private Synthesizer synthesizer;


    public MidiPane(MidiNote note) {
        super(note);
        Injector.inject(this,"Midi");

        try {
            synthesizer = MidiSystem.getSynthesizer();
            if(!synthesizer.isOpen())
                synthesizer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        assert synthesizer != null;

        note.addObserver(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWrapper();

        box_adjustment.setPrefHeight(50);
        box_adjustment.prefWidthProperty().bind(root.widthProperty());
        for (Node n : box_adjustment.getChildren()) {
            Region btn = ((Region) n);
            btn.prefWidthProperty().bind(box_adjustment.widthProperty().divide(3));
            btn.prefHeightProperty().bind(box_adjustment.heightProperty());
            btn.getStyleClass().add("btn-midi");
        }

        box_keyboard.prefWidthProperty().bind(root.widthProperty());
        box_keyboard.prefHeightProperty().bind(root.heightProperty().subtract(50));
        for (int i = 0; i < KEY_NUM; i ++) {
            Button btn = new Button();
            btn.prefWidthProperty().bind(box_keyboard.widthProperty().divide(KEY_NUM));
            btn.prefHeightProperty().bind(box_keyboard.heightProperty());
            btn.getStyleClass().add("btn-midi");
            btn.setOnAction(new MidiPlayHandler(i));

            box_keyboard.getChildren().add(btn);
        }

        updateInstrumentLabel();
    }

    @Override
    public void update(Object o) {
        var info = (MidiNote.MidiInfo) o;

        playMidi(info);

        var btn = (Button) box_keyboard.getChildren().get(info.idx);
        ColorManager.setColorToNode(btn, Color.web("#EEEEEE"));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ColorManager.setColorToNode(btn, new Color(0, 0, 0, 0));
                });
            }
        }, 300);
    }

    public class MidiPlayHandler implements EventHandler<ActionEvent> {

        private int keyIdx;

        MidiPlayHandler(int keyIdx) {
            this.keyIdx = keyIdx;
        }

        @Override
        public void handle(ActionEvent event) {
            MidiNote.MidiInfo info = note.pushMidiKey(keyIdx);

            playMidi(info);
        }

    }

    @Override
    protected void onItemClick(Map<String, Object> item) {
        hideListPane();
        // play the midi sequence
        note.playMidiSeqence((List<Integer>) item.get("keys"));
    }

    @Override
    protected Node makeItemBox(Map<String, Object> item) {
        var node = makeItemBox(HBox.class, 50);
        Label label = makeItemLabel(item, "index");
        label.setText("Record " + label.getText() + " -- click to play");
        node.getChildren().add(label);

        return node;
    }

    @FXML
    private void handlePrevClick() {
        note.setPrevInstrument();
        updateInstrumentLabel();
    }

    @FXML
    private void handleNextClick() {
        note.setNextInstrument();
        updateInstrumentLabel();
    }

    private void updateInstrumentLabel() {
        lb_instrumentIdx.setText(String.valueOf(note.getInstrument() + 1));
    }

    private void playMidi(MidiNote.MidiInfo info) {
        Instrument instrument = synthesizer.getDefaultSoundbank().getInstruments()[info.instrument];
        synthesizer.loadInstrument(instrument);

        MidiChannel channel = synthesizer.getChannels()[0];
        channel.programChange(instrument.getPatch().getProgram());
        channel.noteOn(info.noteNum, info.velocity);
    }

}
