import java.util.HashMap;
import java.util.Map;

public class ExerciseDatabase {
    static final Map<String, Double> metValues = new HashMap<>();
    static final Map<String, String> intensityMap = new HashMap<>();

    static {
        metValues.put("Running", 9.8);
        metValues.put("Walking", 3.5);
        metValues.put("Cycling", 7.5);
        metValues.put("Swimming", 8.0);

        intensityMap.put("Running", "high");
        intensityMap.put("Walking", "low");
        intensityMap.put("Cycling", "moderate");
        intensityMap.put("Swimming", "high");
    }


    public static String getIntensity(String type) {
        return intensityMap.getOrDefault(type, "moderate");
    }

    public static void printAvailableExercises() {
        System.out.println("Available cardio exercises: " + metValues.keySet());
    }
}
