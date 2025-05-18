import java.io.Serializable;

public abstract class Exercise implements Serializable{
    protected String name;
    protected int duration; // in minutes

    public Exercise(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public abstract int calculateCaloriesBurned();
}
