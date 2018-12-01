package test;

import main.model.notes.Recordable;
import main.model.notes.CalculatorNote;
import main.model.notes.ClockNote;

import java.util.Arrays;
import java.util.List;

public class TestRecordable {
    private static final String COLOR = "blue";


    private List<Recordable> recordables;


    public void setup() {
        recordables = Arrays.asList(
                new ClockNote(),
                new CalculatorNote()
        );
        for(Recordable recordable: recordables) {
            recordable.record();
        }
    }

//    @Test
//    public void testAll() {
//        setup();
//        for(Recordable recordable: recordables) {
//            testRecord(recordable);
//            testGetRecord(recordable);
//            testRemoveRecord(recordable);
//        }
//    }
//
//    private void testRecord(Recordable recordable) {
//        assertEquals(1, recordable.recordSize());
//    }
//
//    private void testGetRecord(Recordable recordable) {
//        assertNotNull(recordable.getRecord(0));
//    }
//
//    private void testRemoveRecord(Recordable recordable) {
//        recordable.removeRecord(0);
//
//        assertEquals(0, recordable.recordSize());
//    }

}
