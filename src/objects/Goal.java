package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import services.StringFunctions;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Goal implements Serializable, Comparable {

	private String uniqueID;
	private SimpleStringProperty priority = new SimpleStringProperty("");
	private SimpleStringProperty description = new SimpleStringProperty("");
	private SimpleStringProperty duration = new SimpleStringProperty("");
	private SimpleBooleanProperty checkedOff = new SimpleBooleanProperty(false);
	private LocalDate date;

	private StringFunctions stringFunctions = new StringFunctions();

	public Goal(String description, String priority, Boolean checkedOff, String duration, LocalDate date, String id) {
		if(id.equals("")) {
			uniqueID = UUID.randomUUID().toString();
		}else {
			uniqueID = id;
		}
		this.description.set(description);
		this.checkedOff.set(checkedOff);
		setPriority(priority);
		this.duration.set(duration);
		this.date = date;
	}
	
	public String getID() {
		return uniqueID;
	}
	
	public boolean getCheckedOff() {
		return checkedOff.get();
	}

	public SimpleBooleanProperty checkedOffProperty() {
		return checkedOff;
	}

	public void setCheckedOff(Boolean checkedOff) {
		this.checkedOff.set(checkedOff);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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
