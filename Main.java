import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String today = LocalDate.now().toString();

        // === USER SETUP ===
        User user = loadUser();

        if (user == null) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter your height (m): ");
            double height = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter your current weight (kg): ");
            double weight = Double.parseDouble(scanner.nextLine());

            System.out.println("Choose your goal: 1) Lose Weight 2) Gain Muscle 3) Maintain");
            int g = Integer.parseInt(scanner.nextLine());
            GoalType goal = switch (g) {
                case 1 -> GoalType.LOSE_WEIGHT;
                case 2 -> GoalType.GAIN_MUSCLE;
                default -> GoalType.MAINTAIN;
            };

            user = new User(name, age, height, weight, goal);
            saveUser(user);
        }

        // === NUTRITION LOG ===
        NutritionLog nutritionLog = new NutritionLog(today);
        System.out.println("Log your food (type 'done' to finish):");
        FoodDatabase.printAvailableFoods();

        while (true) {
            System.out.print("Food name: ");
            String nameInput = scanner.nextLine();
            if (nameInput.equalsIgnoreCase("done")) break;

            FoodItem baseItem = FoodDatabase.getFood(nameInput);
            if (baseItem != null) {
                System.out.print("How many portions did you eat? ");
                int portions = Integer.parseInt(scanner.nextLine());

                FoodItem adjustedItem = new FoodItem(
                        baseItem.getName(),
                        baseItem.getCalories() * portions,
                        baseItem.getProtein() * portions,
                        baseItem.getCarbs() * portions,
                        baseItem.getFat() * portions
                );

                nutritionLog.addFoodItem(adjustedItem);
                System.out.println("✅ Logged: " + portions + "x " + baseItem.getName());
            } else {
                System.out.println("❌ Food not found.");
            }
        }

        // === WORKOUT LOG ===
        WorkoutLog workoutLog = new WorkoutLog(today);

        System.out.println("\nLog your cardio (type 'done' to finish):");
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

        System.out.println("\nLog your strength training (type 'done' to finish):");
        StrengthExerciseTemplates.printAvailableExercises();
        while (true) {
            System.out.print("Strength exercise: ");
            String exercise = scanner.nextLine();
            if (exercise.equalsIgnoreCase("done")) break;

            if (!StrengthExerciseTemplates.isAvailable(exercise)) {
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
            double usedWeight = Double.parseDouble(scanner.nextLine());

            StrengthTraining st = new StrengthTraining(exercise, duration, sets, reps, usedWeight);
            workoutLog.addExercise(st);
        }

        // === DAILY SUMMARY ===
        int caloriesIn = CalorieCalculator.calculateCaloriesConsumed(nutritionLog);
        int caloriesOut = CalorieCalculator.calculateCaloriesBurned(workoutLog);

        DailySummary summary = new DailySummary();
        summary.summarizeDay(nutritionLog, workoutLog, user.getDailyCalorieTarget());

        System.out.println("\n📊 Summary for " + today);
        System.out.println("Calories In:  " + caloriesIn);
        System.out.println("Calories Out: " + caloriesOut);
        System.out.println("Goal Met: " + (summary.isGoalMet() ? "✅ Yes" : "❌ No"));

        // === SAVE DAILY LOG ===
        saveDailyLog(today, nutritionLog, workoutLog);

        // === VIEW PAST LOG ===
        System.out.print("\nWould you like to view a previous log? (yes/no): ");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.print("Enter the date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            loadDailyLog(date);
        }

        scanner.close();
    }

    public static void saveDailyLog(String date, NutritionLog nlog, WorkoutLog wlog) {
        try (PrintWriter out = new PrintWriter("log_" + date + ".txt")) {
            out.println("=== NUTRITION ===");
            for (FoodItem f : nlog.getFoodItems()) {
                out.println("- " + f.getName() + " | Calories: " + f.getCalories());
            }

            out.println("\n=== WORKOUT ===");
            for (Exercise ex : wlog.getExercises()) {
                out.println("- " + ex.getClass().getSimpleName() + ": " + ex.name + " (" + ex.duration + " min)");
            }
        } catch (IOException e) {
            System.out.println("❌ Failed to save daily log.");
        }
    }

    public static void loadDailyLog(String date) {
        File file = new File("log_" + date + ".txt");

        if (!file.exists()) {
            System.out.println("⚠️ No log found for " + date);
            return;
        }

        System.out.println("\n📖 Log for " + date + ":");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("❌ Failed to read log for " + date);
        }
    }
    public static void saveUser(User user) {
        try (PrintWriter writer = new PrintWriter("user.txt")) {
            writer.println(user.getName());
            writer.println(user.getAge());
            writer.println(user.getHeight());
            writer.println(user.getWeight());
            writer.println(user.getGoalType()); // enum name: LOSE_WEIGHT, etc.
        } catch (IOException e) {
            System.out.println("❌ Failed to save user data.");
        }
    }

    public static User loadUser() {
        File file = new File("user.txt");
        if (!file.exists()) return null;

        try (Scanner fileScanner = new Scanner(file)) {
            String name = fileScanner.nextLine();
            int age = Integer.parseInt(fileScanner.nextLine());
            double height = Double.parseDouble(fileScanner.nextLine());
            double weight = Double.parseDouble(fileScanner.nextLine());
            GoalType goal = GoalType.valueOf(fileScanner.nextLine());

            return new User(name, age, height, weight, goal);
        } catch (Exception e) {
            System.out.println("⚠️ Failed to load user data. Re-entering info...");
            return null;
        }
    }
}
