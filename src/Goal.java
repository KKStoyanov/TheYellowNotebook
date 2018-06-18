

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.io.Serializable;

public class Goal implements Serializable{

    private SimpleStringProperty description = new SimpleStringProperty("");
    private CheckBox achieved;

    public Goal(String description){
        this.description.set(description);
        this.achieved = new CheckBox();
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
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
}
