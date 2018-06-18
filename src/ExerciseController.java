
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ExerciseController {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField repTF;

    @FXML
    private TextField setTF;

    @FXML
    private TextField weightTF;

    public ExerciseController(){
    }

    public Exercise processResults(String category){
        String name = comboBox.getSelectionModel().getSelectedItem();
        String reps = repTF.getText();
        String sets = setTF.getText();
        String weight = weightTF.getText();

        Exercise newExercise = new Exercise(name, reps, sets, weight, category);
        return newExercise;
    }

    //this is so the old data will show in the boxes
    public void editExercise(Exercise exercise){
        comboBox.getSelectionModel().select(exercise.getName());
        repTF.setText(exercise.getReps());
        setTF.setText(exercise.getSets());
        weightTF.setText(exercise.getWeight());
    }

    public void updateExercise(Exercise exercise){
        exercise.setName(comboBox.getSelectionModel().getSelectedItem());
        exercise.setReps(repTF.getText());
        exercise.setSets(setTF.getText());
        exercise.setWeight(weightTF.getText());
    }

}
