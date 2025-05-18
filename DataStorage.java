// DataStorage.java
import java.io.*;
import java.util.*;

public class DataStorage {
    private static final String FILE_NAME = "fitness_data.ser";

    public static void saveData(Map<String, NutritionLog> nutritionLogs, 
                                Map<String, WorkoutLog> workoutLogs, 
                                List<DailySummary> summaries) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(nutritionLogs);
            oos.writeObject(workoutLogs);
            oos.writeObject(summaries);
        } catch (IOException e) {
            System.out.println("⚠️ Error saving data: " + e.getMessage());
        }
    }

    public static Object[] loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Map<String, NutritionLog> nutritionLogs = (Map<String, NutritionLog>) ois.readObject();
            Map<String, WorkoutLog> workoutLogs = (Map<String, WorkoutLog>) ois.readObject();
            List<DailySummary> summaries = (List<DailySummary>) ois.readObject();
            return new Object[]{nutritionLogs, workoutLogs, summaries};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Error loading data: " + e.getMessage());
            return null;
        }
    }
}