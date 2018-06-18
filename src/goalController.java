
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class goalController {

    @FXML
    private TextArea goalTA;

    public Goal processResults(){
        String description = goalTA.getText();

        Goal goal = new Goal(description);
        return goal;
    }

    public void editGoal(Goal goal){
        goalTA.setText(goal.getDescription());
    }

    public void updateGoal(Goal goal){
        goal.setDescription(goalTA.getText());
    }
}
