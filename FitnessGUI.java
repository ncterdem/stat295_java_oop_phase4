import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class FitnessGUI extends JFrame {
    private User currentUser;
    private NutritionLog nutritionLog;
    private WorkoutLog workoutLog;
    private final String today = LocalDate.now().toString();
    private HistoryPanel historyPanel;

    public FitnessGUI() {
        setTitle("Fitness Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize core components
        currentUser = Main.loadUser();
        nutritionLog = new NutritionLog(today);
        workoutLog = new WorkoutLog(today);

        // Configure tabs
        JTabbedPane tabbedPane = new JTabbedPane() {
        @Override
        public Insets getInsets() {
            // Adjust these values to move tabs up/down
            return new Insets(10, 10, 10, 10); // TOP, LEFT, BOTTOM, RIGHT
        }
        };
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Top, Left, Bottom, Right
        tabbedPane.addTab("User", new UserPanel());
        tabbedPane.addTab("Nutrition", new NutritionPanel());
        tabbedPane.addTab("Workout", new WorkoutPanel());
        tabbedPane.addTab("Summary", new SummaryPanel());
        
        historyPanel = new HistoryPanel();
        tabbedPane.addTab("History", historyPanel);

        add(tabbedPane);
    }

    // ================================= INNER PANELS ================================= //

    private class UserPanel extends JPanel {
        private JTextField nameField;
        private JSpinner ageSpinner, heightSpinner, weightSpinner;
        private JComboBox<GoalType> goalCombo;

        public UserPanel() {
            setLayout(new GridLayout(6, 2, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            nameField = new JTextField();
            ageSpinner = new JSpinner(new SpinnerNumberModel(25, 1, 120, 1));
            heightSpinner = new JSpinner(new SpinnerNumberModel(1.7, 0.5, 2.5, 0.1));
            weightSpinner = new JSpinner(new SpinnerNumberModel(70.0, 30.0, 200.0, 0.5));
            goalCombo = new JComboBox<>(GoalType.values());

            if (currentUser != null) loadUserData();

            addComponents();
        }

        private void addComponents() {
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

        private void loadUserData() {
            nameField.setText(currentUser.getName());
            ageSpinner.setValue(currentUser.getAge());
            heightSpinner.setValue(currentUser.getHeight());
            weightSpinner.setValue(currentUser.getWeight());
            goalCombo.setSelectedItem(currentUser.getGoalType());
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

            initializeComponents();
            setupLayout();
        }

        private void initializeComponents() {
            DefaultListModel<String> foodModel = new DefaultListModel<>();
            FoodDatabase.foodMap.keySet().forEach(foodModel::addElement);
            foodList = new JList<>(foodModel);
            portionSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
            logModel = new DefaultListModel<>();
        }

        private void setupLayout() {
            JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
            inputPanel.add(new JLabel("Select Food:"), BorderLayout.NORTH);
            inputPanel.add(new JScrollPane(foodList), BorderLayout.CENTER);
            inputPanel.add(portionSpinner, BorderLayout.SOUTH);

            JPanel logPanel = new JPanel(new BorderLayout(10, 10));
            logPanel.add(new JLabel("Logged Items:"), BorderLayout.NORTH);
            logPanel.add(new JScrollPane(new JList<>(logModel)), BorderLayout.CENTER);

            JButton addBtn = new JButton("Add Food");
            addBtn.addActionListener(e -> addFood());

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
        private JComboBox<String> exerciseCombo;
        private JSpinner durationSpinner;
        private JLabel intensityLabel;
        private DefaultListModel<String> logModel;

        public CardioPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            initializeComponents();
            setupLayout();
        }

        private void initializeComponents() {
            exerciseCombo = new JComboBox<>(ExerciseDatabase.metValues.keySet().toArray(new String[0]));
            durationSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 240, 1));
            intensityLabel = new JLabel("Intensity: ");
            logModel = new DefaultListModel<>();
        }

        private void setupLayout() {
            exerciseCombo.addActionListener(e -> updateIntensity());
            JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            inputPanel.add(new JLabel("Exercise Type:"));
            inputPanel.add(exerciseCombo);
            inputPanel.add(new JLabel("Duration (minutes):"));
            inputPanel.add(durationSpinner);
            inputPanel.add(new JLabel("Intensity Level:"));
            inputPanel.add(intensityLabel);

            JButton addBtn = new JButton("Add Cardio Session");
            addBtn.addActionListener(e -> addCardio());

            add(inputPanel, BorderLayout.NORTH);
            add(new JScrollPane(new JList<>(logModel)), BorderLayout.CENTER);
            add(addBtn, BorderLayout.SOUTH);

            updateIntensity();
        }

        private void updateIntensity() {
            String exercise = (String) exerciseCombo.getSelectedItem();
            intensityLabel.setText("Intensity: " + ExerciseDatabase.getIntensity(exercise).toUpperCase());
        }

        private void addCardio() {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Complete user profile first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String type = (String) exerciseCombo.getSelectedItem();
            Cardio cardio = new Cardio(
                type,
                (int) durationSpinner.getValue(),
                type,
                ExerciseDatabase.getIntensity(type),
                currentUser.getWeight()
            );
            workoutLog.addExercise(cardio);
            logModel.addElement(String.format("%s - %d min (%d kcal)", 
                type, cardio.duration, cardio.calculateCaloriesBurned()));
        }
    }

    private class StrengthPanel extends JPanel {
        private JComboBox<String> exerciseCombo;
        private JSpinner durationSpinner, setsSpinner, repsSpinner, weightSpinner;
        private DefaultListModel<String> logModel;

        public StrengthPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            initializeComponents();
            setupLayout();
        }

        private void initializeComponents() {
            exerciseCombo = new JComboBox<>(
                StrengthExerciseTemplates.availableExercises.toArray(new String[0]));
            durationSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 240, 1));
            setsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
            repsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
            weightSpinner = new JSpinner(new SpinnerNumberModel(20.0, 0.0, 500.0, 2.5));
            logModel = new DefaultListModel<>();
        }

        private void setupLayout() {
            JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            inputPanel.add(new JLabel("Exercise:"));
            inputPanel.add(exerciseCombo);
            inputPanel.add(new JLabel("Duration (min):"));
            inputPanel.add(durationSpinner);
            inputPanel.add(new JLabel("Sets:"));
            inputPanel.add(setsSpinner);
            inputPanel.add(new JLabel("Reps per Set:"));
            inputPanel.add(repsSpinner);
            inputPanel.add(new JLabel("Weight (kg):"));
            inputPanel.add(weightSpinner);

            JButton addBtn = new JButton("Add Strength Session");
            addBtn.addActionListener(e -> addStrength());

            add(inputPanel, BorderLayout.NORTH);
            add(new JScrollPane(new JList<>(logModel)), BorderLayout.CENTER);
            add(addBtn, BorderLayout.SOUTH);
        }

        private void addStrength() {
            StrengthTraining session = new StrengthTraining(
                (String) exerciseCombo.getSelectedItem(),
                (int) durationSpinner.getValue(),
                (int) setsSpinner.getValue(),
                (int) repsSpinner.getValue(),
                (double) weightSpinner.getValue()
            );
            workoutLog.addExercise(session);
            logModel.addElement(String.format("%s - %d×%d @ %.1fkg (%d kcal)", 
                session.name, session.sets, session.reps, session.weight, 
                session.calculateCaloriesBurned()));
        }
    }

    private class SummaryPanel extends JPanel {
        private JLabel caloriesInLabel, caloriesOutLabel, goalStatusLabel;

        public SummaryPanel() {
            setLayout(new GridLayout(5, 1, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            initializeComponents();
            setupLayout();
        }

        private void initializeComponents() {
            caloriesInLabel = new JLabel("Calories In: --");
            caloriesOutLabel = new JLabel("Calories Out: --");
            goalStatusLabel = new JLabel("Goal Status: --");
        }

        private void setupLayout() {
            JButton refreshBtn = new JButton("Refresh Summary");
            refreshBtn.addActionListener(e -> updateSummary());

            JButton saveBtn = new JButton("Save Daily Log");
            saveBtn.addActionListener(e -> saveLog());

            add(caloriesInLabel);
            add(caloriesOutLabel);
            add(goalStatusLabel);
            add(refreshBtn);
            add(saveBtn);
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

        private void saveLog() {
            try {
                Main.saveDailyLog(today, nutritionLog, workoutLog);
                JOptionPane.showMessageDialog(this, "Log saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                historyPanel.populateDateList();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage(), 
                    "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class HistoryPanel extends JPanel {
        private JComboBox<String> dateCombo;
        private JSpinner yearSpinner, monthSpinner, daySpinner;
        private JTextArea logArea;
        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public HistoryPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            initializeComponents();
            setupLayout();
            configureSpinners();
        }

        private void initializeComponents() {
            dateCombo = new JComboBox<>();
            yearSpinner = new JSpinner(new SpinnerNumberModel(
                LocalDate.now().getYear(), 2000, LocalDate.now().getYear() + 1, 1));
            monthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
            daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            logArea = new JTextArea(15, 60);
        }

        private void setupLayout() {
            JPanel datePanel = new JPanel(new GridLayout(2, 1, 10, 10));
            
            // Date Combo Panel
            JPanel comboPanel = new JPanel(new BorderLayout(10, 10));
            comboPanel.add(new JLabel("Available Logs:"), BorderLayout.NORTH);
            comboPanel.add(new JScrollPane(dateCombo), BorderLayout.CENTER);
            
            JButton refreshBtn = new JButton("Refresh Log List");
            refreshBtn.addActionListener(e -> populateDateList());
            comboPanel.add(refreshBtn, BorderLayout.SOUTH);

            // Spinner Panel
            JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            spinnerPanel.add(new JLabel("Year:"));
            spinnerPanel.add(yearSpinner);
            spinnerPanel.add(new JLabel("Month:"));
            spinnerPanel.add(monthSpinner);
            spinnerPanel.add(new JLabel("Day:"));
            spinnerPanel.add(daySpinner);
            
            JButton loadBtn = new JButton("Load Selected Date");
            loadBtn.addActionListener(e -> loadSelectedDate());
            spinnerPanel.add(loadBtn);

            datePanel.add(comboPanel);
            datePanel.add(spinnerPanel);

            // Log Area
            logArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(logArea);

            add(datePanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            dateCombo.addActionListener(e -> {
                String selected = (String) dateCombo.getSelectedItem();
                if (selected != null) updateSpinners(selected);
            });

            populateDateList();
        }

        private void configureSpinners() {
            ChangeListener spinnerListener = new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    updateDaySpinner((int) monthSpinner.getValue(), (int) yearSpinner.getValue());
                }
            };
            monthSpinner.addChangeListener(spinnerListener);
            yearSpinner.addChangeListener(spinnerListener);
        }

        private void updateDaySpinner(int month, int year) {
            LocalDate date = LocalDate.of(year, month, 1);
            int maxDays = date.lengthOfMonth();
            daySpinner.setModel(new SpinnerNumberModel(
                Math.min((int) daySpinner.getValue(), maxDays), 1, maxDays, 1));
        }

        public void populateDateList() {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            File logDir = new File(".");
            File[] logFiles = logDir.listFiles((dir, name) -> name.startsWith("log_") && name.endsWith(".txt"));

            if (logFiles != null) {
                List<LocalDate> dates = new ArrayList<>();
                for (File file : logFiles) {
                    String dateStr = file.getName().substring(4, 14);
                    dates.add(LocalDate.parse(dateStr, dateFormatter));
                }
                Collections.sort(dates, Collections.reverseOrder());
                dates.forEach(date -> model.addElement(date.format(dateFormatter)));
            }

            dateCombo.setModel(model);
        }

        private void updateSpinners(String dateStr) {
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            yearSpinner.setValue(date.getYear());
            monthSpinner.setValue(date.getMonthValue());
            daySpinner.setValue(date.getDayOfMonth());
            loadLog(dateStr);
        }

        private void loadSelectedDate() {
            String dateStr = String.format("%04d-%02d-%02d",
                (int) yearSpinner.getValue(),
                (int) monthSpinner.getValue(),
                (int) daySpinner.getValue()
            );
            loadLog(dateStr);
        }

        private void loadLog(String dateStr) {
            try (BufferedReader reader = new BufferedReader(new FileReader("log_" + dateStr + ".txt"))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                logArea.setText(content.toString());
            } catch (Exception e) {
                logArea.setText("Error loading log for " + dateStr + ":\n" + e.getMessage());
            }
        }
    }

    // ================================= MAIN METHOD ================================= //
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            FitnessGUI gui = new FitnessGUI();
            gui.setVisible(true);
        });
    }
}