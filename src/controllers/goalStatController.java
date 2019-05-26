package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.Statistics;

public class goalStatController {
	
	@FXML
	private Label successLabel;
	
	@FXML
	private Label failedLabel;
	
	private Statistics stats;
	
	public void updateStats() {
		stats = new Statistics();
		successLabel.textProperty().bind(stats.totalAchievedGoals());
		failedLabel.textProperty().bind(stats.totalFailedGoals());
	}

}
