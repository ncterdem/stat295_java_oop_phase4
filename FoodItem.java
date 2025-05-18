import java.util.ArrayList;
import java.util.List;

public class FoodItem {
    private List<FoodItem> foodItems = new ArrayList<>();
    private String name;
    private int calories, protein, carbs, fat;

    public FoodItem(String name, int calories, int protein, int carbs, int fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public int getCalories() { return calories; }
    public String getNutrients() {
        return "Protein: " + protein + "g, Carbs: " + carbs + "g, Fat: " + fat + "g";
    }
    public String getName() {
        return name;
    }



    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFat() {
        return fat;
    }
    public List<FoodItem> getFoodItems() {
        return foodItems;
    }
}
