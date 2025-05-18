public abstract class Exercise {
    protected String name;
    protected int duration; // in minutes

    public Exercise(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public abstract int calculateCaloriesBurned();
}
