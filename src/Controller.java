
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class Controller {

	@FXML
	private BorderPane mainBorderPane;

	private GridPane gridPane;

	@FXML
	private ListView<Day> dayListView;

	@FXML
	private TableView<Exercise> exerciseTableView;

	@FXML
	private TableView<Goal> goalTableView;

	@FXML
	private TableColumn<Goal, CheckBox> achievedColumn;

	@FXML
	private ContextMenu exerciseTVContextMenu;

	@FXML
	private ContextMenu goalTVContextMenu;

	@FXML
	private ContextMenu dayContextMenu;

	@FXML
	private Label titleLabel;

	private Button addExercise;

	private Button addGoal;

	public void initialize() {
		dayContextMenu = new ContextMenu();
		MenuItem deleteDayMI = new MenuItem("Delete");
		deleteDayMI.setOnAction(new EventHandler<ActionEvent>() { // what to do when a cell in the listView is
																	// right-clicked
			@Override
			public void handle(ActionEvent event) {
				Day selectedDay = dayListView.getSelectionModel().getSelectedItem(); // checking what day was selected
				deleteSelectedDay(selectedDay); // deleting the selected day
			}
		});
		dayContextMenu.getItems().addAll(deleteDayMI); // adding the delete button to the context menu

		// this replaces handleListView; don't need onMouseClicked in FXML file
		dayListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Day>() {
			@Override
			public void changed(ObservableValue<? extends Day> observable, Day oldValue, Day newValue) {
				if (newValue != null) { // this checks that what we are clicking in the ListView is not null
					Day day = dayListView.getSelectionModel().getSelectedItem(); // the day that is selected on the
																					// listView
					mainBorderPane.setCenter(displayGridPane(day));
				}else {
					mainBorderPane.setCenter(null);
				}
			}
		});

		// sorting the days in the list view
		SortedList<Day> sortedList = new SortedList<Day>(DailyData.getInstance().getDays(), new Comparator<Day>() {
			@Override
			public int compare(Day o1, Day o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		});
		dayListView.setItems(sortedList);

		// as of right now this is only for the context menu
		dayListView.setCellFactory(new Callback<ListView<Day>, ListCell<Day>>() {
			@Override
			public ListCell<Day> call(ListView<Day> param) {
				ListCell<Day> cell = new ListCell<Day>() {
					protected void updateItem(Day day, boolean empty) {
						super.updateItem(day, empty);
						if (empty) {
							setText(null);
						} else {
							setText(day.toString());
						}
					}
				};
				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setContextMenu(null);
					} else {
						cell.setContextMenu(dayContextMenu);
					}
				});
				return cell;
			}
		});

		// similar to deleting day in listView

		dayListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		dayListView.getSelectionModel().selectFirst(); //this is so the first item in the listview is automatically selected when the program is opened
	}

	@FXML
	public void addDate() { // method ran when user opens the menubar and presses add date
		Dialog<ButtonType> dialog = new Dialog<ButtonType>(); // <ButtonType> is used to represent the type of the
																// result property
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Choose Date");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("datedialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the date dialog");
			e.printStackTrace();
			return;
		}
		ButtonType OKButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		// dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().addAll(OKButtonType, ButtonType.CANCEL);
		dateController controller = fxmlLoader.getController();
		Optional<ButtonType> result = dialog.showAndWait();
		Node okButton = dialog.getDialogPane().lookupButton(OKButtonType);
		okButton.setDisable(true);
		if (result.isPresent() && result.get() == OKButtonType && controller.enteredInput()) {
			Day day = new Day(controller.processDate());
			DailyData.getInstance().addDay(day);
		} else {
			System.out.println("in here");
		}

	}

	@FXML
	public void handleClickListView() {
		System.out.println("in here");
		mainBorderPane.setCenter(new Label("Hello"));
		// Day day = dayListView.getSelectionModel().getSelectedItem(); //the day that
		// is selected on the listView
		// exerciseTableView.setItems(day.getExercises()); //setting the exercises in
		// the exercise tableView
		// goalTableView.setItems(day.getGoals()); //setting the goals in the goal
		// tableView
	}

	@FXML
	public void addExercise() {
		Day selectedDay = dayListView.getSelectionModel().getSelectedItem();
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Choose Muscle Area");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("musclegroupdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the muscle group dialog");
			e.printStackTrace();
			return;
		}
		Musclegroupdialog mgController = fxmlLoader.getController();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.NEXT) {
			Dialog<ButtonType> dialog1 = new Dialog<ButtonType>();
			dialog1.initOwner(mainBorderPane.getScene().getWindow());
			dialog1.setTitle("Choose Exercise");
			FXMLLoader fxmlLoader1 = new FXMLLoader();
			fxmlLoader1.setLocation(getClass().getResource(mgController.getCorrectPath()));
			String category = determineCategory(mgController.getCorrectPath());
			System.out.println(mgController.getCorrectPath());
			try {
				dialog1.getDialogPane().setContent(fxmlLoader1.load());
			} catch (IOException e) {
				System.out.println("Couldn't load the exercise dialog");
				e.printStackTrace();
				return;
			}
			dialog1.getDialogPane().getButtonTypes().add(ButtonType.OK);
			dialog1.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			Optional<ButtonType> result1 = dialog1.showAndWait();
			if (result1.isPresent() && result1.get() == ButtonType.OK) {
				ExerciseController exerciseController = fxmlLoader1.getController();
				Exercise newExercise = exerciseController.processResults(category);
				selectedDay.addExercise(newExercise);
			}
		}
	}

	@FXML
	public void addGoal() {
		Day selectedDay = dayListView.getSelectionModel().getSelectedItem();
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add Goal");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("goaldialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		goalController controller = fxmlLoader.getController();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Goal goal = controller.processResults();
			selectedDay.addGoal(goal);
		}
	}

	public void deleteSelectedDay(Day day) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Day");
		alert.setHeaderText("Delete Day: " + day.toString());
		alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			DailyData.getInstance().deleteDay(day);
		}
	}

	public void deleteSelectedExercise(Exercise exercise) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Exercise");
		alert.setHeaderText("Delete Exercise: " + exercise.getName());
		alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Day selectedDay = dayListView.getSelectionModel().getSelectedItem();
			selectedDay.deleteExercise(exercise);
		}
	}

	public void deleteSelectedGoal(Goal goal) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Goal");
		alert.setHeaderText("Delete Goal: " + goal.getDescription());
		alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Day selectedDay = dayListView.getSelectionModel().getSelectedItem();
			selectedDay.deleteGoal(goal);
		}
	}

	public void editSelectedExercise(Exercise exercise) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Edit Exercise");
		FXMLLoader fxmlLoader = new FXMLLoader();
		String path = determinePath(exercise.getCategory());
		fxmlLoader.setLocation(getClass().getResource(path));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		ExerciseController controller = fxmlLoader.getController();
		controller.editExercise(exercise);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			controller.updateExercise(exercise);
		}
	}

	/**
	 * This method will edit a goal that is right clicked. It creates a dialog with
	 * an OK and cancel button, and a large textarea. The text area contains the
	 * current description of the goal. The controller of the dialog is used to call
	 * the editGoal and updateGoal methods.
	 *
	 * @param goal
	 */
	public void editSelectedGoal(Goal goal) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Edit Goal");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("goaldialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		goalController controller = fxmlLoader.getController();
		controller.editGoal(goal);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			controller.updateGoal(goal);
		}
	}

	public String determineCategory(String path) {
		String category = "";
		if (path.equals("abexercisedialog.fxml")) {
			category = "C";
		} else if (path.equals("armexercisedialog.fxml")) {
			category = "A";
		} else if (path.equals("chestexercisedialog.fxml")) {
			category = "P";
		} else if (path.equals("legexercisedialog.fxml")) {
			category = "L";
		} else
			category = "H";

		return category;
	}

	public String determinePath(String category) {
		String path = "";
		if (category.equals("C")) {
			path = "abexercisedialog.fxml";
		} else if (category.equals("A")) {
			path = "armexercisedialog.fxml";
		} else if (category.equals("P")) {
			path = "chestexercisedialog.fxml";
		} else if (category.equals("L")) {
			path = "legexercisedialog.fxml";
		} else
			path = "cardioexercisedialog.fxml";

		return path;
	}

	public GridPane displayGridPane(Day day) {
		gridPane = new GridPane();
		exerciseTableView = new TableView();
		goalTableView = new TableView();
		titleLabel = new Label();
		addExercise = new Button("Add Exercise");
		addGoal = new Button("Add Goal");


		gridPane.setHgrow(exerciseTableView, Priority.ALWAYS); // this is so the tableviews fully extend on the screen

		titleLabel.setFont(new Font("Arial Black", 24));

		TableColumn exerciseCol = new TableColumn("Exercise");
		exerciseCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
		TableColumn repsCol = new TableColumn("Reps");
		repsCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("reps"));
		TableColumn setsCol = new TableColumn("Sets");
		setsCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("sets"));
		TableColumn weightsCol = new TableColumn("Weight");
		weightsCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("weight"));

		exerciseTableView.getColumns().addAll(exerciseCol, repsCol, setsCol, weightsCol);
		exerciseTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn achievedCol = new TableColumn("Achieved");
		achievedCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("achieved"));
		achievedCol.setPrefWidth(100);
		achievedCol.setMinWidth(100);
		achievedCol.setMaxWidth(150);
		achievedCol.setStyle("-fx-alignment: CENTER;"); // places the checkbox in
		// the center of the column

		TableColumn descriptionCol = new TableColumn("Description");
		// descriptionCol.setPrefWidth(400);
		// descriptionCol.setMaxWidth(1000);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("description"));

		goalTableView.getColumns().addAll(achievedCol, descriptionCol);
		goalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		exerciseTableView.setItems(day.getExercises()); // setting the exercises in the exercise tableView
		goalTableView.setItems(day.getGoals()); // setting the goals in the goal tableView
		titleLabel.setText(day.toString());

		gridPane.setPadding(new Insets(5));
		gridPane.setVgap(5);

		// HBox hbox = new HBox();
		// Label spaceLabel = new Label(" ");
		// hbox.setHgrow(exerciseButton, Priority.ALWAYS);
		// hbox.getChildren().addAll(spaceLabel, exerciseButton);

		gridPane.setHalignment(addExercise, HPos.RIGHT);
		gridPane.setHalignment(addGoal, HPos.RIGHT);

		gridPane.addRow(0, titleLabel);
		gridPane.addRow(1, new Label("Exercises"));
		gridPane.addRow(2, exerciseTableView);
		gridPane.addRow(4, new Label("Goals"));
		gridPane.addRow(3, addExercise);
		gridPane.addRow(5, goalTableView);
		gridPane.addRow(6, addGoal);
		
		rightClickExerciseTableView(exerciseTableView);
		rightClickGoalTableView(goalTableView);
		clickedAddExercise(addExercise);
		clickedAddGoal(addGoal);
		

		return gridPane;
	}
	
	public void rightClickExerciseTableView(TableView tv) {
		tv.setRowFactory(new Callback<TableView<Exercise>, TableRow<Exercise>>() {

			@Override
			public TableRow<Exercise> call(TableView<Exercise> param) {
				TableRow<Exercise> row = new TableRow<Exercise>();
				exerciseTVContextMenu = new ContextMenu();
				MenuItem deleteExerciseMI = new MenuItem("Delete Exercise");
				MenuItem editExerciseMI = new MenuItem("Edit Exercise");
				
				//this is for deleting a selected exercise
				deleteExerciseMI.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Exercise selectedExercise = (Exercise) tv.getSelectionModel().getSelectedItem();
						deleteSelectedExercise(selectedExercise);
					}
				});
				exerciseTVContextMenu.getItems().addAll(deleteExerciseMI);
				
				//this is for editing a selected exercise
				editExerciseMI.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Exercise selectedExercise = (Exercise) tv.getSelectionModel().getSelectedItem();
						editSelectedExercise(selectedExercise);
					}
				});
				exerciseTVContextMenu.getItems().addAll(editExerciseMI);
				
				//this is to make sure the context menu does not appear when there are no exercises in the tableview
				row.contextMenuProperty().bind(
						Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(exerciseTVContextMenu));
				return row;
			}
		});
	}
	
	public void rightClickGoalTableView(TableView tv) {
		tv.setRowFactory(new Callback<TableView<Goal>, TableRow<Goal>>() {

			@Override
			public TableRow<Goal> call(TableView<Goal> param) {
				TableRow<Goal> row = new TableRow<Goal>();
				goalTVContextMenu = new ContextMenu();
				MenuItem deleteGoalMI = new MenuItem("Delete Goal");
				MenuItem editGoalMI = new MenuItem("Edit Goal");
				deleteGoalMI.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Goal selectedGoal = (Goal) tv.getSelectionModel().getSelectedItem();
						deleteSelectedGoal(selectedGoal);
					}
				});
				goalTVContextMenu.getItems().addAll(deleteGoalMI);
				editGoalMI.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Goal selectedGoal = (Goal) tv.getSelectionModel().getSelectedItem();
						editSelectedGoal(selectedGoal);
					}
				});
				goalTVContextMenu.getItems().addAll(editGoalMI);
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(goalTVContextMenu));
				return row;
			}
		});
	}
	
	public void clickedAddExercise(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addExercise();
			}
		});
	}
	
	public void clickedAddGoal(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addGoal();
			}
		});
	}

	public void showExerciseStatistics() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Exercise Statistics");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("exercisestatdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		exerciseStatController controller = fxmlLoader.getController();
		controller.processDialog(DailyData.getInstance());
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			dialog.close();
		}
	}
}
