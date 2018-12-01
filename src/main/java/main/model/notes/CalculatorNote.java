package main.model.notes;

import main.gui.notepane.content.CalculatorPane;
import main.model.exceptions.CalculationErrorException;
import main.model.exceptions.WrongOperandException;
import main.model.exceptions.WrongOperatorUsageException;
import main.model.exceptions.WrongOperatorException;

import java.util.HashMap;
import java.util.Map;


@View(value = CalculatorPane.class, isRecordable = true)
public class CalculatorNote extends RecordableStickNote {

    private int result;
    private int operand;

    private char operator;
    private boolean isValid;
    private boolean isResult;


    public CalculatorNote() {
        super();
        result = 0;
        operand = 0;
        operator = '+';
        isResult = true;
        isValid = true;
    }

    // EFFECTS: Return the number that is shown on the calculator
    public int getValue() {
        if(isResult) {
            return result;
        }
        return operand;
    }

    // MODIFIES: this
    // EFFECTS: emulate pressing number buttons on the calculator
    //          throw wrong operand exception if operand is not possible
    public void pushOperand(int digit) throws WrongOperandException {
        if(digit < 0 || digit > 9) {
            throw new WrongOperandException();
        }

        operand = 10 * operand + digit;
        isResult = false;
        isValid = true;
    }

    // MODIFIES: this
    // EFFECTS: emulate pressing the button with sign opa on the calculator
    //          Throw WrongOperatorException if two calculation operation are pressed
    //          Throw CalculationErrorException if there is some impossible calculation
    public void pushOperator(char opa) throws WrongOperatorException, CalculationErrorException, WrongOperatorUsageException {
        if(!isValid) {
            throw new WrongOperatorUsageException(this);
        } else if(opa != '=' && opa != 'c' && opa != '+' && opa != '-' && opa != '*' && opa != '/') {
            throw new WrongOperatorException();
        }

        isValid = false;

        try {
            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    result /= operand;
                    break;
            }
        } catch (ArithmeticException e) {
            result = 0;
            operator = '+';
            isValid = true;
            throw new CalculationErrorException("The calculation cannot be performed. ", this);
        }
        operator = opa;
        if(opa == 'c') {
            result = 0;
            operator = '+';
            isValid = true;
        } else if(opa == '=') {
            operator = '+';
            isValid = true;
        }
        operand = 0;
        isResult = true;
    }

    @Override
    protected void record(Map<String, Object> item) {
        item.put("result", getValue());
    }

}

