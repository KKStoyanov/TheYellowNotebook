
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DailyData {
	public static final String DB_NAME = "days.db";

	public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\BGking45\\Desktop\\YNWorkspace\\TheYellowNotebook\\src\\"
			+ DB_NAME;

	public static final String TABLE_DAYS = "days";
	public static final String COLUMN_DATE = "date";
	
	private static DailyData instance = new DailyData();

	private ObservableList<Day> days;

	private DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");

	private DailyData() {
		days = FXCollections.observableArrayList();
	}

	public static DailyData getInstance() {
		return instance;
	}

	public ObservableList<Day> getDays() {
		return days;
	}

	public void addDay(Day day) {
		days.add(day);
	}

	public void deleteDay(Day day) {
		days.remove(day);
	}

	public void saveDailyData() throws IOException {
		Path path = Paths.get("Days.txt");
		BufferedWriter bw = Files.newBufferedWriter(path);
		try {
			Iterator<Day> iter = getInstance().getDays().iterator();
			while (iter.hasNext()) {
				Day day = iter.next();
				Iterator<Exercise> iter2 = day.getExercises().iterator();
				Iterator<Goal> iter3 = day.getGoals().iterator();
				while (iter2.hasNext()) {
					Exercise exercise = iter2.next();
					bw.write(exercise.getName() + "#" + exercise.getReps() + "#" + exercise.getSets() + "#"
							+ exercise.getWeight() + "#" + exercise.getCategory() + "-");
				}
				bw.write("/#");
				while (iter3.hasNext()) {
					Goal goal = iter3.next();
					bw.write(goal.getPriority() + "#" + goal.getDescription() + "#" + goal.getDuration() + "-");
				}
				bw.write("/#");
				bw.write(day.getDate().format(df));
				bw.write("/#" + day.getNotes());
				bw.write("\n");
			}

		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	public void loadDailyData() throws IOException {

		Path path = Paths.get("Days.txt");
		BufferedReader br = Files.newBufferedReader(path);

		String input;

		try {

			boolean noExercises = false;
			boolean noGoals = true;
			while ((input = br.readLine()) != null) { // entire line
				ObservableList<Exercise> exercises = FXCollections.observableArrayList(); // fixed doubling problem by
																							// moving these two
																							// statements in the while
																							// loop; previously they
																							// were outside
				ObservableList<Goal> goals = FXCollections.observableArrayList();
				ObservableList<Task> tasks = FXCollections.observableArrayList();

				String[] dayPieces = input.split("/#");

				String exercisePortion = dayPieces[0];
				String[] individualExercises = exercisePortion.split("-");
				for (String s : individualExercises) {
					if (s.equals("")) {
						noExercises = true;
					} else {
						String[] exercisePieces = s.split("#");

						String name = exercisePieces[0];
						System.out.println(name);
						String reps = exercisePieces[1];
						System.out.println(reps);
						String sets = exercisePieces[2];
						String weight = exercisePieces[3];
						String category = exercisePieces[4];

						Exercise exercise = new Exercise(name, reps, sets, weight, "00:01:00", category);
						exercises.add(exercise);
					}
				}

				String goalsPortion = dayPieces[1];
				String[] individualGoals = goalsPortion.split("-");
				for (String s : individualGoals) {
					if (s.equals("")) {
						noGoals = true;
					} else {
						String[] goalPieces = s.split("#");

						String priority = goalPieces[0];
						String description = goalPieces[1];
						String duration = goalPieces[2];
						Goal goal = new Goal(description, priority, duration);
						goals.add(goal);
					}
				}
				String date = dayPieces[2];
				LocalDate localDate = LocalDate.parse(date, df);
				String notes = dayPieces[3];
				if (noExercises && noGoals) {
					Day day = new Day(localDate);
					getInstance().getDays().add(day);
				} else {
					Day day = new Day(exercises, goals, localDate, tasks, notes);
					getInstance().getDays().add(day);
				}

			}
		} finally {
			if (br != null) {
				br.close();
			}
		}

	}
}
