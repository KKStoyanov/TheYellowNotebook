import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class removeExerciseController {

	@FXML
	private ComboBox comboBox;

	public void updateComboBox(String category) {
		if (category.equals("P")) {
			comboBox.setItems(ExerciseData.getInstance().getChestExerciseNames());
		} else if (category.equals("C")) {
			comboBox.setItems(ExerciseData.getInstance().getAbExerciseNames());
		} else if (category.equals("B")) {
			comboBox.setItems(ExerciseData.getInstance().getBackExerciseNames());
		} else if (category.equals("A")) {
			comboBox.setItems(ExerciseData.getInstance().getArmExerciseNames());
		} else if (category.equals("L")) {
			comboBox.setItems(ExerciseData.getInstance().getLegExerciseNames());
		} else {
			comboBox.setItems(ExerciseData.getInstance().getCardioExerciseNames());
		}
	}

	public void processResults(ExerciseData data, String category) {
		String exercise = comboBox.getSelectionModel().getSelectedItem().toString();
		if (category.equals("P")) {
			data.deleteChestExercise(exercise);
		} else if (category.equals("C")) {
			data.deleteAbExercise(exercise);
		} else if (category.equals("B")) {
			data.deleteBackExercise(exercise);
		} else if (category.equals("A")) {
			data.deleteArmExercise(exercise);
		} else if (category.equals("L")) {
			data.deleteAbExercise(exercise);
		} else {
			data.deleteCardioExercise(exercise);
		}
	}
}
