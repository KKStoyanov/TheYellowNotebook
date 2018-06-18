
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Day implements Serializable{

    private ObservableList<Exercise> exercises;
    private ObservableList<Goal> goals;
    private LocalDate date;

    public Day(ObservableList<Exercise> exercises, ObservableList<Goal> goals, LocalDate date){
        this.exercises = exercises;
        this.goals = goals;
        this.date = date;
    }

    public Day(LocalDate date){
        this.date = date;
        exercises = FXCollections.observableArrayList();
        goals = FXCollections.observableArrayList();
    }

    public ObservableList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ObservableList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise){
        exercises.add(exercise);
    }

    public void deleteExercise(Exercise exercise){
        exercises.remove(exercise);
    }

    public ObservableList<Goal> getGoals() {
        return goals;
    }

    public void setGoals(ObservableList<Goal> goals) {
        this.goals = goals;
    }

    public void addGoal(Goal goal){
        goals.add(goal);
    }

    public void deleteGoal(Goal goal){
        goals.remove(goal);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return df.format(getDate());
    }
}
