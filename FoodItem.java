import java.io.Serializable;

public class FoodItem implements Serializable{
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
}
