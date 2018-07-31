
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.io.Serializable;

public class Goal implements Serializable, Comparable {

	private SimpleStringProperty priority = new SimpleStringProperty("");
	private SimpleStringProperty description = new SimpleStringProperty("");
	private SimpleStringProperty duration = new SimpleStringProperty("");

	private StringFunctions stringFunctions = new StringFunctions();

	private CheckBox achieved;

	public Goal(String description, String priority, String duration) {
		this.description.set(description);
		this.achieved = new CheckBox();
		setPriority(priority);
		this.duration.set(duration);
	}

	public String getDescription() {
		return description.get();
	}

	public SimpleStringProperty descriptionProperty() { // method must be called descriptionProperty() otherwise the
														// tableview will not update immediately
		return description;
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public CheckBox isAchieved() {
		return achieved;
	}

	public void setAchieved(CheckBox achieved) {
		this.achieved = achieved;
	}

	public SimpleStringProperty priorityProperty() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority.set(stringFunctions.digitOnlyString(priority));
	}

	public SimpleStringProperty durationProperty() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration.set(duration);
	}

	public String getPriority() {
		return priority.get();
	}

	public String getDuration() {
		return duration.get();
	}

	@Override
	public int compareTo(Object goal) {
		int num = 0;
		int priorityOne;
		int priorityTwo;
		if (goal instanceof Goal) {
			if (!this.getPriority().equals("") && (!((Goal) goal).getPriority().equals(""))) {
				priorityOne = Integer.parseInt(this.getPriority());
				priorityTwo = Integer.parseInt(((Goal) goal).getPriority());
				if (priorityOne > priorityTwo) {
					num = 1;
				} else if (priorityOne < priorityTwo) {
					num = -1;
				} else
					num = 0;
			} else if (!this.getPriority().equals("") && ((Goal) goal).getPriority().equals("")) {
				num = -1;
			} else if (this.getPriority().equals("") && (!((Goal) goal).getPriority().equals(""))) {
				num = 1;
			}
		}
		return num;

	}
}
