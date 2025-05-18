import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // === USER SETUP ===
        GoalType goal = GoalType.LOSE_WEIGHT; // change to GAIN_MUSCLE or MAINTAIN if needed
        User user = new User("Ali", 22, 1.75, 68, goal);

        // === DATA STORAGE ===
        Map<String, NutritionLog> nutritionLogsByDate = new HashMap<>();
        Map<String, WorkoutLog> workoutLogsByDate = new HashMap<>();
        List<DailySummary> summaryList = new ArrayList<>();

        // === MAIN LOGGING LOOP ===
        while (true) {
            System.out.print("\nEnter date to log (YYYY-MM-DD) or 'done' to finish: ");
            String date = scanner.nextLine().trim();
            if (date.equalsIgnoreCase("done")) break;

            // Check for existing entries
            if (nutritionLogsByDate.containsKey(date)) {
                System.out.print("Logs for " + date + " exist. Overwrite? (yes/no): ");
                String choice = scanner.nextLine().trim();
                if (!choice.equalsIgnoreCase("yes")) continue;
            }

            // === NUTRITION LOG ===
            NutritionLog nutritionLog = new NutritionLog(date);
            System.out.println("\nLog your food for " + date + " (type 'done' to finish):");
            FoodDatabase.printAvailableFoods();
            while (true) {
                System.out.print("Food name: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("done")) break;
                FoodItem item = FoodDatabase.getFood(input);
                if (item != null) {
                    nutritionLog.addFoodItem(item);
                    System.out.println("✅ Added: " + item.getNutrients());
                } else {
                    System.out.println("❌ Food not found.");
                }
            }

            // === WORKOUT LOG ===
            WorkoutLog workoutLog = new WorkoutLog(date);
            
            // Cardio logging
            System.out.println("\nLog your cardio for " + date + " (type 'done' to finish):");
            ExerciseDatabase.printAvailableExercises();
            while (true) {
                System.out.print("Cardio type: ");
                String type = scanner.nextLine();
                if (type.equalsIgnoreCase("done")) break;
                System.out.print("Duration (min): ");
                int duration = Integer.parseInt(scanner.nextLine());
                String intensity = ExerciseDatabase.getIntensity(type);
                Cardio cardio = new Cardio(type, duration, type, intensity, user.getWeight());
                workoutLog.addExercise(cardio);
            }

            // Strength training logging
            System.out.println("\nLog your strength training for " + date + " (type 'done' to finish):");
            StrengthExerciseTemplates.printAvailableExercises();
            while (true) {
                System.out.print("Strength exercise: ");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("done")) break;
                if (!StrengthExerciseTemplates.isAvailable(name)) {
                    System.out.println("❌ Not found. Try again.");
                    continue;
                }
                System.out.print("Duration (min): ");
                int duration = Integer.parseInt(scanner.nextLine());
                System.out.print("Sets: ");
                int sets = Integer.parseInt(scanner.nextLine());
                System.out.print("Reps per set: ");
                int reps = Integer.parseInt(scanner.nextLine());
                System.out.print("Weight used (kg): ");
                double weight = Double.parseDouble(scanner.nextLine());

                StrengthTraining st = new StrengthTraining(name, duration, sets, reps, weight);
                workoutLog.addExercise(st);
            }

            // === SAVE DAILY DATA ===
            DailySummary summary = new DailySummary();
            summary.summarizeDay(nutritionLog, workoutLog, user.getDailyCalorieTarget());
            
            nutritionLogsByDate.put(date, nutritionLog);
            workoutLogsByDate.put(date, workoutLog);
            summaryList.add(summary);
            
            System.out.println("\n✅ Successfully saved data for " + date);
        }

        // === VIEW LOGS BY DATE ===
        System.out.print("\nWould you like to view logs for a specific date? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter date (e.g., 2025-05-11): ");
            String date = scanner.nextLine();
            NutritionLog nlog = nutritionLogsByDate.get(date);
            WorkoutLog wlog = workoutLogsByDate.get(date);

            if (nlog != null && wlog != null) {
                int calIn = CalorieCalculator.calculateCaloriesConsumed(nlog);
                int calOut = CalorieCalculator.calculateCaloriesBurned(wlog);
                DailySummary daily = new DailySummary();
                daily.summarizeDay(nlog, wlog, user.getDailyCalorieTarget());

                System.out.println("\n📅 Date: " + date);
                System.out.println("👤 Weight: " + user.getWeight() + " kg");
                System.out.println("🍽️ Calories In:  " + calIn);
                System.out.println("🔥 Calories Out: " + calOut);
                System.out.println("🎯 Goal Met: " + (daily.isGoalMet() ? "✅ Yes" : "❌ No"));

                System.out.println("\n🏋️ Exercises performed:");
                for (Exercise ex : wlog.getExercises()) {
                    System.out.println("- " + ex.getClass().getSimpleName() + ": " + ex.name + " (" + ex.duration + " min)");
                    if (ex instanceof StrengthTraining st) {
                        System.out.println("   Sets: " + st.getSets() + ", Reps: " + st.getReps() + ", Weight: " + st.getWeight() + " kg");
                    }
                }
            } else {
                System.out.println("⚠️ No data found for that date.");
            }
        }

        // === WEEKLY REPORT + GOAL TRACKING ===
        System.out.print("\nWould you like to see a weekly progress report? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            ProgressReport report = new ProgressReport("Week 1", summaryList);
            report.generateReport();
            report.printSummary();

            GoalTracker tracker = new GoalTracker(user, 
                new ArrayList<>(nutritionLogsByDate.values()), 
                new ArrayList<>(workoutLogsByDate.values()));
            tracker.trackProgress();
        }

        scanner.close();
    }
}