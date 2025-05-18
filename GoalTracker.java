import java.util.List;

public class GoalTracker {
    private User user;
    private List<NutritionLog> nutritionLogs;
    private List<WorkoutLog> workoutLogs;

    public GoalTracker(User user, List<NutritionLog> nutritionLogs, List<WorkoutLog> workoutLogs) {
        this.user = user;
        this.nutritionLogs = nutritionLogs;
        this.workoutLogs = workoutLogs;
    }

    public void trackProgress() {
        if (nutritionLogs.isEmpty() || workoutLogs.isEmpty()) {
            System.out.println("No data available to track progress.");
            return;
        }

        int totalIn = 0;
        int totalOut = 0;

        for (int i = 0; i < nutritionLogs.size(); i++) {
            totalIn += nutritionLogs.get(i).getTotalCalories();
            totalOut += workoutLogs.get(i).getTotalCaloriesBurned();
        }

        int net = totalIn - totalOut;
        int avgNet = net / nutritionLogs.size();
        int target = user.getDailyCalorieTarget();

        String result = switch (user.getGoalType()) {
            case LOSE_WEIGHT -> (avgNet < target) ? "✅ On track to lose weight" : "⚠️ Too much intake";
            case GAIN_MUSCLE -> (avgNet > target) ? "✅ On track to gain muscle" : "⚠️ Not eating enough";
            case MAINTAIN -> Math.abs(avgNet - target) < 100 ? "✅ Maintaining well" : "⚠️ Needs adjustment";
        };

        System.out.println("📈 Goal Tracker Result: " + result);
    }
}
