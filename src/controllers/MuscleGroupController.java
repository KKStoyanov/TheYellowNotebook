package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import singletons.DailyData;
import singletons.ExerciseData;

import java.io.IOException;
import java.util.Optional;

public class MuscleGroupController {

    @FXML
    private ComboBox<String> muscleComboB;

    @FXML
    private DialogPane mainDP;

    @FXML
    private DialogPane mgDialog;

    private ExerciseData exercises;

    private DailyData data;

    public void initialize(){

    }

    public String determineCategory() {
        String category = "";
        if(muscleComboB.getSelectionModel().getSelectedItem().equals("Chest")){
            category = "P";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Core")) {
            category = "C";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Arms")) {
            category = "A";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Legs")) {
            category = "L";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Back")) {
            category = "B";
        } else if (muscleComboB.getSelectionModel().getSelectedItem().equals("Cardio")) {
            category = "H";
        }
        return category;
    }
    
    public boolean safeToProceed() {
    	return !muscleComboB.getSelectionModel().isEmpty();
    }
    
    public ComboBox<String> getMuscleComboB() {
        return muscleComboB;
    }

    public DialogPane getMainDP() {
        return mainDP;
    }
}
