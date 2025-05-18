import java.util.HashMap;
import java.util.Map;

public class FoodDatabase {
    private static final Map<String, FoodItem> foodMap = new HashMap<>();

    static {
        foodMap.put("apple", new FoodItem("Apple", 95, 0, 25, 0));
        foodMap.put("banana", new FoodItem("Banana", 105, 1, 27, 0));
        foodMap.put("chicken breast", new FoodItem("Chicken Breast", 200, 30, 0, 5));
        foodMap.put("rice", new FoodItem("Rice", 206, 4, 45, 0));
        foodMap.put("egg", new FoodItem("Egg", 78, 6, 1, 5));
        foodMap.put("salmon", new FoodItem("Salmon", 208, 22, 0, 13));
        foodMap.put("broccoli", new FoodItem("Broccoli", 55, 4, 11, 0));
        foodMap.put("oatmeal", new FoodItem("Oatmeal", 150, 5, 27, 3));
        foodMap.put("peanut butter", new FoodItem("Peanut Butter", 190, 8, 6, 16));
        foodMap.put("almonds", new FoodItem("Almonds", 164, 6, 6, 14));
        foodMap.put("avocado", new FoodItem("Avocado", 234, 3, 12, 21));
        foodMap.put("spinach", new FoodItem("Spinach", 23, 3, 4, 0));
        foodMap.put("carrot", new FoodItem("Carrot", 25, 1, 6, 0));
        foodMap.put("potato", new FoodItem("Potato", 163, 4, 37, 0));
        foodMap.put("sweet potato", new FoodItem("Sweet Potato", 112, 2, 26, 0));
        foodMap.put("yogurt", new FoodItem("Yogurt", 100, 6, 8, 4));
        foodMap.put("greek yogurt", new FoodItem("Greek Yogurt", 120, 12, 8, 5));
        foodMap.put("beef", new FoodItem("Beef", 250, 26, 0, 18));
        foodMap.put("pasta", new FoodItem("Pasta", 221, 8, 43, 1));
        foodMap.put("cheese", new FoodItem("Cheese", 113, 7, 1, 9));
        foodMap.put("milk", new FoodItem("Milk", 103, 8, 12, 2));
        foodMap.put("soy milk", new FoodItem("Soy Milk", 100, 7, 10, 4));
        foodMap.put("orange", new FoodItem("Orange", 62, 1, 15, 0));
        foodMap.put("grapes", new FoodItem("Grapes", 62, 1, 16, 0));
        foodMap.put("strawberries", new FoodItem("Strawberries", 49, 1, 12, 0));
        foodMap.put("blueberries", new FoodItem("Blueberries", 57, 1, 14, 0));
        foodMap.put("watermelon", new FoodItem("Watermelon", 46, 1, 12, 0));
        foodMap.put("tuna", new FoodItem("Tuna", 132, 28, 0, 1));
        foodMap.put("tofu", new FoodItem("Tofu", 144, 15, 3, 9));
        foodMap.put("lentils", new FoodItem("Lentils", 230, 18, 40, 1));
        foodMap.put("black beans", new FoodItem("Black Beans", 227, 15, 41, 1));
        foodMap.put("chickpeas", new FoodItem("Chickpeas", 269, 15, 45, 4));
        foodMap.put("brown rice", new FoodItem("Brown Rice", 216, 5, 45, 2));
        foodMap.put("quinoa", new FoodItem("Quinoa", 222, 8, 39, 3));
        foodMap.put("corn", new FoodItem("Corn", 96, 3, 21, 1));
        foodMap.put("cucumber", new FoodItem("Cucumber", 16, 1, 4, 0));
        foodMap.put("onion", new FoodItem("Onion", 44, 1, 10, 0));
        foodMap.put("tomato", new FoodItem("Tomato", 22, 1, 5, 0));
        foodMap.put("mushrooms", new FoodItem("Mushrooms", 22, 3, 3, 0));
        foodMap.put("zucchini", new FoodItem("Zucchini", 17, 1, 3, 0));
        foodMap.put("eggplant", new FoodItem("Eggplant", 25, 1, 6, 0));
        foodMap.put("green beans", new FoodItem("Green Beans", 31, 2, 7, 0));
        foodMap.put("asparagus", new FoodItem("Asparagus", 20, 2, 4, 0));
        foodMap.put("cabbage", new FoodItem("Cabbage", 22, 1, 5, 0));
        foodMap.put("cauliflower", new FoodItem("Cauliflower", 25, 2, 5, 0));
        foodMap.put("bell pepper", new FoodItem("Bell Pepper", 31, 1, 7, 0));
        foodMap.put("pumpkin", new FoodItem("Pumpkin", 50, 1, 12, 0));
        foodMap.put("pear", new FoodItem("Pear", 101, 1, 27, 0));
        foodMap.put("kiwi", new FoodItem("Kiwi", 42, 1, 10, 0));
    }

    public static FoodItem getFood(String name) {
        return foodMap.get(name.toLowerCase());
    }

    public static void printAvailableFoods() {
        System.out.println("Available foods: " + foodMap.keySet());
    }
}
