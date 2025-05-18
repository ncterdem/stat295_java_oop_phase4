import java.util.List;

public class ProgressReport {
    private String timePeriod;
    private List<DailySummary> dailySummaries;
    private double averageCaloriesIn;
    private double averageCaloriesOut;

    public ProgressReport(String timePeriod, List<DailySummary> dailySummaries) {
        this.timePeriod = timePeriod;
        this.dailySummaries = dailySummaries;
    }

    public void generateReport() {
        int totalIn = 0, totalOut = 0;
        for (DailySummary ds : dailySummaries) {
            totalIn += ds.getTotalCaloriesIn();
            totalOut += ds.getTotalCaloriesOut();
        }
        int count = dailySummaries.size();
        this.averageCaloriesIn = count > 0 ? (double) totalIn / count : 0;
        this.averageCaloriesOut = count > 0 ? (double) totalOut / count : 0;
    }

    public void printSummary() {
        System.out.println("\n📊 Progress Report: " + timePeriod);
        System.out.println("Average Calories In:  " + averageCaloriesIn);
        System.out.println("Average Calories Out: " + averageCaloriesOut);
        System.out.println("Days Tracked: " + dailySummaries.size());
    }
}
