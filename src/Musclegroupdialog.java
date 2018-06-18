

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.Optional;

public class Musclegroupdialog {

    @FXML
    private ComboBox<String> muscleComboB;

    @FXML
    private DialogPane mainDP;

    @FXML
    private DialogPane mgDialog;

    private Exercises exercises;

    private DailyData data;

    public void initialize(){

    }

    public String getCorrectPath() {
        String properDialog = "";
        if(muscleComboB.getSelectionModel().getSelectedItem().equals("Chest (Chest, Triceps)")){
            properDialog = "chestexercisedialog.fxml";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Core (Abs, Obliques)")) {
            properDialog = "abexercisedialog.fxml";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Arms (Biceps, Forearms, Shoulders, Lats, Upper Back)")) {
            properDialog = "armexercisedialog.fxml";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Legs (Calves, Quads, Glutes, Lower Back, Shins)")) {
            properDialog = "legexercisedialog.fxml";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Cardio")) {
            properDialog = "cardioexercisedialog.fxml";
        }
        return properDialog;
    }
    public ComboBox<String> getMuscleComboB() {
        return muscleComboB;
    }

    public DialogPane getMainDP() {
        return mainDP;
    }
}
