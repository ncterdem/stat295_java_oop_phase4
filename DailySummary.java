public class DailySummary {
    private String date;
    private int totalCaloriesIn, totalCaloriesOut;
    private boolean goalMet;

    public void summarizeDay(NutritionLog nutrition, WorkoutLog workout, int target) {
        this.totalCaloriesIn = nutrition.getTotalCalories();
        this.totalCaloriesOut = workout.getTotalCaloriesBurned();
        this.goalMet = (totalCaloriesIn - totalCaloriesOut) <= target;
    }

    public boolean isGoalMet() {
        return goalMet;
    }
    public int getTotalCaloriesIn() {
        return totalCaloriesIn;
    }

    public int getTotalCaloriesOut() {
        return totalCaloriesOut;
    }

}
