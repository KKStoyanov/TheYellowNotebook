package objects;

import javafx.beans.property.SimpleStringProperty;
import services.StringFunctions;

import java.io.Serializable;
import java.time.LocalDate;

public class Exercise implements Serializable {

	private SimpleStringProperty name = new SimpleStringProperty("");
	private SimpleStringProperty reps = new SimpleStringProperty("");
	private SimpleStringProperty sets = new SimpleStringProperty("");
	private SimpleStringProperty weight = new SimpleStringProperty("");
	private SimpleStringProperty time = new SimpleStringProperty("");
	private String category;
	private LocalDate date;
	private StringFunctions stringFunctions = new StringFunctions();

	public Exercise(String name, String reps, String sets, String weight, String time, String category, LocalDate date) {
		this.name.set(name);
		this.reps.set(reps);
		this.sets.set(sets);
		this.weight.set(weight);
		this.time.set(time);
		this.date = date;
		this.category = category;

	}

	public String getName() {
		return name.get();
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getReps() {
		return reps.get();
	}

	public SimpleStringProperty repsProperty() {
		return reps;
	}

	public void setReps(String reps) {
		reps = stringFunctions.digitOnlyString(reps);
		if (reps.equals("") || reps.length() > 9 || Integer.parseInt(reps) == 0) {
			reps = "N/A";
		}
		this.reps.set(reps);
	}

	public String getSets() {
		return sets.get();
	}

	public SimpleStringProperty setsProperty() {
		return sets;
	}

	public void setSets(String sets) {
		sets = stringFunctions.digitOnlyString(sets);
		if (sets.equals("") || sets.length() > 9 || Integer.parseInt(sets) == 0) {
			sets = "N/A";
		}
		this.sets.set(sets);
	}

	public String getWeight() {
		return weight.get();
	}

	public SimpleStringProperty weightProperty() {
		return weight;
	}

	public void setWeight(String weight) {
		String numWeight = weight.substring(0, weight.length() - 2);
		if (numWeight.length() > 9) {
			weight = "Out of Range";
		} else if (numWeight.equals("") || Integer.parseInt(numWeight) == 0) {
			weight = "N/A";
		}
		this.weight.set(weight);
	}

	public String getTime() {
		return time.get();
	}

	public SimpleStringProperty timeProperty() {
		return time;
	}

	public void setTime(String time) {
		this.time.set(time);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	 public LocalDate getDate() {
	        return date;
	    }

	    public void setDate(LocalDate date) {
	        this.date = date;
	    }

}
