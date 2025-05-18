import java.util.HashMap;
import java.util.Map;

public class StrengthTrainingDatabase {
    private static final Map<String, StrengthTraining> strengthMap = new HashMap<>();

    static {
        strengthMap.put("squat", new StrengthTraining("Squat", 15, 3, 10, 60));
        strengthMap.put("bench press", new StrengthTraining("Bench Press", 20, 3, 8, 50));
        strengthMap.put("deadlift", new StrengthTraining("Deadlift", 15, 3, 5, 80));
    }

    public static StrengthTraining getExercise(String name) {
        StrengthTraining ex = strengthMap.get(name.toLowerCase());
        if (ex == null) return null;

        return new StrengthTraining(
                ex.getName(),
                ex.getDuration(),
                ex.getSets(),
                ex.getReps(),
                ex.getWeight()
        );
    }

    public static void printAvailableStrengthExercises() {
        System.out.println("Available strength exercises: " + strengthMap.keySet());
    }
}
