import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class FitnessGUI extends JFrame {
    private User currentUser;
    private NutritionLog nutritionLog;
    private WorkoutLog workoutLog;
    private final String today = LocalDate.now().toString();

    public FitnessGUI() {
        setTitle("Fitness Tracker");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load existing user or create new
        currentUser = Main.loadUser();
        nutritionLog = new NutritionLog(today);
        workoutLog = new WorkoutLog(today);

        // Configure tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("User", new UserPanel());
        tabbedPane.addTab("Nutrition", new NutritionPanel());
        tabbedPane.addTab("Workout", new WorkoutPanel());
        tabbedPane.addTab("Summary", new SummaryPanel());

        add(tabbedPane);
    }

    // ================== INNER PANEL CLASSES ================== //

    private class UserPanel extends JPanel {
        private JTextField nameField;
        private JSpinner ageSpinner, heightSpinner, weightSpinner;
        private JComboBox<GoalType> goalCombo;

        public UserPanel() {
            setLayout(new GridLayout(6, 2, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Initialize components
            nameField = new JTextField();
            ageSpinner = new JSpinner(new SpinnerNumberModel(25, 1, 120, 1));
            heightSpinner = new JSpinner(new SpinnerNumberModel(1.7, 0.5, 2.5, 0.1));
            weightSpinner = new JSpinner(new SpinnerNumberModel(70.0, 30.0, 200.0, 0.5));
            goalCombo = new JComboBox<>(GoalType.values());

            // Load existing user data
            if (currentUser != null) {
                nameField.setText(currentUser.getName());
                ageSpinner.setValue(currentUser.getAge());
                heightSpinner.setValue(currentUser.getHeight());
                weightSpinner.setValue(currentUser.getWeight());
                goalCombo.setSelectedItem(currentUser.getGoalType());
            }

            // Add components
            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Age:"));
            add(ageSpinner);
            add(new JLabel("Height (m):"));
            add(heightSpinner);
            add(new JLabel("Weight (kg):"));
            add(weightSpinner);
            add(new JLabel("Goal:"));
            add(goalCombo);

            JButton saveBtn = new JButton("Save Profile");
            saveBtn.addActionListener(e -> saveUser());
            add(saveBtn);
        }

        private void saveUser() {
            currentUser = new User(
                nameField.getText(),
                (int) ageSpinner.getValue(),
                (double) heightSpinner.getValue(),
                (double) weightSpinner.getValue(),
                (GoalType) goalCombo.getSelectedItem()
            );
            Main.saveUser(currentUser);
            JOptionPane.showMessageDialog(this, "Profile saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class NutritionPanel extends JPanel {
        private JList<String> foodList;
        private JSpinner portionSpinner;
        private DefaultListModel<String> logModel;

        public NutritionPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Food selection
            DefaultListModel<String> foodModel = new DefaultListModel<>();
            FoodDatabase.foodMap.keySet().forEach(foodModel::addElement);
            foodList = new JList<>(foodModel);
            foodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Portion selection
            portionSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

            // Logged items
            logModel = new DefaultListModel<>();
            JList<String> logList = new JList<>(logModel);

            // Buttons
            JButton addBtn = new JButton("Add Food");
            addBtn.addActionListener(e -> addFood());

            // Layout
            JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
            inputPanel.add(new JLabel("Select Food:"), BorderLayout.NORTH);
            inputPanel.add(new JScrollPane(foodList), BorderLayout.CENTER);
            inputPanel.add(portionSpinner, BorderLayout.SOUTH);

            JPanel logPanel = new JPanel(new BorderLayout(10, 10));
            logPanel.add(new JLabel("Logged Items:"), BorderLayout.NORTH);
            logPanel.add(new JScrollPane(logList), BorderLayout.CENTER);

            add(inputPanel, BorderLayout.WEST);
            add(addBtn, BorderLayout.CENTER);
            add(logPanel, BorderLayout.EAST);
        }

        private void addFood() {
            String foodName = foodList.getSelectedValue();
            if (foodName == null) {
                JOptionPane.showMessageDialog(this, "Please select a food!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            FoodItem item = FoodDatabase.getFood(foodName);
            int portions = (int) portionSpinner.getValue();
            FoodItem adjusted = new FoodItem(
                item.getName(),
                item.getCalories() * portions,
                item.getProtein() * portions,
                item.getCarbs() * portions,
                item.getFat() * portions
            );
            nutritionLog.addFoodItem(adjusted);
            logModel.addElement(adjusted.getName() + " (" + portions + "x)");
        }
    }

    private class WorkoutPanel extends JPanel {
        private JTabbedPane exerciseTabs;

        public WorkoutPanel() {
            setLayout(new BorderLayout());
            exerciseTabs = new JTabbedPane();
            exerciseTabs.addTab("Cardio", new CardioPanel());
            exerciseTabs.addTab("Strength", new StrengthPanel());
            add(exerciseTabs, BorderLayout.CENTER);
        }
    }

    private class CardioPanel extends JPanel {
        // Similar structure to NutritionPanel, with inputs for cardio exercises
    }

    private class StrengthPanel extends JPanel {
        // Similar structure, with inputs for strength exercises
    }

    private class SummaryPanel extends JPanel {
        private JLabel caloriesInLabel, caloriesOutLabel, goalStatusLabel;

        public SummaryPanel() {
            setLayout(new GridLayout(4, 1, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            caloriesInLabel = new JLabel("Calories In: --");
            caloriesOutLabel = new JLabel("Calories Out: --");
            goalStatusLabel = new JLabel("Goal Status: --");

            JButton refreshBtn = new JButton("Refresh Summary");
            refreshBtn.addActionListener(e -> updateSummary());

            add(caloriesInLabel);
            add(caloriesOutLabel);
            add(goalStatusLabel);
            add(refreshBtn);
        }

        private void updateSummary() {
            int caloriesIn = nutritionLog.getTotalCalories();
            int caloriesOut = workoutLog.getTotalCaloriesBurned();
            
            caloriesInLabel.setText("Calories In: " + caloriesIn);
            caloriesOutLabel.setText("Calories Out: " + caloriesOut);
            
            if (currentUser != null) {
                DailySummary summary = new DailySummary();
                summary.summarizeDay(nutritionLog, workoutLog, currentUser.getDailyCalorieTarget());
                goalStatusLabel.setText("Goal Met: " + (summary.isGoalMet() ? "✅ Yes" : "❌ No"));
            }
        }
    }

    // ================== MAIN METHOD ================== //
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FitnessGUI().setVisible(true);
        });
    }
}