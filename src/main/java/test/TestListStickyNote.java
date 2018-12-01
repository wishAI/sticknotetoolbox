package test;

import main.model.notes.ListStickyNote;
import main.model.exceptions.NoteImpossibleItemException;
import main.model.notes.AccountingNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class TestListStickyNote {

    private ListStickyNote note;

    @BeforeEach
    void setup() {
        note = new AccountingNote();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("name", "chair");
        item1.put("transfer", 20.0);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("name", "desk");
        item2.put("transfer", -15.0);
//        ((AccountingNote) note).addItem(item1);
//        ((AccountingNote) note).addItem(item2);
    }
//
//    @Test
//    void testGetItem() {
//        assertEquals(note.itemsSize(), 2);
//
//        try {
//            Map<String, Object> item1 = note.getItem(0);
//            Map<String, Object> item2 = note.getItem(1);
//            assertTrue(item1.get("name").equals("chair"));
//            assertTrue(item2.get("name").equals("desk"));
//
//            Double value = (Double) item1.get("transfer");
//            assertTrue(value < 20.1 && value > 19.9);
//            value = (Double) item2.get("transfer");
//            assertTrue(value < -14.9 && value > -15.1);
//        } catch(NoteImpossibleItemException e) {
//            fail("");
//        }
//    }
//
//    @Test
//    void testGetItemImpossibleItemException() {
//        assertEquals(note.itemsSize(), 2);
//
//        try {
//            note.getItem(2);
//            fail("");
//        } catch(NoteImpossibleItemException e) {
//            System.out.println("Exception catched. ");
//        }
//    }

}
