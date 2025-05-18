import java.util.List;

public class StrengthExerciseTemplates {
    static final List<String> availableExercises = List.of(
            "squat", "bench press", "deadlift", "shoulder press", "pull up"
    );

    public static boolean isAvailable(String name) {
        return availableExercises.contains(name.toLowerCase());
    }

    public static void printAvailableExercises() {
        System.out.println("Available strength exercises: " + availableExercises);
    }
}
