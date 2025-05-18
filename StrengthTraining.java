public class StrengthTraining extends Exercise {
    private int sets, reps;
    private double weight;
    private double lastUsedWeight;

    public StrengthTraining(String name, int duration, int sets, int reps, double weight) {
        super(name, duration);
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.lastUsedWeight = weight;
    }

    @Override
    public int calculateCaloriesBurned() {
        return (int)(sets * reps * weight * 0.1);
    }

    public void updateLastWeight(double weight) {
        this.lastUsedWeight = weight;
    }

    public double getLastUsedWeight() {
        return lastUsedWeight;
    }

    // ✅ Getters for encapsulated fields
    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
