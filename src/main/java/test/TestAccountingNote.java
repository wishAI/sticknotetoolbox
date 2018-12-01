package test;


import main.model.exceptions.NumFormatException;
import main.model.notes.AccountingNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestAccountingNote {

    private AccountingNote note;


    @BeforeEach
    void setup() {
        note = new AccountingNote();
    }

    @Test
    void testAddAccounting() {
        try {
            note.addAccounting("chair", "20.0");
        } catch (NumFormatException e) {
            fail("");
        }

//        Map<String, Object> item = note.getItem(0);
//        assertTrue(item.get("name").equals("chair"));
//        Double value = (Double) item.get("transfer");
//        assertTrue(value < 20.1 && value > 19.9);
    }

    @Test
    void testAddAccountingWithException() {
        try {
            note.addAccounting("chair", "2a.0");
            fail("");
        } catch (NumFormatException e) {
            System.out.println("Exception catched. ");
        }
    }

}
