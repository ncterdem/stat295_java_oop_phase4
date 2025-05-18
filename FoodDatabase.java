import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDatabase {
    private static final Map<String, FoodItem> foodMap = new HashMap<>();

    static {
        foodMap.put("apple", new FoodItem("Apple", 95, 0, 25, 0));
        foodMap.put("banana", new FoodItem("Banana", 105, 1, 27, 0));
        foodMap.put("chicken breast", new FoodItem("Chicken Breast", 200, 30, 0, 5));
        foodMap.put("rice", new FoodItem("Rice", 206, 4, 45, 0));
    }

    public static FoodItem getFood(String name) {
        return foodMap.get(name.toLowerCase());
    }

    public static void printAvailableFoods() {
        System.out.println("Available foods: " + foodMap.keySet());
    }

    // FoodDatabase.java - Add this method
    public static List<String> getFoodNames() {
        return new ArrayList<>(foodMap.keySet());
    }
}
