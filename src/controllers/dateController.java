package controllers;

import java.time.LocalDate;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import objects.Day;
import singletons.DailyData;

public class dateController {

    @FXML
    private DatePicker datePicker;
    
    private SimpleBooleanProperty enteredInput = new SimpleBooleanProperty(true);
    
    public void disableDays() {
    	datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>(){
    		

			@Override
			public DateCell call(DatePicker datePicker) {
				System.out.println("looping");
				return new DateCell() {
					@Override
		    		public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
		    			for(Day day : DailyData.getInstance().getDays()) {
		    				if(item.equals(day.getDate())) {
		    					setDisable(true);
		    					setStyle("-fx-background-color: #ffc0cb;");
		    				}
		    			}
				}
			};
    		}
    	});
    }

    public LocalDate processDate(){
        return datePicker.getValue();
    }

    public BooleanBinding enteredInputProperty(){
    	return datePicker.valueProperty().isNull();
        /*if (datePicker.getValue() != null) {
            enteredInput.set(false);
            System.out.println(datePicker.getValue());
        }
        return enteredInput;*/
    }
}
