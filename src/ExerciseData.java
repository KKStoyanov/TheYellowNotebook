
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	private ObservableList<String> allExercises;

	public static final String DB_NAME = "exerciseNames.db";

	public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\BGking45\\Desktop\\YNWorkspace\\TheYellowNotebook\\src\\"
			+ DB_NAME;

	public static final String TABLE_CHEST_EXERCISES = "chestExercises";
	public static final String COLUMN_CHEST_EXERCISE_NAME = "name";

	public static final String TABLE_CORE_EXERCISES = "coreExercises";
	public static final String COLUMN_CORE_EXERCISE_NAME = "name";

	public static final String TABLE_ARM_EXERCISES = "armExercises";
	public static final String COLUMN_ARM_EXERCISE_NAME = "name";

	public static final String TABLE_LEG_EXERCISES = "legExercises";
	public static final String COLUMN_LEG_EXERCISE_NAME = "name";

	public static final String TABLE_BACK_EXERCISES = "backExercises";
	public static final String COLUMN_BACK_EXERCISE_NAME = "name";

	public static final String TABLE_CARDIO_EXERCISES = "cardioExercises";
	public static final String COLUMN_CARDIO_EXERCISE_NAME = "name";

	// CREATE TABLE IF NOT EXISTS chestExercises (name TEXT)
	public static final String CREATE_CHEST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CHEST_EXERCISES + " ("
			+ COLUMN_CHEST_EXERCISE_NAME + " TEXT)";

	public static final String CREATE_CORE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CORE_EXERCISES + " ("
			+ COLUMN_CORE_EXERCISE_NAME + " TEXT)";

	public static final String CREATE_ARM_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ARM_EXERCISES + " ("
			+ COLUMN_ARM_EXERCISE_NAME + " TEXT)";

	public static final String CREATE_LEG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LEG_EXERCISES + " ("
			+ COLUMN_LEG_EXERCISE_NAME + " TEXT)";

	public static final String CREATE_BACK_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BACK_EXERCISES + " ("
			+ COLUMN_BACK_EXERCISE_NAME + " TEXT)";

	public static final String CREATE_CARDIO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CARDIO_EXERCISES + " ("
			+ COLUMN_CARDIO_EXERCISE_NAME + " TEXT)";

	private Connection conn;

	private PreparedStatement pStatement;
	private PreparedStatement insertStatement;
	private PreparedStatement deleteStatement;
	private Statement statement;

	private ExerciseData() {
		chestExerciseNames = FXCollections.observableArrayList();
		abExerciseNames = FXCollections.observableArrayList();
		armExerciseNames = FXCollections.observableArrayList();
		legExerciseNames = FXCollections.observableArrayList();
		cardioExerciseNames = FXCollections.observableArrayList();
		allExercises = FXCollections.observableArrayList();
	}

	public String insertExercise(String nameOfTable, String nameOfColumn) {
		return "INSERT INTO " + nameOfTable + " (" + nameOfColumn + ") VALUES(?)";
	}

	public String queryExercise(String nameOfTable) {
		return "SELECT * FROM " + nameOfTable;
	}

	public String deleteExercise(String nameOfTable, String nameOfColumn) {
		return "DELETE FROM " + nameOfTable + " WHERE " + nameOfColumn + " = ?";
	}
	
	public void deleteExerciseFromDataBase(String tableName, String columnName, String exerciseName) {
		try {
			// creates the table if it does not already exist
			deleteStatement = conn.prepareStatement(deleteExercise(tableName, columnName));

			// set the corresponding param
			deleteStatement.setString(1, exerciseName);
			// execute the delete statement
			deleteStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
			try {
				insertStatement = conn.prepareStatement(insertExercise(TABLE_CHEST_EXERCISES, COLUMN_CHEST_EXERCISE_NAME));

				insertStatement.setString(1, exerciseName);

				insertStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteChestExercise(String exerciseName) {
		chestExerciseNames.remove(exerciseName);
		deleteExerciseFromDataBase(TABLE_CHEST_EXERCISES, COLUMN_CHEST_EXERCISE_NAME, exerciseName);
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
			try {
				insertStatement = conn.prepareStatement(insertExercise(TABLE_CORE_EXERCISES, COLUMN_CORE_EXERCISE_NAME));

				insertStatement.setString(1, exerciseName);

				insertStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteAbExercise(String exerciseName) {
		abExerciseNames.remove(exerciseName);
		deleteExerciseFromDataBase(TABLE_CORE_EXERCISES, COLUMN_CORE_EXERCISE_NAME, exerciseName);
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
			try {
				insertStatement = conn.prepareStatement(insertExercise(TABLE_ARM_EXERCISES, COLUMN_ARM_EXERCISE_NAME));

				insertStatement.setString(1, exerciseName);

				insertStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteArmExercise(String exerciseName) {
		armExerciseNames.remove(exerciseName);
		deleteExerciseFromDataBase(TABLE_ARM_EXERCISES, COLUMN_ARM_EXERCISE_NAME, exerciseName);
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
			try {
				insertStatement = conn.prepareStatement(insertExercise(TABLE_CARDIO_EXERCISES, COLUMN_CARDIO_EXERCISE_NAME));

				insertStatement.setString(1, exerciseName);

				insertStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteCardioExercise(String exerciseName) {
		cardioExerciseNames.remove(exerciseName);
		deleteExerciseFromDataBase(TABLE_CARDIO_EXERCISES, COLUMN_CARDIO_EXERCISE_NAME, exerciseName);
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
			try {
				insertStatement = conn.prepareStatement(insertExercise(TABLE_BACK_EXERCISES, COLUMN_BACK_EXERCISE_NAME));

				insertStatement.setString(1, exerciseName);

				insertStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteBackExercise(String exerciseName) {
		backExerciseNames.remove(exerciseName);
		deleteExerciseFromDataBase(TABLE_BACK_EXERCISES, COLUMN_BACK_EXERCISE_NAME, exerciseName);
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
			try {
				insertStatement = conn.prepareStatement(insertExercise(TABLE_LEG_EXERCISES, COLUMN_LEG_EXERCISE_NAME));

				insertStatement.setString(1, exerciseName);

				insertStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteLegExercise(String exerciseName) {
		legExerciseNames.remove(exerciseName);
		deleteExerciseFromDataBase(TABLE_LEG_EXERCISES, COLUMN_LEG_EXERCISE_NAME, exerciseName);
	}

	public boolean open() {
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);

			// creates the table if it does not already exist
			pStatement = conn.prepareStatement(CREATE_CHEST_TABLE);
			pStatement.executeUpdate();
			
			pStatement = conn.prepareStatement(CREATE_CORE_TABLE);
			pStatement.executeUpdate();
			
			pStatement = conn.prepareStatement(CREATE_ARM_TABLE);
			pStatement.executeUpdate();
			
			pStatement = conn.prepareStatement(CREATE_LEG_TABLE);
			pStatement.executeUpdate();
			
			pStatement = conn.prepareStatement(CREATE_BACK_TABLE);
			pStatement.executeUpdate();
			
			pStatement = conn.prepareStatement(CREATE_CARDIO_TABLE);
			pStatement.executeUpdate();

			statement = conn.createStatement();
			ResultSet results = statement.executeQuery(queryExercise(TABLE_CHEST_EXERCISES));
			while (results.next()) {
				chestExerciseNames.add(results.getString(1));
			}
			
			statement = conn.createStatement();
			results = statement.executeQuery(queryExercise(TABLE_CORE_EXERCISES));
			while (results.next()) {
				abExerciseNames.add(results.getString(1));
			}
			
			statement = conn.createStatement();
			results = statement.executeQuery(queryExercise(TABLE_ARM_EXERCISES));
			while (results.next()) {
				armExerciseNames.add(results.getString(1));
			}
			
			statement = conn.createStatement();
			results = statement.executeQuery(queryExercise(TABLE_LEG_EXERCISES));
			while (results.next()) {
				legExerciseNames.add(results.getString(1));
			}
			
			statement = conn.createStatement();
			results = statement.executeQuery(queryExercise(TABLE_BACK_EXERCISES));
			while (results.next()) {
				backExerciseNames.add(results.getString(1));
			}
			
			statement = conn.createStatement();
			results = statement.executeQuery(queryExercise(TABLE_CARDIO_EXERCISES));
			while (results.next()) {
				cardioExerciseNames.add(results.getString(1));
			}

			return true;
		} catch (SQLException e) {
			System.out.println("Couldn't connect to the database: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		try {

			if (insertStatement != null) {
				insertStatement.close();
			}

			if (pStatement != null) {
				pStatement.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (deleteStatement != null) {
				statement.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Couldn't close the database: " + e.getMessage());
		}
	}
}
