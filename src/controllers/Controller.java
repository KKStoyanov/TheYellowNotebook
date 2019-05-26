package controllers;

import java.io.IOException;
import java.time.LocalDate;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;
import objects.Day;
import objects.Exercise;
import singletons.ExerciseData;
import objects.Goal;
import objects.Task;
import objects.WrappingTextFieldTableCell;
import services.StringFunctions;
import singletons.DailyData;

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

	private TableView<Task> taskTableView;

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

	private TextArea notesTA;

	private Button addNote;

	private TextField hourTF1;

	private TextField minuteTF1;

	private TextField hourTF2;

	private TextField minuteTF2;

	private ComboBox oneCB;

	private ComboBox twoCB;

	private TextField eventTF;

	private Button addTask;

	private Button deleteTask;

	private HBox taskBox;

	private HBox taskBox2;

	private StringFunctions stringFunctions = new StringFunctions();
	
	

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
				} else {
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
		dayListView.getSelectionModel().selectFirst(); // this is so the first item in the listview is automatically
														// selected when the program is opened
	}

	/**
	 * This method will add a date when add date is clicked inside of the file tab.
	 * Inside the method, a dialog with a DatePicker is generated. The OK button is disabled
	 * as long the user has not selected a date to add. Once a date is selected, a day will be added to the list view
	 * and daily data singleton.
	 */
	@FXML
	public void addDate() { // method ran when user opens the menubar and presses add date
		Dialog<ButtonType> dialog = new Dialog<ButtonType>(); // <ButtonType> is used to represent the type of the
																// result property
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Choose Date");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../view/datedialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the date dialog");
			e.printStackTrace();
			return;
		}
		ButtonType OKButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButtonType, ButtonType.CANCEL);
		Node okButton = dialog.getDialogPane().lookupButton(OKButtonType);
		
		dateController controller = fxmlLoader.getController();
		controller.disableDays(); //makes it so the user cannot select the same day twice
		okButton.disableProperty().bind(controller.enteredInputProperty()); //
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == OKButtonType) {
			Day day = new Day(controller.processDate());
			DailyData.getInstance().addDay(day);
			dayListView.getSelectionModel().select(day); // this makes it so that when a new date is added it is
															// automatically selected
		} else {
			System.out.println("in here");
		}

	}

	@FXML
	public void addExercise() {
		Day selectedDay = dayListView.getSelectionModel().getSelectedItem();
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Choose Muscle Area");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../view/musclegroupdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the muscle group dialog");
			e.printStackTrace();
			return;
		}
		MuscleGroupController mgController = fxmlLoader.getController();
		
		//buttons
		dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);
		dialog.getDialogPane().lookupButton(ButtonType.NEXT).disableProperty()
				.bind(mgController.getMuscleComboB().valueProperty().isNull()); // the way to disable a button
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		
		dialog.getDialogPane().getStylesheets().add(getClass().getResource("../css/myDialogs.css").toExternalForm());
		//dialog.getDialogPane().getStyleClass().add("goalMenu");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.NEXT) {
			String category = mgController.determineCategory();
			Dialog<ButtonType> dialog1 = new Dialog<ButtonType>();
			dialog1.initOwner(mainBorderPane.getScene().getWindow());
			dialog1.setTitle("Choose Exercise");
			FXMLLoader fxmlLoader1 = new FXMLLoader();
			fxmlLoader1.setLocation(getClass().getResource("../view/exercisedialog.fxml"));
			try {
				dialog1.getDialogPane().setContent(fxmlLoader1.load());
			} catch (IOException e) {
				System.out.println("Couldn't load the exercise dialog");
				e.printStackTrace();
				return;
			}
			dialog1.getDialogPane().getButtonTypes().add(ButtonType.OK);
			dialog1.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			ExerciseController exerciseController = fxmlLoader1.getController();
			exerciseController.updateComboBox(category);
			Optional<ButtonType> result1 = dialog1.showAndWait();
			if (result1.isPresent() && result1.get() == ButtonType.OK) {
				if (!exerciseController.containsEmptyFields()) { // look into this
					Exercise newExercise = exerciseController.processResults(category, selectedDay.getDate());
					DailyData.getInstance().addExerciseToDay(selectedDay, newExercise);
				}
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
		fxmlLoader.setLocation(getClass().getResource("../view/goaldialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		goalController controller = fxmlLoader.getController();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		dialog.getDialogPane().getStylesheets().add(getClass().getResource("../css/myDialogs.css").toExternalForm());
		dialog.getDialogPane().getStyleClass().add("goalMenu");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if (!controller.containsEmptyTextArea()) {
				Goal goal = controller.processResults(selectedDay.getDate());
				DailyData.getInstance().addGoalToDay(selectedDay, goal);
			}
		}
	}

	public void addNote() {
		Day selectedDay = dayListView.getSelectionModel().getSelectedItem();
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Change Notes");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../view/notedialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		noteController controller = fxmlLoader.getController();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		controller.displayOldNotes(selectedDay.getNotes());
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if (!controller.containsEmptyTextArea()) {
				String notes = controller.processResults();
				selectedDay.setNotes(notes);
			}
		}
	}

	// public void addTask(TextField timeTF, TextField eventTF) {
	// Day selectedDay = dayListView.getSelectionModel().getSelectedItem();
	// selectedDay.addTask(new Task(timeTF.getText(), eventTF.getText()));
	// }

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
		alert.setHeaderText("Are you sure you want to delete this goal?");
		alert.setContentText("\"" + goal.getDescription() + "\"");
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../css/myDialogs.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
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
		fxmlLoader.setLocation(getClass().getResource("../view/exercisedialog.fxml"));
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
		fxmlLoader.setLocation(getClass().getResource("../view/goaldialog.fxml"));
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
			System.out.println(goal.getPriority());
			DailyData.getInstance().updateGoal(goal);
		}
	}
	/*
	 * public String determineCategory(String path) { String category = ""; if
	 * (path.equals("abexercisedialog.fxml")) { category = "C"; } else if
	 * (path.equals("armexercisedialog.fxml")) { category = "A"; } else if
	 * (path.equals("chestexercisedialog.fxml")) { category = "P"; } else if
	 * (path.equals("legexercisedialog.fxml")) { category = "L"; } else category =
	 * "H";
	 * 
	 * return category; }
	 */

	public GridPane displayGridPane(Day day) {
		gridPane = new GridPane();
		exerciseTableView = new TableView();
		goalTableView = new TableView();
		titleLabel = new Label();
		addExercise = new Button("Add Exercise");
		addGoal = new Button("Add Goal");
		notesTA = new TextArea(day.getNotes());
		addNote = new Button("Add Note(s)");
		taskTableView = new TableView<Task>();
		hourTF1 = new TextField();
		minuteTF1 = new TextField();
		hourTF2 = new TextField();
		minuteTF2 = new TextField();
		oneCB = new ComboBox();
		twoCB = new ComboBox();
		eventTF = new TextField();
		addTask = new Button("+");
		deleteTask = new Button("-");
		taskBox = new HBox(2); // the 2 is the spacing between the elements in the hbox
		taskBox2 = new HBox(2);

		oneCB.getItems().addAll("AM", "PM");
		oneCB.getSelectionModel().selectFirst();
		twoCB.getItems().addAll("AM", "PM");
		twoCB.getSelectionModel().selectFirst();
		// oneCB.prefWidthProperty().bind(taskBox.widthProperty());
		// twoCB.prefWidthProperty().bind(taskBox.widthProperty());

		hourTF1.setPromptText("Hour");
		hourTF1.setPrefWidth(40);
		minuteTF1.setPrefWidth(40);
		hourTF2.setPrefWidth(40);
		minuteTF2.setPrefWidth(40);
		// hourTF1.prefWidthProperty().bind(taskBox.widthProperty());
		// minuteTF1.prefWidthProperty().bind(taskBox.widthProperty());
		// hourTF2.prefWidthProperty().bind(taskBox.widthProperty());
		// minuteTF2.prefWidthProperty().bind(taskBox.widthProperty());
		hourTF2.setPromptText("Hour");
		minuteTF1.setPromptText("Minute");
		minuteTF2.setPromptText("Minute");
		eventTF.setPromptText("Event");

		notesTA.setId("ta");
		notesTA.setWrapText(true);
		notesTA.setEditable(false);

		// addExercise.getStyleClass().add("button1");
		// addGoal.getStyleClass().add("button");

		exerciseTableView.setPlaceholder(new Label("")); // this is so it doesn't say "no content in table" when the
															// tableview is empty
		goalTableView.setPlaceholder(new Label(""));
		taskTableView.setPlaceholder(new Label("Empty"));

		gridPane.setHgrow(exerciseTableView, Priority.ALWAYS); // this is so the tableviews fully extend on the screen

		titleLabel.setId("dateLabel");
		// titleLabel.setFont(new Font("Arial Black", 24));

		TableColumn<Exercise, String> exerciseCol = new TableColumn<>("Exercise");
		exerciseCol.setCellFactory(tc -> { // this is so the text will wrap in the cell
			TableCell<Exercise, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);// text.getBoundsInLocal().getHeight() also works
			text.wrappingWidthProperty().bind(exerciseCol.widthProperty()); // you should bind to the width of the
			// columns not the width of the cell
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});
		exerciseCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));

		TableColumn<Exercise, String> repsCol = new TableColumn<>("Reps");
		setWrappingEditableCellFactory(repsCol);
		repsCol.setOnEditCommit(new EventHandler<CellEditEvent<Exercise, String>>() {
			@Override
			public void handle(CellEditEvent<Exercise, String> t) {
				((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReps(t.getNewValue());
			}
		});
		repsCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("reps"));

		TableColumn<Exercise, String> setsCol = new TableColumn<>("Sets");
		setsCol.setOnEditCommit(new EventHandler<CellEditEvent<Exercise, String>>() {
			@Override
			public void handle(CellEditEvent<Exercise, String> t) {
				((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSets(t.getNewValue());
			}
		});
		setWrappingEditableCellFactory(setsCol);
		setsCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("sets"));

		TableColumn<Exercise, String> weightsCol = new TableColumn<>("Weight");
		weightsCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("weight"));
		weightsCol.setCellFactory(tc -> { // this is so the text will wrap in the cell
			TableCell<Exercise, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);// text.getBoundsInLocal().getHeight() also works
			text.wrappingWidthProperty().bind(exerciseCol.widthProperty()); // you should bind to the width of the
			// columns not the width of the cell
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});

		TableColumn<Exercise, String> exerciseTimeCol = new TableColumn<>("Time");
		exerciseCol.setCellFactory(tc -> { // this is so the text will wrap in the cell
			TableCell<Exercise, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);// text.getBoundsInLocal().getHeight() also works
			text.wrappingWidthProperty().bind(exerciseCol.widthProperty()); // you should bind to the width of the
			// columns not the width of the cell
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});
		exerciseTimeCol.setCellValueFactory(new PropertyValueFactory<Exercise, String>("time"));

		exerciseTableView.getColumns().addAll(exerciseCol, repsCol, setsCol, weightsCol, exerciseTimeCol);
		exerciseTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Goal, String> priorityCol = new TableColumn<>("Priority");
		priorityCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("priority"));
		setWrappingEditableCellFactory(priorityCol);
		priorityCol.setOnEditCommit(new EventHandler<CellEditEvent<Goal, String>>() {
			@Override
			public void handle(CellEditEvent<Goal, String> t) {
				Goal selectedGoal = ((Goal) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				selectedGoal.setPriority(t.getNewValue());
				System.out.println("ooooooooooooooo");
				//DailyData.getInstance().updateGoal(selectedGoal);
			}
		});
		priorityCol.setMaxWidth(75);
		priorityCol.setMinWidth(75);

		TableColumn<Goal, Boolean> achievedCol = new TableColumn<>("Achieved"); //setOnEditCommit does not work with checkboxtablecell
		achievedCol.setCellValueFactory(new PropertyValueFactory<Goal, Boolean>("checkedOff"));
		achievedCol.setPrefWidth(100);
		achievedCol.setMinWidth(100);
		achievedCol.setMaxWidth(150);
		//achievedCol.setStyle("-fx-alignment: CENTER;"); // places the checkbox in
		// the center of the column
		//achievedCol.setCellFactory(CheckBoxTableCell.forTableColumn(achievedCol);
		achievedCol.setCellFactory(tc -> {
			CheckBox checkBox = new CheckBox();
			TableCell<Goal, Boolean> cell = new TableCell<Goal, Boolean>(){
				@Override
				public void updateItem(Boolean item, boolean empty) {
					if(empty) {
						setGraphic(null);
					}else {
						checkBox.setSelected(item);
						setGraphic(checkBox);
					}
				}
			};
			checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
				Goal goal = (Goal)cell.getTableRow().getItem();
				goal.setCheckedOff(isSelected);
				System.out.println(goal.getCheckedOff());
				DailyData.getInstance().updateGoal(goal);
			});		
			cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			cell.setAlignment(Pos.CENTER);
			return cell;
		});

		TableColumn<Goal, String> descriptionCol = new TableColumn<>("Description");
		descriptionCol.setCellFactory(tc -> {
			TableCell<Goal, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.setId("text");
			text.wrappingWidthProperty().bind(descriptionCol.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("description"));

		TableColumn<Goal, String> durationCol = new TableColumn<>("Duration");
		durationCol.setCellFactory(tc -> { // this is so the text will wrap in the cell
			TableCell<Goal, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);// text.getBoundsInLocal().getHeight() also works
			text.wrappingWidthProperty().bind(durationCol.widthProperty()); // you should bind to the width of the
																			// columns not the width of the cell
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});
		durationCol.setCellValueFactory(new PropertyValueFactory<Goal, String>("duration"));
		durationCol.setMaxWidth(100);
		durationCol.setMinWidth(100);

		goalTableView.getColumns().addAll(priorityCol, achievedCol, descriptionCol, durationCol);
		goalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Task, String> timeCol = new TableColumn<>("Time");
		timeCol.setCellValueFactory(new PropertyValueFactory<Task, String>("time"));

		TableColumn<Task, String> eventCol = new TableColumn<>("Event");
		eventCol.setCellValueFactory(new PropertyValueFactory<Task, String>("event"));
		setWrappingEditableCellFactory(eventCol);
		eventCol.setOnEditCommit(new EventHandler<CellEditEvent<Task, String>>() {
			@Override
			public void handle(CellEditEvent<Task, String> t) {
				((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEvent(t.getNewValue());
			}
		});

		taskTableView.setEditable(true); // in order to be able to edit columns by double clicking, this needs to be set
											// to true
		exerciseTableView.setEditable(true);
		goalTableView.setEditable(true);

		taskTableView.getColumns().addAll(timeCol, eventCol);
		taskTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // this makes it so the header fits
																					// the entire tableview

		SortedList<Goal> sortedGoals = new SortedList<Goal>(day.getGoals(), new Comparator<Goal>() {
			@Override
			public int compare(Goal o1, Goal o2) {
				return o1.compareTo(o2);
			}
		});

		// sortedGoals.comparatorProperty().bind(goalTableView.comparatorProperty());

		SortedList<Task> sortedTasks = new SortedList<Task>(day.getTasks(), new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				return o1.compareTo(o2);
			}
		});

		// sortedTasks.comparatorProperty().bind(taskTableView.comparatorProperty());

		exerciseTableView.setItems(day.getExercises()); // setting the exercises in the exercise tableView
		goalTableView.setItems(sortedGoals); // setting the goals in the goal tableView
		taskTableView.setItems(sortedTasks);
		titleLabel.setText(day.toString());

		gridPane.setPadding(new Insets(5));
		gridPane.setVgap(5);

		taskBox.setHgrow(eventTF, Priority.ALWAYS); // this will take up all possible space when the program is maximized to full screen
		taskBox.setHgrow(hourTF1, Priority.ALWAYS);
		taskBox.setHgrow(hourTF2, Priority.ALWAYS);
		taskBox.setHgrow(minuteTF1, Priority.ALWAYS);
		taskBox.setHgrow(minuteTF2, Priority.ALWAYS);

		taskBox.getChildren().addAll(hourTF1, new Label(":"), minuteTF1, oneCB, new Label(" - "), hourTF2,
				new Label(":"), minuteTF2, twoCB); // how to add elements to a hbox
		taskBox2.getChildren().addAll(eventTF, addTask, deleteTask);

		gridPane.setHalignment(titleLabel, HPos.RIGHT);
		gridPane.setHalignment(addExercise, HPos.RIGHT);
		gridPane.setHalignment(addGoal, HPos.RIGHT);
		gridPane.setHalignment(addNote, HPos.RIGHT);

		gridPane.setColumnSpan(exerciseTableView, 2); // this is how many columns you want the tableviews to take up
		gridPane.setColumnSpan(goalTableView, 2);
		gridPane.setColumnSpan(titleLabel, 2);

		gridPane.setRowSpan(notesTA, 2);

		ColumnConstraints col = new ColumnConstraints(); // this is used to set how big you want the columns of the
															// gridpane to be
		col.setPercentWidth(60);
		gridPane.getColumnConstraints().add(col);

		gridPane.add(titleLabel, 0, 0);
		gridPane.add(new Label("Exercises"), 0, 1);
		gridPane.add(exerciseTableView, 0, 2);
		gridPane.add(new Label("Goals"), 0, 4);
		gridPane.add(addExercise, 1, 3);
		gridPane.add(goalTableView, 0, 5);
		gridPane.add(addGoal, 1, 6);
		gridPane.add(new Label("Schedule"), 0, 7);
		gridPane.add(taskTableView, 0, 8);
		gridPane.add(new Label("Notes"), 1, 7);
		gridPane.add(notesTA, 1, 8);
		gridPane.add(taskBox, 0, 9);
		gridPane.add(taskBox2, 0, 10);
		gridPane.add(addNote, 1, 10);

		addTask.setOnAction(e -> {
			String startHour = stringFunctions.editString(hourTF1.getText());
			String startMinute = stringFunctions.editString(minuteTF1.getText());
			String finishHour = stringFunctions.editString(hourTF2.getText());
			String finishMinute = stringFunctions.editString(minuteTF2.getText());
			String time = stringFunctions.determineStringforTimeRange(startHour, startMinute, finishHour, finishMinute,
					(String) oneCB.getValue(), (String) twoCB.getValue());
			String event = eventTF.getText();
			if (!time.equals("") && !(eventTF.getText().equals(""))) {
				Task task = new Task(time, event);
				day.addTask(task);
				hourTF1.setText(finishHour);
				minuteTF1.setText(finishMinute);
				oneCB.setValue(twoCB.getValue());
			} else {
				hourTF1.clear();
				minuteTF1.clear();
			}
			minuteTF2.clear();
			hourTF2.clear();
			eventTF.clear();
		});

		deleteTask.setOnAction(e -> {
			Task task = taskTableView.getSelectionModel().getSelectedItem();
			day.deleteTask(task);
		});

		rightClickExerciseTableView(exerciseTableView);
		rightClickGoalTableView(goalTableView);
		clickedAddExercise(addExercise);
		clickedAddGoal(addGoal);
		clickedAddNote(addNote);
		// clickedAddTask(timeTF, eventTF, addTask);

		notesTA.textProperty().bind(day.notesProperty());

		// notesTA.textProperty().addListener((observable, oldValue, newValue) -> {
		// if (!newValue.equals("")) {
		// notesTA.setText(day.getNotes());
		// }
		// });
		return gridPane;
	}

	public void setWrappingEditableCellFactory(TableColumn column) {
		column.setCellFactory(tc -> { // this is so the text will wrap in the cell
			TableCell cell = new WrappingTextFieldTableCell();
			// Text text = new Text();
			// cell.setGraphic(text);
			// cell.setPrefHeight(Control.USE_COMPUTED_SIZE);//
			// text.getBoundsInLocal().getHeight() also works
			// text.wrappingWidthProperty().bind(column.widthProperty()); // you should bind
			// to the width of the
			// // columns not the width of the cell
			// text.textProperty().bind(cell.itemProperty());
			return cell;
		});
	}

	public void rightClickExerciseTableView(TableView tv) {
		tv.setRowFactory(new Callback<TableView<Exercise>, TableRow<Exercise>>() {

			@Override
			public TableRow<Exercise> call(TableView<Exercise> param) {
				TableRow<Exercise> row = new TableRow<Exercise>();
				exerciseTVContextMenu = new ContextMenu();
				MenuItem deleteExerciseMI = new MenuItem("Delete Exercise");
				MenuItem editExerciseMI = new MenuItem("Edit Exercise");

				// this is for deleting a selected exercise
				deleteExerciseMI.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Exercise selectedExercise = (Exercise) tv.getSelectionModel().getSelectedItem();
						deleteSelectedExercise(selectedExercise);
					}
				});
				exerciseTVContextMenu.getItems().addAll(deleteExerciseMI);

				// this is for editing a selected exercise
				editExerciseMI.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Exercise selectedExercise = (Exercise) tv.getSelectionModel().getSelectedItem();
						editSelectedExercise(selectedExercise);
					}
				});
				exerciseTVContextMenu.getItems().addAll(editExerciseMI);

				// this is to make sure the context menu does not appear when there are no
				// exercises in the tableview
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

	public void clickedAddNote(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addNote();
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
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Exercise Statistics");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../view/exercisestatdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the exercise dialog");
			e.printStackTrace();
			return;
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Optional<ButtonType> result = dialog.showAndWait();
	}

	public void addNewExercise() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add New Exercise");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../view/addexercisedialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		addNewExerciseController controller = fxmlLoader.getController();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			controller.processResults(ExerciseData.getInstance());
			addNewExercise();
		}
	}

	public void removeUnusedExercise() {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Choose Muscle Area");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../view/musclegroupdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the muscle group dialog");
			e.printStackTrace();
			return;
		}
		MuscleGroupController mgController = fxmlLoader.getController();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);
		dialog.getDialogPane().lookupButton(ButtonType.NEXT).disableProperty()
				.bind(mgController.getMuscleComboB().valueProperty().isNull()); // the way to disable a button
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.NEXT) {
			String category = mgController.determineCategory();
			Dialog<ButtonType> dialog1 = new Dialog<ButtonType>();
			dialog1.initOwner(mainBorderPane.getScene().getWindow());
			dialog1.setTitle("Remove Exercise");
			FXMLLoader fxmlLoader1 = new FXMLLoader();
			fxmlLoader1.setLocation(getClass().getResource("../view/removeexercisedialog.fxml"));
			try {
				dialog1.getDialogPane().setContent(fxmlLoader1.load());
			} catch (IOException e) {
				System.out.println("Couldn't load the exercise dialog");
				e.printStackTrace();
				return;
			}
			dialog1.getDialogPane().getButtonTypes().add(ButtonType.OK);
			dialog1.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			removeExerciseController controller = fxmlLoader1.getController();
			controller.updateComboBox(category);
			Optional<ButtonType> result1 = dialog1.showAndWait();
			if (result1.isPresent() && result1.get() == ButtonType.OK) {
				controller.processResults(ExerciseData.getInstance(), category);
			}
		}
	}
	
	public void showGoalStatistics() {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Goal Statistics");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../view/goalstatdialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (IOException e) {
			System.out.println("Couldn't load the exercise dialog");
			e.printStackTrace();
			return;
		}
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		goalStatController controller = fxmlLoader.getController();
		controller.updateStats();
		Optional<ButtonType> result = dialog.showAndWait();
	}
}
