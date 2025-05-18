import java.util.HashMap;
import java.util.Map;

public class StrengthTrainingDatabase {
    private static final Map<String, StrengthTraining> strengthMap = new HashMap<>();

    static {
        strengthMap.put("squat", new StrengthTraining("Squat", 15, 3, 10, 60));
        strengthMap.put("bench press", new StrengthTraining("Bench Press", 20, 3, 8, 50));
        strengthMap.put("deadlift", new StrengthTraining("Deadlift", 15, 3, 5, 80));
        strengthMap.put("shoulder press", new StrengthTraining("Shoulder Press", 20, 3, 8, 50));
        strengthMap.put("pull up", new StrengthTraining("Pull Up", 20, 3, 8, 50));
    }


    public static void printAvailableStrengthExercises() {
        System.out.println("Available strength exercises: " + strengthMap.keySet());
    }
}
