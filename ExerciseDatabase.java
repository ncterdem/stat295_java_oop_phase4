import java.util.HashMap;
import java.util.Map;

public class ExerciseDatabase {
    private static final Map<String, Double> metValues = new HashMap<>();
    private static final Map<String, String> intensityMap = new HashMap<>();

    static {
        metValues.put("running", 9.8);
        metValues.put("walking", 3.5);
        metValues.put("cycling", 7.5);
        metValues.put("swimming", 8.0);

        intensityMap.put("running", "high");
        intensityMap.put("walking", "low");
        intensityMap.put("cycling", "moderate");
        intensityMap.put("swimming", "high");
    }


    public static String getIntensity(String type) {
        return intensityMap.getOrDefault(type.toLowerCase(), "moderate");
    }

    public static void printAvailableExercises() {
        System.out.println("Available cardio exercises: " + metValues.keySet());
    }
}
