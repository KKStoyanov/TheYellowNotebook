
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Exercise implements Serializable{

    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty reps = new SimpleStringProperty("");
    private SimpleStringProperty sets = new SimpleStringProperty("");
    private SimpleStringProperty weight = new SimpleStringProperty("");
    private String category;

    public Exercise(String name, String reps, String sets, String weight, String category){
        this.name.set(name);
        this.reps.set(reps);
        this.sets.set(sets);
        this.weight.set(weight);
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
        this.reps.set(reps);
    }

    public String getSets() {
        return sets.get();
    }

    public SimpleStringProperty setsProperty() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets.set(sets);
    }

    public String getWeight() {
        return weight.get();
    }

    public SimpleStringProperty weightProperty() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight.set(weight);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
