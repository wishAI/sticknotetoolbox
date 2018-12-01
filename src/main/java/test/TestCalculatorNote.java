package test;


import main.model.exceptions.CalculationErrorException;
import main.model.exceptions.WrongOperandException;
import main.model.exceptions.WrongOperatorUsageException;
import main.model.exceptions.WrongOperatorException;
import main.model.notes.CalculatorNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class TestCalculatorNote {

    private CalculatorNote note;


    @BeforeEach
    void setup() {
        note = new CalculatorNote();
    }

    @Test
    void testConstructor() {
        assertEquals(0, note.getValue());
    }

    @Test
    void testPushOperandNonZero() {
        try {
            note.pushOperand(7);
            note.pushOperand(9);
        } catch(WrongOperandException e) {
            fail("");
        }

        assertEquals(note.getValue(), 79);
    }

    @Test
    void testPushOperandWithZero() {
        try {
            note.pushOperand(0);
            note.pushOperand(5);
            note.pushOperand(0);
        } catch(WrongOperandException e) {
            fail("");
        }

        assertEquals(note.getValue(), 50);
    }

    @Test
    void testPushOperandWithException() {
        try {
            note.pushOperand(10);
            fail("");
        } catch (WrongOperandException e) {
            System.out.println("Exception catched. ");
        }
    }

    @Test
    void testPushOperator() {
        try {
            note.pushOperand(8);
            note.pushOperator('*');
            note.pushOperand(9);
            note.pushOperator('=');

            assertEquals(note.getValue(), 72);
        } catch (WrongOperatorException e) {
            fail("");
        } catch (CalculationErrorException e) {
            fail("");
        } catch (WrongOperatorUsageException e) {
            fail("");
        } catch(WrongOperandException e) {
            fail("");
        }
    }

    @Test
    void testPushOperatorWrongOperatorException() {
        try {
            note.pushOperand(7);
            note.pushOperator('@');

            fail("");
        } catch (WrongOperatorException e) {
            System.out.println("Exception catched. ");
        } catch (CalculationErrorException e) {
            fail("");
        } catch (WrongOperatorUsageException e) {
            fail("");
        } catch(WrongOperandException e) {
            fail("");
        }
    }

    @Test
    void testPushOperatorCalculationErrorException() {
        try {
            note.pushOperand(7);
            note.pushOperator('/');
            note.pushOperand(0);
            note.pushOperator('=');

            fail("");
        } catch (WrongOperatorException e) {
            fail("");
        } catch (CalculationErrorException e) {
            System.out.println("Exception catched. ");
        } catch (WrongOperatorUsageException e) {
            fail("");
        } catch(WrongOperandException e) {
            fail("");
        }
    }

    @Test
    void testPushOperatorWrongOperatorUsageException() {
        try {
            note.pushOperand(5);
            note.pushOperator('/');
            note.pushOperator('*');

            fail("");
        } catch (WrongOperatorException e) {
            fail("");
        } catch (CalculationErrorException e) {
            fail("");
        } catch (WrongOperatorUsageException e) {
            System.out.println("Exception catched. ");
        } catch(WrongOperandException e) {
            fail("");
        }
    }

}
