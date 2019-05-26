package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.Statistics;
public class exerciseStatController {

    @FXML
    private Label maxRepLabel;

    @FXML
    private Label totalRepsLabel;

    @FXML
    private Label maxWeightLabel;

    @FXML
    private Label bestTimeLabel;
    
    @FXML
    private TextField exerciseNameTF;
    
    private Statistics statistics;
    
    public void updateStats() {
    	statistics = new Statistics();
    	String exercise = exerciseNameTF.getText();
    	maxRepLabel.textProperty().bind(statistics.getMaxReps(exercise));
    	totalRepsLabel.textProperty().bind(statistics.getTotalReps(exercise));
    	maxWeightLabel.textProperty().bind(statistics.getMaxWeight(exercise));
    	bestTimeLabel.textProperty().bind(statistics.getBestTime(exercise));
    }

}
