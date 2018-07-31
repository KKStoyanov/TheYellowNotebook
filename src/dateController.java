
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

public class dateController {

    @FXML
    private DatePicker datePicker;
    
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

    public boolean enteredInput(){
        boolean enteredInput = false;
        if (datePicker.getValue() != null)
            enteredInput = true;
        return enteredInput;
    }
}
