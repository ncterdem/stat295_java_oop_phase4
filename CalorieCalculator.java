public class CalorieCalculator {
    public static int calculateCaloriesConsumed(NutritionLog log) {
        return log.getTotalCalories();
    }

    public static int calculateCaloriesBurned(WorkoutLog log) {
        return log.getTotalCaloriesBurned();
    }
}
