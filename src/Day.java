
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Day implements Serializable{

    private ObservableList<Exercise> exercises;
    private ObservableList<Goal> goals;
    private LocalDate date;
    private ObservableList<Task> tasks;
    private SimpleStringProperty notes = new SimpleStringProperty("");
    private int id;

    public Day(ObservableList<Exercise> exercises, ObservableList<Goal> goals, LocalDate date, ObservableList<Task> tasks, String notes){
        this.exercises = exercises;
        this.goals = goals;
        this.date = date;
        this.tasks = tasks;
        this.notes.set(notes);
    }

    public Day(LocalDate date){
        this.date = date;
        exercises = FXCollections.observableArrayList();
        goals = FXCollections.observableArrayList();
        tasks = FXCollections.observableArrayList();
        notes.set("");
    }
    
    public ObservableList<Task> getTasks(){
    	return tasks;
    }
    
    public void setTasks(ObservableList<Task> tasks) {
    	this.tasks = tasks;
    }
    
    public void addTask(Task task) {
    	tasks.add(task);
    }
    
    public void deleteTask(Task task) {
    	tasks.remove(task);
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
    
    public void setNotes(String notes) {
    	this.notes.set(notes);
    }
    
    public String getNotes() {
    	return notes.get();
    }
    
    public SimpleStringProperty notesProperty() {
    	return notes;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return df.format(getDate());
    }
}
