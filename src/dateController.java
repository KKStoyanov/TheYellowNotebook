
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class dateController {

    @FXML
    private DatePicker datePicker;

    public LocalDate processDate(){
        return datePicker.getValue();
    }

    public boolean enteredInput(){
        boolean enteredInput = false;
        if (datePicker.getValue() != null)
            enteredInput = true;
        return enteredInput;
    }
}
