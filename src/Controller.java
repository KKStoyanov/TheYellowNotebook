
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainBorderPane;

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

    public void initialize() {
        achievedColumn.setStyle("-fx-alignment: CENTER;"); //places the checkbox in the center of the column


        dayContextMenu = new ContextMenu();
        MenuItem deleteDayMI = new MenuItem("Delete");
        deleteDayMI.setOnAction(new EventHandler<ActionEvent>() { //what to do when a cell in the listView is right-clicked
            @Override
            public void handle(ActionEvent event) {
                Day selectedDay = dayListView.getSelectionModel().getSelectedItem(); //checking what day was selected
                deleteSelectedDay(selectedDay); //deleting the selected day
            }
        });
        dayContextMenu.getItems().addAll(deleteDayMI); //adding the delete button to the context menu

        //this replaces handleListView; don't need onMouseClicked in  FXML file
        dayListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Day>() {
            @Override
            public void changed(ObservableValue<? extends Day> observable, Day oldValue, Day newValue) {
                if (newValue != null) { //this checks that what we are clicking in the ListView is not null
                    Day day = dayListView.getSelectionModel().getSelectedItem(); //the day that is selected on the listView
                    exerciseTableView.setItems(day.getExercises()); //setting the exercises in the exercise tableView
                    goalTableView.setItems(day.getGoals()); //setting the goals in the goal tableView
                    titleLabel.setText(day.toString());
                }
            }
        });

        //sorting the days in the list view
        SortedList<Day> sortedList = new SortedList<Day>(DailyData.getInstance().getDays(), new Comparator<Day>() {
            @Override
            public int compare(Day o1, Day o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        dayListView.setItems(sortedList);

        //as of right now this is only for the context menu
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
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(dayContextMenu);
                            }
                        });
                return cell;
            }
        });

        //similar to deleting day in listView
        exerciseTableView.setRowFactory(new Callback<TableView<Exercise>, TableRow<Exercise>>() {
            @Override
            public TableRow<Exercise> call(TableView<Exercise> param) {
                TableRow<Exercise> row = new TableRow<Exercise>();
                exerciseTVContextMenu = new ContextMenu();
                MenuItem deleteExerciseMI = new MenuItem("Delete Exercise");
                MenuItem editExerciseMI = new MenuItem("Edit Exercise");
                deleteExerciseMI.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Exercise selectedExercise = exerciseTableView.getSelectionModel().getSelectedItem();
                        deleteSelectedExercise(selectedExercise);
                    }
                });
                exerciseTVContextMenu.getItems().addAll(deleteExerciseMI);
                editExerciseMI.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Exercise selectedExercise = exerciseTableView.getSelectionModel().getSelectedItem();
                        editSelectedExercise(selectedExercise);
                    }
                });
                exerciseTVContextMenu.getItems().addAll(editExerciseMI);
                row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(exerciseTVContextMenu));
                return row;
            }
        });

        goalTableView.setRowFactory(new Callback<TableView<Goal>, TableRow<Goal>>() {
            @Override
            public TableRow<Goal> call(TableView<Goal> param) {
                TableRow<Goal> row = new TableRow<Goal>();
                goalTVContextMenu = new ContextMenu();
                MenuItem deleteGoalMI = new MenuItem("Delete Goal");
                MenuItem editGoalMI = new MenuItem("Edit Goal");
                deleteGoalMI.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Goal selectedGoal = goalTableView.getSelectionModel().getSelectedItem();
                        deleteSelectedGoal(selectedGoal);
                    }
                });
                goalTVContextMenu.getItems().addAll(deleteGoalMI);
                editGoalMI.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Goal selectedGoal = goalTableView.getSelectionModel().getSelectedItem();
                        editSelectedGoal(selectedGoal);
                    }
                });
                goalTVContextMenu.getItems().addAll(editGoalMI);
                row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(goalTVContextMenu));
                return row;
            }
        });

        dayListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void addDate() { //method ran when user opens the menubar and presses add date
        Dialog<ButtonType> dialog = new Dialog<ButtonType>(); //<ButtonType> is used to represent the type of the result property
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
        //dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
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
        Day day = dayListView.getSelectionModel().getSelectedItem(); //the day that is selected on the listView
        exerciseTableView.setItems(day.getExercises()); //setting the exercises in the exercise tableView
        goalTableView.setItems(day.getGoals()); //setting the goals in the goal tableView
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
     * This method will edit a goal that is right clicked. It creates a dialog with an OK and cancel button, and a large textarea.
     * The text area contains the current description of the goal. The controller of the dialog is used to call the editGoal and updateGoal methods.
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
        } else category = "H";

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
        } else path = "cardioexercisedialog.fxml";

        return path;
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
