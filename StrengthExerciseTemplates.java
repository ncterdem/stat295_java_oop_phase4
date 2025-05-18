import java.util.List;

public class StrengthExerciseTemplates {
    static final List<String> availableExercises = List.of(
            "Squat", "Bench Press", "Deadlift", "Shoulder Press", "Pull Up"
    );

    public static boolean isAvailable(String name) {
        return availableExercises.contains(name);
    }

    public static void printAvailableExercises() {
        System.out.println("Available strength exercises: " + availableExercises);
    }
}
