
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

	@FXML
	private ComboBox unitCB;

	@FXML
	private TextField hourTF;

	@FXML
	private TextField minuteTF;

	@FXML
	private TextField secondTF;

	private StringFunctions sFunctions = new StringFunctions();

	public ExerciseController() {

	}

	public void updateComboBox(String category) {
		if (category.equals("P")) {
			comboBox.setItems(ExerciseData.getInstance().getChestExerciseNames());
		}else if(category.equals("C")) {
			comboBox.setItems(ExerciseData.getInstance().getAbExerciseNames());
		}else if(category.equals("B")) {
			comboBox.setItems(ExerciseData.getInstance().getBackExerciseNames());
		}else if(category.equals("A")) {
			comboBox.setItems(ExerciseData.getInstance().getArmExerciseNames());
		}else if(category.equals("L")) {
			comboBox.setItems(ExerciseData.getInstance().getLegExerciseNames());
		}else {
			comboBox.setItems(ExerciseData.getInstance().getCardioExerciseNames());
		}
	}

	public Exercise processResults(String category) {
		String name = comboBox.getSelectionModel().getSelectedItem();

		String reps = "N/A";
		if (!sFunctions.digitOnlyString(repTF.getText()).equals("")) {
			reps = sFunctions.digitOnlyString(repTF.getText());
		}
		String sets = "N/A";
		if (!sFunctions.digitOnlyString(setTF.getText()).equals("")) {
			sets = sFunctions.digitOnlyString(setTF.getText());
		}

		String weight = "N/A";
		if (!sFunctions.digitOnlyString(weightTF.getText()).equals("")) {
			weight = sFunctions.digitOnlyString(weightTF.getText())
					+ unitCB.getSelectionModel().getSelectedItem().toString();
		}

		String hour = sFunctions.editString(hourTF.getText());
		String minute = sFunctions.editString(minuteTF.getText());
		String second = sFunctions.editString(secondTF.getText());
		String time = sFunctions.stringForDuration(hour, minute, second);

		Exercise newExercise = new Exercise(name, reps, sets, weight, time, category);
		return newExercise;
	}

	/**
	 * This function returns a boolean indicating whether the combobox or any of the
	 * textfields are empty
	 * 
	 * @return
	 */
	public boolean containsEmptyFields() {
		return comboBox.getSelectionModel().isEmpty();// || repTF.getText().trim().isEmpty()
		// || setTF.getText().trim().isEmpty()); // ||
		// weightTF.getText().trim().isEmpty());
	}

	// this is so the old data will show in the boxes
	public void editExercise(Exercise exercise) {
		comboBox.getSelectionModel().select(exercise.getName());

		if (!exercise.getReps().equals("N/A")) {
			repTF.setText(exercise.getReps());
		}

		if (!exercise.getSets().equals("N/A")) {
			setTF.setText(exercise.getSets());
		}

		if (!exercise.getWeight().equals("N/A") && !exercise.getWeight().equals("Out of Range")) {
			weightTF.setText(exercise.getWeight().substring(0, exercise.getWeight().length() - 2));
			unitCB.setValue(
					exercise.getWeight().substring(exercise.getWeight().length() - 2, exercise.getWeight().length()));
		}
		if (!exercise.getTime().equals("N/A")) {
			hourTF.setText(exercise.getTime().substring(0, 2));
			minuteTF.setText(exercise.getTime().substring(3, 5));
			secondTF.setText(exercise.getTime().substring(6, 8));
		}
	}

	public void updateExercise(Exercise exercise) {
		exercise.setName(comboBox.getSelectionModel().getSelectedItem());

		exercise.setReps(repTF.getText());

		exercise.setSets(setTF.getText());

		exercise.setWeight(
				sFunctions.digitOnlyString(weightTF.getText()) + unitCB.getSelectionModel().getSelectedItem());

		String hour = sFunctions.editString(hourTF.getText());
		String minute = sFunctions.editString(minuteTF.getText());
		String second = sFunctions.editString(secondTF.getText());
		exercise.setTime(sFunctions.stringForDuration(hour, minute, second));
	}

}
