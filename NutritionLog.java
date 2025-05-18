import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class NutritionLog implements Serializable {
    private String date;
    private List<FoodItem> foodItems = new ArrayList<>();
    private double waterIntake;

    public NutritionLog(String date) {
        this.date = date;
    }

    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
    }

    public int getTotalCalories() {
        int total = 0;
        for (FoodItem item : foodItems) {
            total += item.getCalories();
        }
        return total;
    }

    public void logWater(double amount) {
        waterIntake += amount;
    }
}
