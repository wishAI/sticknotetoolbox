package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import main.model.notes.Editable;
import main.model.notes.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestEditable {

    private static final String COLOR = "blue";


    private List<Editable> editables;


    public void setup() {
        editables = Arrays.asList(
                new TextNote(COLOR)
        );
    }

    @Test
    public void testAll() {
        setup();
        for(Editable editable: editables) {
            testGetContent(editable);
        }
    }

    private void testGetContent(Editable editable) {
        String content = "Hello 1";
        editable.setContent(content);

        assertTrue(editable.getContent().equals(content));
    }

    private void testSetContent(Editable editable) {
        String content = "Hello 1";
        editable.setContent(content);

        assertTrue(editable.getContent().equals(content));
    }

}
