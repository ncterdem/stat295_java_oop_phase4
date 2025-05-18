import java.util.HashMap;
import java.util.Map;

public class FoodDatabase {
    public static final Map<String, FoodItem> foodMap = new HashMap<>();

    static {
        foodMap.put("Almonds", new FoodItem("Almonds", 164, 6, 6, 14));
        foodMap.put("Apple", new FoodItem("Apple", 95, 0, 25, 0));
        foodMap.put("Asparagus", new FoodItem("Asparagus", 20, 2, 4, 0));
        foodMap.put("Avocado", new FoodItem("Avocado", 234, 3, 12, 21));
        foodMap.put("Banana", new FoodItem("Banana", 105, 1, 27, 0));
        foodMap.put("Beef", new FoodItem("Beef", 250, 26, 0, 18));
        foodMap.put("Bell Pepper", new FoodItem("Bell Pepper", 31, 1, 7, 0));
        foodMap.put("Black Beans", new FoodItem("Black Beans", 227, 15, 41, 1));
        foodMap.put("Broccoli", new FoodItem("Broccoli", 55, 4, 11, 0));
        foodMap.put("Brown Rice", new FoodItem("Brown Rice", 216, 5, 45, 2));
        foodMap.put("Cabbage", new FoodItem("Cabbage", 22, 1, 5, 0));
        foodMap.put("Carrot", new FoodItem("Carrot", 25, 1, 6, 0));
        foodMap.put("Cauliflower", new FoodItem("Cauliflower", 25, 2, 5, 0));
        foodMap.put("Cheese", new FoodItem("Cheese", 113, 7, 1, 9));
        foodMap.put("Chicken Breast", new FoodItem("Chicken Breast", 200, 30, 0, 5));
        foodMap.put("Chickpeas", new FoodItem("Chickpeas", 269, 15, 45, 4));
        foodMap.put("Corn", new FoodItem("Corn", 96, 3, 21, 1));
        foodMap.put("Cucumber", new FoodItem("Cucumber", 16, 1, 4, 0));
        foodMap.put("Egg", new FoodItem("Egg", 78, 6, 1, 5));
        foodMap.put("Eggplant", new FoodItem("Eggplant", 25, 1, 6, 0));
        foodMap.put("Grapes", new FoodItem("Grapes", 62, 1, 16, 0));
        foodMap.put("Green Beans", new FoodItem("Green Beans", 31, 2, 7, 0));
        foodMap.put("Strained Yogurt", new FoodItem("Strained Yogurt", 120, 12, 8, 5));
        foodMap.put("Kiwi", new FoodItem("Kiwi", 42, 1, 10, 0));
        foodMap.put("Lentils", new FoodItem("Lentils", 230, 18, 40, 1));
        foodMap.put("Milk", new FoodItem("Milk", 103, 8, 12, 2));
        foodMap.put("Mushrooms", new FoodItem("Mushrooms", 22, 3, 3, 0));
        foodMap.put("Oatmeal", new FoodItem("Oatmeal", 150, 5, 27, 3));
        foodMap.put("Onion", new FoodItem("Onion", 44, 1, 10, 0));
        foodMap.put("Orange", new FoodItem("Orange", 62, 1, 15, 0));
        foodMap.put("Pasta", new FoodItem("Pasta", 221, 8, 43, 1));
        foodMap.put("Peanut Butter", new FoodItem("Peanut Butter", 190, 8, 6, 16));
        foodMap.put("Pear", new FoodItem("Pear", 101, 1, 27, 0));
        foodMap.put("Potato", new FoodItem("Potato", 163, 4, 37, 0));
        foodMap.put("Pumpkin", new FoodItem("Pumpkin", 50, 1, 12, 0));
        foodMap.put("Quinoa", new FoodItem("Quinoa", 222, 8, 39, 3));
        foodMap.put("Rice", new FoodItem("Rice", 206, 4, 45, 0));
        foodMap.put("Salmon", new FoodItem("Salmon", 208, 22, 0, 13));
        foodMap.put("Soy Milk", new FoodItem("Soy Milk", 100, 7, 10, 4));
        foodMap.put("Spinach", new FoodItem("Spinach", 23, 3, 4, 0));
        foodMap.put("Strawberries", new FoodItem("Strawberries", 49, 1, 12, 0));
        foodMap.put("Sweet Potato", new FoodItem("Sweet Potato", 112, 2, 26, 0));
        foodMap.put("Tofu", new FoodItem("Tofu", 144, 15, 3, 9));
        foodMap.put("Tomato", new FoodItem("Tomato", 22, 1, 5, 0));
        foodMap.put("Tuna", new FoodItem("Tuna", 132, 28, 0, 1));
        foodMap.put("Watermelon", new FoodItem("Watermelon", 46, 1, 12, 0));
        foodMap.put("Yogurt", new FoodItem("Yogurt", 100, 6, 8, 4));
        foodMap.put("Zucchini", new FoodItem("Zucchini", 17, 1, 3, 0));
    }

    public static FoodItem getFood(String name) {
        return foodMap.get(name);
    }

    public static void printAvailableFoods() {
        System.out.println("Available foods: " + foodMap.keySet());
    }
}
