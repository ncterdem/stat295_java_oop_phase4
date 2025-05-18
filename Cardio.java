import java.io.Serializable;

public class Cardio extends Exercise implements Serializable{
    private String type;
    private String intensity;
    private double userWeight;

    public Cardio(String name, int duration, String type, String intensity, double userWeight) {
        super(name, duration);
        this.type = type;
        this.intensity = intensity;
        this.userWeight = userWeight;
    }

    @Override
    public int calculateCaloriesBurned() {
        double met = switch (intensity.toLowerCase()) {
            case "low" -> 4.0;
            case "moderate" -> 6.0;
            case "high" -> 8.0;
            default -> 5.0;
        };
        return (int)(duration * met * userWeight * 0.0175);
    }
}
