
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Exercises {

    private ObservableList<String> chestExercises;
    private ObservableList<Exercise> legExercises;
    private ObservableList<Exercise> cardioExercises;
    private ObservableList<Exercise> armExercises;
    private ObservableList<Exercise> abExercises;

    public Exercises(){
        Exercise pushUps = new Exercise("Push Ups", "0", "0", "0", "P");
        Exercise nPushUps = new Exercise("Narrow Push Ups", "0", "0", "0","P");
        Exercise wPushUps = new Exercise("Wide Push Ups", "0", "0", "0", "P");
        Exercise bbellPress = new Exercise("Flat Bench Barbell Press", "0", "0", "0", "P");
        Exercise dBbellPress = new Exercise("Declined Bench Barbell Press", "0", "0", "0", "P");
        Exercise iBbellPress = new Exercise("Inclined Bench Barbell Press", "0", "0", "0", "P");

        chestExercises = FXCollections.observableArrayList();
        chestExercises.add("Push Ups");
        chestExercises.add("Narrow Push Ups");
        chestExercises.add("Wide Push Ups");
        chestExercises.add("Barbell Press");
        chestExercises.add("Declined Barbell Press");
        chestExercises.add("Inclined Barbell Press");
    }

    public void addChestExercise(String exercise){
        chestExercises.add(exercise);
    }

    public ObservableList<String> getChestExercises() {
        return chestExercises;
    }
}
