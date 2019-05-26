package singletons;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Day;
import objects.Exercise;
import objects.Goal;

public class DailyData {
	public static final String DB_NAME = "days.db";

	public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\BGking45\\Desktop\\YNWorkspace\\TheYellowNotebook\\src\\"
			+ DB_NAME;

	public static final String TABLE_DAYS = "days";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_NOTES = "notes";

	public static final String TABLE_EXERCISES = "exercises";
	public static final String COLUMN_EXERCISE_DATE = "date";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_REPS = "reps";
	public static final String COLUMN_SETS = "sets";
	public static final String COLUMN_WEIGHT = "weight";
	public static final String COLUMN_DURATION = "duration";
	public static final String COLUMN_CATEGORY = "category";

	public static final String TABLE_GOALS = "goals";
	public static final String COLUMN_GOAL_DATE = "date";
	public static final String COLUMN_PRIORITY = "priority";
	public static final String COLUMN_ACHIEVED = "achieved";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_TIME = "duration";
	public static final String COLUMN_GOAL_ID = "goalID";

	//String for creating a table representing the goals in the database
	public static final String CREATE_GOAL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GOALS + " (" + COLUMN_PRIORITY
			+ " TEXT, " + COLUMN_ACHIEVED + " INTEGER, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_TIME + " TEXT, "
			+ COLUMN_GOAL_DATE + " TEXT, " + COLUMN_GOAL_ID +  " TEXT)";

	//String for creating a table representing the days in the database
	public static final String CREATE_DAY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DAYS + " (" + COLUMN_DATE
			+ " TEXT, " + COLUMN_NOTES + " TEXT)";

	//String for creating a table representing the exercises in the database
	public static final String CREATE_EXERCISE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISES + " ("
			+ COLUMN_NAME + " TEXT, " + COLUMN_REPS + " TEXT, " + COLUMN_SETS + " TEXT, " + COLUMN_WEIGHT + " TEXT, "
			+ COLUMN_DURATION + " TEXT, " + COLUMN_CATEGORY + " TEXT, " + COLUMN_EXERCISE_DATE + " TEXT)";

	private Connection conn;
	private PreparedStatement pStatement; //Prepared Statements are used to avoid sql injections and other similar behaviors
	private PreparedStatement insertStatement;
	private PreparedStatement insertExerciseStatement;
	private PreparedStatement insertGoalStatement;
	private PreparedStatement editGoalStatement;
	private PreparedStatement deleteStatement;
	private Statement statement;
	private Statement statement2;
	private Statement statement3;

	private static DailyData instance = new DailyData();

	private ObservableList<Day> days;

	private DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");

	private DailyData() {
		days = FXCollections.observableArrayList();
	}

	public static DailyData getInstance() {
		return instance;
	}

	public String queryDays(String nameOfTable) {
		return "SELECT * FROM " + nameOfTable;
	}

	// SELECT * FROM exercises INNER JOIN days ON exercises.date = days.date WHERE
	// exercises.date = date
	public String queryExercises(String date) {
		return "SELECT * FROM " + TABLE_EXERCISES + " INNER JOIN " + TABLE_DAYS + " ON " + TABLE_EXERCISES + "."
				+ COLUMN_EXERCISE_DATE + " = " + TABLE_DAYS + "." + COLUMN_DATE + " WHERE " + TABLE_EXERCISES + "."
				+ COLUMN_EXERCISE_DATE + " = " + "\"" + date + "\"";
	}

	public String queryGoals(String date) {
		return "SELECT * FROM " + TABLE_GOALS + " INNER JOIN " + TABLE_DAYS + " ON " + TABLE_GOALS + "."
				+ COLUMN_GOAL_DATE + " = " + TABLE_DAYS + "." + COLUMN_DATE + " WHERE " + TABLE_GOALS + "."
				+ COLUMN_GOAL_DATE + " = " + "\"" + date + "\"";
	}

