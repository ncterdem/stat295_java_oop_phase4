import java.util.ArrayList;
import java.util.List;

public class WorkoutLog {
    private String date;
    private List<Exercise> exercises = new ArrayList<>();

    public WorkoutLog(String date) {
        this.date = date;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public int getTotalCaloriesBurned() {
        int total = 0;
        for (Exercise e : exercises) {
            total += e.calculateCaloriesBurned();
        }
        return total;
    }
    public List<Exercise> getExercises() {
        return exercises;
    }

}
