import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class noteController {

	@FXML
	private TextArea noteTA;

	public String processResults() {
		return noteTA.getText();
	}
	
	public void displayOldNotes(String notes) {
		noteTA.setText(notes);
	}

	public boolean containsEmptyTextArea() {
		return noteTA.getText().trim().isEmpty();
	}

}