	public ObservableList<Day> getDays() {
		return days;
	}

	public void addDay(Day day) {
		days.add(day);
		try {
			insertStatement = conn.prepareStatement(insertDay(TABLE_DAYS, COLUMN_DATE, COLUMN_NOTES));

			insertStatement.setString(1, day.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
			insertStatement.setString(2, day.getNotes());

			insertStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteDay(Day day) {
		days.remove(day);
		deleteDayFromDataBase(TABLE_DAYS, COLUMN_DATE, day.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));

	}

	/*
	 * This method adds an exercise to the observable arraylist that is for exercises in the Day class.
	 * It also adds the exercise into the database for that day.
	 */
	public void addExerciseToDay(Day day, Exercise exercise) {
		day.addExercise(exercise);
		try {
			insertExerciseStatement = conn.prepareStatement(insertExercise(TABLE_EXERCISES, COLUMN_NAME, COLUMN_REPS,
					COLUMN_SETS, COLUMN_WEIGHT, COLUMN_DURATION, COLUMN_CATEGORY, COLUMN_EXERCISE_DATE));

			insertExerciseStatement.setString(1, exercise.getName());
			insertExerciseStatement.setString(2, exercise.getReps());
			insertExerciseStatement.setString(3, exercise.getSets());
			insertExerciseStatement.setString(4, exercise.getWeight());
			insertExerciseStatement.setString(5, exercise.getTime());
			insertExerciseStatement.setString(6, exercise.getCategory());
			insertExerciseStatement.setString(7, day.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));

			insertExerciseStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public int convertBooleanToInt(boolean bool) {
		if (bool == false) {
			return 0;
		} else
			return 1;
	}

	/*
	 * This method adds a goal to the observable arraylist that is for goals in the Day class.
	 * It also adds the goal into the database for that day.
	 */
	public void addGoalToDay(Day day, Goal goal) {
		day.addGoal(goal);
		try {
			insertGoalStatement = conn.prepareStatement(insertGoal());

			insertGoalStatement.setString(1, goal.getPriority());
			insertGoalStatement.setInt(2, convertBooleanToInt(goal.getCheckedOff()));
			insertGoalStatement.setString(3, goal.getDescription());
			insertGoalStatement.setString(4, goal.getDuration());
			insertGoalStatement.setString(5, day.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
			insertGoalStatement.setString(6, goal.getID());

			insertGoalStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public String insertGoal() {
		return "INSERT INTO " + TABLE_GOALS + " (" + COLUMN_PRIORITY + ", " + COLUMN_ACHIEVED + ", "
				+ COLUMN_DESCRIPTION + ", " + COLUMN_TIME + ", " + COLUMN_GOAL_DATE + ", " + COLUMN_GOAL_ID + ") VALUES (?, ?, ?, ?, ?, ?)";
	}

	public String updateGoalStatement() {
		return "UPDATE " + TABLE_GOALS + " SET " + COLUMN_PRIORITY + " = ?, " + COLUMN_ACHIEVED + " = ?, "
				+ COLUMN_DESCRIPTION + " = ?, " + COLUMN_DURATION + " = ? WHERE " + COLUMN_GOAL_ID + " = ?";
	}

	public void updateGoal(Goal goal) {
		try {
			editGoalStatement = conn.prepareStatement(updateGoalStatement());

			editGoalStatement.setString(1, goal.getPriority());
			editGoalStatement.setInt(2, convertBooleanToInt(goal.getCheckedOff()));
			editGoalStatement.setString(3, goal.getDescription());
			editGoalStatement.setString(4, goal.getDuration());
			//editGoalStatement.setString(5, goal.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
			editGoalStatement.setString(5, goal.getID());

			editGoalStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public String insertDay(String nameOfTable, String dateColumn, String noteColumn) {
		return "INSERT INTO " + nameOfTable + " (" + dateColumn + ", " + noteColumn + ") VALUES(?, ?)";
	}

	public String insertExercise(String nameOfTable, String nameColumn, String repColumn, String setsColumn,
			String weightColumn, String timeColumn, String categoryColumn, String dateColumn) {
		return "INSERT INTO " + nameOfTable + " (" + nameColumn + ", " + repColumn + ", " + setsColumn + ", "
				+ weightColumn + ", " + timeColumn + ", " + categoryColumn + ", " + dateColumn
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?)";
	}

	public String deleteDay(String nameOfTable, String nameOfColumn) {
		return "DELETE FROM " + nameOfTable + " WHERE " + nameOfColumn + " = ?";
	}

	public void deleteDayFromDataBase(String tableName, String columnName, String date) {
		try {
			// creates the table if it does not already exist
			deleteStatement = conn.prepareStatement(deleteDay(tableName, columnName));

			// set the corresponding param
			deleteStatement.setString(1, date);
			// execute the delete statement
			deleteStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean convertIntToBoolean(int num) {
		if (num == 0)
			return false;
		else
			return true;
	}

	public boolean open() {
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);

			// creates the table if it does not already exist
			pStatement = conn.prepareStatement(CREATE_DAY_TABLE);
			pStatement.executeUpdate();
			pStatement = conn.prepareStatement(CREATE_EXERCISE_TABLE);
			pStatement.executeUpdate();
			pStatement = conn.prepareStatement(CREATE_GOAL_TABLE); //"DROP TABLE table_name
			pStatement.executeUpdate();

			statement = conn.createStatement();
			ResultSet results = statement.executeQuery(queryDays(TABLE_DAYS));
			statement2 = conn.createStatement();
			statement3 = conn.createStatement();
			while (results.next()) {
				LocalDate date = LocalDate.parse(results.getString(COLUMN_DATE));
				Day day = new Day(date);
				ResultSet results2 = statement2
						.executeQuery(queryExercises(date.format(DateTimeFormatter.ISO_LOCAL_DATE)));
				// System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
				while (results2.next()) {
					System.out.println("in herre");
					String name = results2.getString(COLUMN_NAME);
					String reps = results2.getString(COLUMN_REPS);
					String sets = results2.getString(COLUMN_SETS);
					String weight = results2.getString(COLUMN_WEIGHT);
					String time = results2.getString(COLUMN_DURATION);
					String category = results2.getString(COLUMN_CATEGORY);
					String date2 = results2.getString(COLUMN_EXERCISE_DATE);
					Exercise exercise = new Exercise(name, reps, sets, weight, time, category, LocalDate.parse(date2));
					System.out.println(exercise.getDate());
					day.addExercise(exercise);
				}
				ResultSet results3 = statement3.executeQuery(queryGoals(date.format(DateTimeFormatter.ISO_LOCAL_DATE)));
				while (results3.next()) {
					String priority = results3.getString(COLUMN_PRIORITY);
					System.out.println(results3.getInt(COLUMN_ACHIEVED));
					Boolean achieved = convertIntToBoolean(results3.getInt(COLUMN_ACHIEVED));
					String description = results3.getString(COLUMN_DESCRIPTION);
					String time = results3.getString(COLUMN_TIME);
					String date3 = results3.getString(COLUMN_GOAL_DATE);
					String id = results3.getString(COLUMN_GOAL_ID);

					Goal goal = new Goal(description, priority, achieved, time, LocalDate.parse(date3), id);
					System.out.println(achieved);
					day.addGoal(goal);
				}
				results2.close();
				results3.close();
				day.setNotes(results.getString(COLUMN_NOTES));
				days.add(day);
			}
			//
			// SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
			// FROM Orders
			// INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID;
			results.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Couldn't connect to the database " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		try {
			if (pStatement != null) {
				pStatement.close();
			}
			if (insertGoalStatement != null) {
				insertGoalStatement.close();
			}
			if (editGoalStatement != null) {
				editGoalStatement.close();
			}
			if (insertExerciseStatement != null) {
				insertExerciseStatement.close();
			}
			if (insertStatement != null) {
				insertStatement.close();
			}
			if (deleteStatement != null) {
				deleteStatement.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (statement2 != null) {
				statement2.close();
			}
			if (statement3 != null) {
				statement3.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Couldn't close the database " + e.getMessage());
		}
	}

	/*
	 * public void saveDailyData() throws IOException { Path path =
	 * Paths.get("Days.txt"); BufferedWriter bw = Files.newBufferedWriter(path); try
	 * { Iterator<Day> iter = getInstance().getDays().iterator(); while
	 * (iter.hasNext()) { Day day = iter.next(); Iterator<Exercise> iter2 =
	 * day.getExercises().iterator(); Iterator<Goal> iter3 =
	 * day.getGoals().iterator(); while (iter2.hasNext()) { Exercise exercise =
	 * iter2.next(); bw.write(exercise.getName() + "#" + exercise.getReps() + "#" +
	 * exercise.getSets() + "#" + exercise.getWeight() + "#" +
	 * exercise.getCategory() + "-"); } bw.write("/#"); while (iter3.hasNext()) {
	 * Goal goal = iter3.next(); bw.write(goal.getPriority() + "#" +
	 * goal.getDescription() + "#" + goal.getDuration() + "-"); } bw.write("/#");
	 * bw.write(day.getDate().format(df)); bw.write("/#" + day.getNotes());
	 * bw.write("\n"); }
	 * 
	 * } finally { if (bw != null) { bw.close(); } } }
	 * 
	 * public void loadDailyData() throws IOException {
	 * 
	 * Path path = Paths.get("Days.txt"); BufferedReader br =
	 * Files.newBufferedReader(path);
	 * 
	 * String input;
	 * 
	 * try {
	 * 
	 * boolean noExercises = false; boolean noGoals = true; while ((input =
	 * br.readLine()) != null) { // entire line ObservableList<Exercise> exercises =
	 * FXCollections.observableArrayList(); // fixed doubling problem by // moving
	 * these two // statements in the while // loop; previously they // were outside
	 * ObservableList<Goal> goals = FXCollections.observableArrayList();
	 * ObservableList<Task> tasks = FXCollections.observableArrayList();
	 * 
	 * String[] dayPieces = input.split("/#");
	 * 
	 * String exercisePortion = dayPieces[0]; String[] individualExercises =
	 * exercisePortion.split("-"); for (String s : individualExercises) { if
	 * (s.equals("")) { noExercises = true; } else { String[] exercisePieces =
	 * s.split("#");
	 * 
	 * String name = exercisePieces[0]; System.out.println(name); String reps =
	 * exercisePieces[1]; System.out.println(reps); String sets = exercisePieces[2];
	 * String weight = exercisePieces[3]; String category = exercisePieces[4];
	 * 
	 * Exercise exercise = new Exercise(name, reps, sets, weight, "00:01:00",
	 * category); exercises.add(exercise); } }
	 * 
	 * String goalsPortion = dayPieces[1]; String[] individualGoals =
	 * goalsPortion.split("-"); for (String s : individualGoals) { if (s.equals(""))
	 * { noGoals = true; } else { String[] goalPieces = s.split("#");
	 * 
	 * String priority = goalPieces[0]; String description = goalPieces[1]; String
	 * duration = goalPieces[2]; Goal goal = new Goal(description, priority,
	 * duration); goals.add(goal); } } String date = dayPieces[2]; LocalDate
	 * localDate = LocalDate.parse(date, df); String notes = dayPieces[3]; if
	 * (noExercises && noGoals) { Day day = new Day(localDate);
	 * getInstance().getDays().add(day); } else { Day day = new Day(exercises,
	 * goals, localDate, tasks, notes); getInstance().getDays().add(day); }
	 * 
	 * } } finally { if (br != null) { br.close(); } }
	 * 
	 * }
	 */
}
