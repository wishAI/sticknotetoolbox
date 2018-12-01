package main.model.notes;

import main.gui.notepane.content.AccountingPane;
import main.model.exceptions.NumFormatException;


@View(value = AccountingPane.class, isRecordable = true, isEditable = true)
public class AccountingNote extends ListStickyNote {

    public AccountingNote() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: add an accounting record to items
    //          Throw a NumFormatException if transfer cannot convert to double
    public void addAccounting(String name, String transfer) throws NumFormatException {
        var map = makeNewItem();

        Double trans = 0.0;
        try {
            trans = Double.parseDouble(transfer);
        } catch(NumberFormatException e) {
            throw new NumFormatException("Cannot convert " + transfer + " to double value. ", this);
        }
        map.put("name", name);
        map.put("transfer", trans);

        items.add(map);
    }

}
