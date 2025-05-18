public class User {
    private String name;
    private int age;
    private double height; // in meters
    private double weight; // in kg
    private GoalType goalType;
    private int dailyCalorieTarget;

    public User(String name, int age, double height, double weight, GoalType goalType) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goalType = goalType;
        this.dailyCalorieTarget = calculateCalorieTarget();
    }

    private int calculateCalorieTarget() {
        double bmr = 10 * weight + 6.25 * height * 100 - 5 * age + 5; // Mifflin-St Jeor formula
        return switch (goalType) {
            case LOSE_WEIGHT -> (int)(bmr - 500);
            case GAIN_MUSCLE -> (int)(bmr + 300);
            case MAINTAIN -> (int)(bmr);
        };
    }

    public double getBMI() {
        return weight / (height * height);
    }

    public double getWeight() { return weight; }

    public int getDailyCalorieTarget() { return dailyCalorieTarget; }

    public GoalType getGoalType() { return goalType; }

    public String getGoalDescription() {
        return switch (goalType) {
            case LOSE_WEIGHT -> "Lose Weight";
            case GAIN_MUSCLE -> "Gain Muscle";
            case MAINTAIN -> "Maintain Weight";
        };
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }


}
