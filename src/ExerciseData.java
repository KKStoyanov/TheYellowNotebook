
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExerciseData {
	private static ExerciseData instance = new ExerciseData();

	private ObservableList<String> chestExerciseNames;
	private ObservableList<String> abExerciseNames;
	private ObservableList<String> armExerciseNames;
	private ObservableList<String> legExerciseNames;
	private ObservableList<String> cardioExerciseNames;
	private ObservableList<String> backExerciseNames;

	private ExerciseData() {
		chestExerciseNames = FXCollections.observableArrayList("Push Ups", "Declined Push Ups", "Inclined Push Ups",
				"Declined Narrow Push Ups");
		abExerciseNames = FXCollections.observableArrayList("Leg Raises", "Hanging Leg Raises", "Flutterkicks",
				"Cutting Edge", "Torso Rotation", "Side Jackknives", "Cable Rotations",
				"Standing Cable Oblique Crunches", "Crunches", "Sit-Ups");
		armExerciseNames = FXCollections.observableArrayList("Tricep Dips", "Tricep Extensions", "Overhand Cable Curls",
				"Barbell Curls", "Preacher Curl", "Cable Curls", "Dumbbell Curls", "Shoulder Press", "Lateral Raises",
				"Hammer Curls", "Military Presses", "Chin Ups");
		legExerciseNames = FXCollections.observableArrayList("Squats", "Bulgarian Split Squats", "Hack Squats",
				"Leg Presses", "Box Jumps", "Calf Raises");
		cardioExerciseNames = FXCollections.observableArrayList("Running", "Swimming", "Basketball");
		backExerciseNames = FXCollections.observableArrayList("Deadlifts", "Overhand Pull Ups", "Wide-Grip Pull Ups",
				"Close-Grip Pull Ups", "Lateral Pulldowns", "Lateral Pullbacks", "Lateral Pullbacks #2",
				"One Arm Lateral Pulldowns", "Shoulder Shrugs", "One Arm Dumbbell Row");

	}

	public static ExerciseData getInstance() {
		return instance;
	}

	public ObservableList<String> getChestExerciseNames() {
		return chestExerciseNames;
	}

	public void addChestExercise(String exerciseName) {
		boolean alreadyExists = false;
		for (String name : chestExerciseNames) {
			if (exerciseName.replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			chestExerciseNames.add(exerciseName);
		}
	}

	public void deleteChestExercise(String exerciseName) {
		chestExerciseNames.remove(exerciseName);
	}

	public ObservableList<String> getAbExerciseNames() {
		return abExerciseNames;
	}

	public void addAbExercise(String exerciseName) {
		boolean alreadyExists = false;
		for (String name : abExerciseNames) {
			if (exerciseName.replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			abExerciseNames.add(exerciseName);
		}
	}

	public void deleteAbExercise(String exerciseName) {
		abExerciseNames.remove(exerciseName);
	}

	public ObservableList<String> getArmExerciseNames() {
		return armExerciseNames;
	}

	public void addArmExercise(String exerciseName) {
		boolean alreadyExists = false;
		for (String name : armExerciseNames) {
			if (exerciseName.replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			armExerciseNames.add(exerciseName);
		}
	}

	public void deleteArmExercise(String exerciseName) {
		armExerciseNames.remove(exerciseName);
	}

	public ObservableList<String> getCardioExerciseNames() {
		return cardioExerciseNames;
	}

	public void addCardioExercise(String exerciseName) {
		boolean alreadyExists = false;
		for (String name : cardioExerciseNames) {
			if (exerciseName.replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			cardioExerciseNames.add(exerciseName);
		}
	}

	public void deleteCardioExercise(String exerciseName) {
		cardioExerciseNames.remove(exerciseName);
	}

	public ObservableList<String> getBackExerciseNames() {
		return backExerciseNames;
	}

	public void addBackExercise(String exerciseName) {
		boolean alreadyExists = false;
		for (String name : backExerciseNames) {
			if (exerciseName.replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			backExerciseNames.add(exerciseName);
		}
	}

	public void deleteBackExercise(String exerciseName) {
		backExerciseNames.remove(exerciseName);
	}

	public ObservableList<String> getLegExerciseNames() {
		return legExerciseNames;
	}

	public void addLegExercise(String exerciseName) {
		boolean alreadyExists = false;
		for (String name : legExerciseNames) {
			if (exerciseName.replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			legExerciseNames.add(exerciseName);
		}
	}

	public void deleteLegExercise(String exerciseName) {
		legExerciseNames.remove(exerciseName);
	}
}
