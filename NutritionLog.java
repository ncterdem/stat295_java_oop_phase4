import java.util.*;

public class NutritionLog {
    private String date;
    private List<FoodItem> foodItems;

    public NutritionLog(String date) {
        this.date = date;
        this.foodItems = new ArrayList<>();
    }

    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public int getTotalCalories() {
        return foodItems.stream().mapToInt(FoodItem::getCalories).sum();
    }
}