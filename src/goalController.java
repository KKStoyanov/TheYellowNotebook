
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class goalController {
	
	@FXML
	private TextField priorityTF;

    @FXML
    private TextField goalTA;
    
   @FXML
   private TextField hourTF;
   
   @FXML
   private TextField minuteTF;
   
   @FXML
   private TextField secondTF;
   
   private StringFunctions sFunctions = new StringFunctions();

    public Goal processResults(LocalDate date){
    	String priority = priorityTF.getText();
        String description = goalTA.getText();
       
        String hour = sFunctions.editString(hourTF.getText());
		String minute = sFunctions.editString(minuteTF.getText());
		String second = sFunctions.editString(secondTF.getText());
		String duration = sFunctions.stringForDuration(hour, minute, second);


        Goal goal = new Goal(description, priority, false, duration, date, "");
        return goal;
    }
    
    public boolean containsEmptyTextArea() {
    	return goalTA.getText().trim().isEmpty();
    }

    public void editGoal(Goal goal){
        goalTA.setText(goal.getDescription());
        priorityTF.setText(goal.getPriority());
        
        if (!goal.getDuration().equals("N/A")) {
			hourTF.setText(goal.getDuration().substring(0, 2));
			minuteTF.setText(goal.getDuration().substring(3, 5));
			secondTF.setText(goal.getDuration().substring(6, 8));
		}
    }

    public void updateGoal(Goal goal){
        goal.setPriority(priorityTF.getText());
        goal.setDescription(goalTA.getText());
        
        String hour = sFunctions.editString(hourTF.getText());
		String minute = sFunctions.editString(minuteTF.getText());
		String second = sFunctions.editString(secondTF.getText());
		goal.setDuration(sFunctions.stringForDuration(hour, minute, second));
    }
}
