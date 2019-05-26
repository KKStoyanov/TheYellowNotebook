package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import singletons.ExerciseData;

public class addNewExerciseController {

	@FXML
	private ComboBox comboBox;

	@FXML
	private TextField exerciseTF;

	public void processResults(ExerciseData data) {
		String category = comboBox.getSelectionModel().getSelectedItem().toString();
		String exercise = exerciseTF.getText();

		if (category.equals("Chest")) {
			data.addChestExercise(exercise);
		} else if (category.equals("Core")) {
			data.addAbExercise(exercise);
		} else if (category.equals("Arms")) {
			data.addArmExercise(exercise);
		} else if (category.equals("Legs")) {
			data.addLegExercise(exercise);
		} else if (category.equals("Back")) {
			data.addBackExercise(exercise);
		} else if (category.equals("Cardio")) {
			data.addCardioExercise(exercise);
		}
	}
}
