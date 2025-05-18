import java.util.HashMap;
import java.util.Map;

public class StrengthTrainingDatabase {
    private static final Map<String, StrengthTraining> strengthMap = new HashMap<>();

    static {
        strengthMap.put("Squat", new StrengthTraining("Squat", 15, 3, 10, 60));
        strengthMap.put("Bench Press", new StrengthTraining("Bench Press", 20, 3, 8, 50));
        strengthMap.put("Deadlift", new StrengthTraining("Deadlift", 15, 3, 5, 80));
        strengthMap.put("Shoulder Press", new StrengthTraining("Shoulder Press", 20, 3, 8, 50));
        strengthMap.put("Pull Up", new StrengthTraining("Pull Up", 20, 3, 8, 50));
    }


    public static void printAvailableStrengthExercises() {
        System.out.println("Available strength exercises: " + strengthMap.keySet());
    }
}
