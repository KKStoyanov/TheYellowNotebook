
public class Statistics {

    public String getMaxPushUps(DailyData data){
        int maxPushUps = 0;
        for(Day day : data.getDays()){ //looping through all the days
            for(Exercise exercise : day.getExercises()){ //looping through all the exercises in each day
                if(exercise.getName().equals("Push Ups")){ //finding the push up exercise
                    int reps = Integer.valueOf(exercise.getReps()); //checking if its reps are greater than the current reps
                    if(reps > maxPushUps){
                        maxPushUps = reps; //setting the new max
                    }
                }
            }
        }
        return String.valueOf(maxPushUps);
    }

    public String getMaxFlatBench(DailyData data){
        int maxFlatBench = 0;
        for(Day day : data.getDays()){
            for(Exercise exercise : day.getExercises()){
                if(exercise.getName().equals("Flat Barbell Press")){
                    int weight = Integer.valueOf(exercise.getWeight());
                    if(weight > maxFlatBench){
                        maxFlatBench = weight;
                    }
                }
            }
        }
        return String.valueOf(maxFlatBench);
    }

    public String getMaxDeclineBench(DailyData data){
        int maxDeclineBench = 0;
        for(Day day : data.getDays()){
            for(Exercise exercise : day.getExercises()){
                if(exercise.getName().equals("Declined Barbell Press")){
                    int weight = Integer.valueOf(exercise.getWeight());
                    if(weight > maxDeclineBench){
                        maxDeclineBench = weight;
                    }
                }
            }
        }
        return String.valueOf(maxDeclineBench);
    }

    public String getMaxInclineBench(DailyData data){
        int maxInclineBench = 0;
        for(Day day : data.getDays()){
            for(Exercise exercise : day.getExercises()){
                if(exercise.getName().equals("Inclined Barbell Press")){
                    int weight = Integer.valueOf(exercise.getWeight());
                    if(weight > maxInclineBench){
                        maxInclineBench = weight;
                    }
                }
            }
        }
        return String.valueOf(maxInclineBench);
    }

    public String getMaxSquat(DailyData data){
        int maxSquat = 0;
        for(Day day : data.getDays()){
            for(Exercise exercise : day.getExercises()){
                if(exercise.getName().equals("Squats")){
                    int weight = Integer.valueOf(exercise.getWeight());
                    if(weight > maxSquat){
                        maxSquat = weight;
                    }
                }
            }
        }
        return String.valueOf(maxSquat);
    }
}
