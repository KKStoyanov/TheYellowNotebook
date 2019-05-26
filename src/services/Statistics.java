package services;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import objects.Day;
import objects.Exercise;
import objects.Goal;
import singletons.DailyData;

public class Statistics {
	
	private StringFunctions sF = new StringFunctions();

	public SimpleStringProperty getMaxReps(String name) {
		SimpleStringProperty repsP = new SimpleStringProperty("");
		int maxReps = 0;
		for (Day day : DailyData.getInstance().getDays()) { // looping through all the days
			for (Exercise exercise : day.getExercises()) { // looping through all the exercises in each day
				if (exercise.getName().replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) { // finding the push up exercise
					int reps = Integer.valueOf(validateNumber(exercise.getReps())); // checking if its reps are greater
																					// than the current
					// reps
					if (reps > maxReps) {
						maxReps = reps; // setting the new max
					}
				}
			}
		}
		repsP.set(changeToProperString(String.valueOf(maxReps)));
		return repsP;
	}

	public SimpleStringProperty getTotalReps(String name) {
		SimpleStringProperty repsP = new SimpleStringProperty("");
		int totalReps = 0;
		for (Day day : DailyData.getInstance().getDays()) { // looping through all the days
			for (Exercise exercise : day.getExercises()) { // looping through all the exercises in each day
				if (exercise.getName().replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) { // finding the push up exercise
					totalReps += (Integer.valueOf(validateNumber(exercise.getReps()))
							* Integer.valueOf(validateNumber(exercise.getSets())));
				}
			}
		}
		repsP.set(changeToProperString(String.valueOf(totalReps)));
		return repsP;
	}

	public SimpleStringProperty getMaxWeight(String name) {
		SimpleStringProperty weightP = new SimpleStringProperty("");
		int maxWeight = 0;
		for (Day day : DailyData.getInstance().getDays()) { // looping through all the days
			for (Exercise exercise : day.getExercises()) { // looping through all the exercises in each day
				if (exercise.getName().replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) { // finding the push up exercise
					int weight = Integer.valueOf(sF.digitOnlyString(validateNumber(exercise.getWeight())));

					if (weight > maxWeight) {
						maxWeight = weight;
					}
				}
			}
		}
		weightP.set(changeToProperString(String.valueOf(maxWeight)));
		return weightP;
	}

	public SimpleStringProperty getBestTime(String name) {
		SimpleStringProperty timeP = new SimpleStringProperty("");
		String time = "";
		for (Day day : DailyData.getInstance().getDays()) { // looping through all the days
			for (Exercise exercise : day.getExercises()) { // looping through all the exercises in each day
				if (exercise.getName().replaceAll("\\s", "").equalsIgnoreCase(name.replaceAll("\\s", ""))) { // finding the push up exercise
					if (!exercise.getTime().equals("N/A")) {
						if (time.equals("")) {
							time = exercise.getTime();
						} else {
							if (time.compareTo(exercise.getTime()) > 0) {
								time = exercise.getTime();
							}
						}
					}
				}
			}
		}
		timeP.set(changeToProperString(String.valueOf(time)));
		return timeP;
	}

	public String validateNumber(String entry) {
		if (entry.equals("N/A") || entry.equals("Out of Range")) {
			entry = "0";
		}
		return entry;
	}
	
	public String changeToProperString(String x) {
		if(x.equals("0") || x.equals("")) {
			x = "N/A";
		}
		return x;
	}

	public String getMaxPushUps(DailyData data) {
		int maxPushUps = 0;
		for (Day day : data.getDays()) { // looping through all the days
			for (Exercise exercise : day.getExercises()) { // looping through all the exercises in each day
				if (exercise.getName().equals("Push Ups")) { // finding the push up exercise
					int reps = Integer.valueOf(exercise.getReps()); // checking if its reps are greater than the current
																	// reps
					if (reps > maxPushUps) {
						maxPushUps = reps; // setting the new max
					}
				}
			}
		}
		return String.valueOf(maxPushUps);
	}

	public String getMaxFlatBench(DailyData data) {
		int maxFlatBench = 0;
		for (Day day : data.getDays()) {
			for (Exercise exercise : day.getExercises()) {
				if (exercise.getName().equals("Flat Barbell Press")) {
					int weight = Integer.valueOf(exercise.getWeight());
					if (weight > maxFlatBench) {
						maxFlatBench = weight;
					}
				}
			}
		}
		return String.valueOf(maxFlatBench);
	}

	public String getMaxDeclineBench(DailyData data) {
		int maxDeclineBench = 0;
		for (Day day : data.getDays()) {
			for (Exercise exercise : day.getExercises()) {
				if (exercise.getName().equals("Declined Barbell Press")) {
					int weight = Integer.valueOf(exercise.getWeight());
					if (weight > maxDeclineBench) {
						maxDeclineBench = weight;
					}
				}
			}
		}
		return String.valueOf(maxDeclineBench);
	}

	public String getMaxInclineBench(DailyData data) {
		int maxInclineBench = 0;
		for (Day day : data.getDays()) {
			for (Exercise exercise : day.getExercises()) {
				if (exercise.getName().equals("Inclined Barbell Press")) {
					int weight = Integer.valueOf(exercise.getWeight());
					if (weight > maxInclineBench) {
						maxInclineBench = weight;
					}
				}
			}
		}
		return String.valueOf(maxInclineBench);
	}

	public String getMaxSquat(DailyData data) {
		int maxSquat = 0;
		for (Day day : data.getDays()) {
			for (Exercise exercise : day.getExercises()) {
				if (exercise.getName().equals("Squats")) {
					if (!(exercise.getWeight().equals("N/A") || exercise.getWeight().equals("Out of Range"))) {
						int weight = Integer
								.valueOf(exercise.getWeight().substring(0, exercise.getWeight().length() - 2));
						if (weight > maxSquat) {
							maxSquat = weight;
						}
					}
				}
			}
		}
		return String.valueOf(maxSquat);
	}

	public SimpleStringProperty totalAchievedGoals() {
		SimpleStringProperty successP = new SimpleStringProperty("");
		int totalAchievedGoals = 0;
		for (Day day : DailyData.getInstance().getDays()) {
			for (Goal goal : day.getGoals()) {
				if (goal.getCheckedOff()) {
					totalAchievedGoals += 1;
				}
			}
		}
		successP.set(String.valueOf(totalAchievedGoals));
		return successP;
	}

	public SimpleStringProperty totalFailedGoals() {
		SimpleStringProperty failedP = new SimpleStringProperty("");
		int totalFailedGoals = 0;
		for (Day day : DailyData.getInstance().getDays()) {
			for (Goal goal : day.getGoals()) {
				if (!goal.getCheckedOff() && day.getDate().compareTo(LocalDate.now()) < 0) {
					totalFailedGoals += 1;
				}
			}
		}
		failedP.set(String.valueOf(totalFailedGoals));
		return failedP;
	}
}
