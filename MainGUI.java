// MainGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class MainGUI {
    private User user;
    private Map<String, NutritionLog> nutritionLogs = new HashMap<>();
    private Map<String, WorkoutLog> workoutLogs = new HashMap<>();
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Main Frame
        JFrame frame = new JFrame("Fitness Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        // Main Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // 1. Dashboard Tab
        JPanel dashboardPanel = createDashboardPanel();
        tabbedPane.addTab("Dashboard", dashboardPanel);
        
        // 2. Nutrition Logging Tab
        JPanel nutritionPanel = createNutritionPanel();
        tabbedPane.addTab("Nutrition", nutritionPanel);
        
        // 3. Workout Logging Tab
        JPanel workoutPanel = createWorkoutPanel();
        tabbedPane.addTab("Workout", workoutPanel);
        
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Summary Section
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        JButton refreshBtn = new JButton("Refresh Summary");
        refreshBtn.addActionListener(e -> updateSummary(summaryArea));
        
        panel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createNutritionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Food Input Section
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JTextField foodField = new JTextField();
        JButton addFoodBtn = new JButton("Add Food");
        
        inputPanel.add(new JLabel("Food Item:"));
        inputPanel.add(foodField);
        inputPanel.add(addFoodBtn);
        
        // Food List Display
        DefaultListModel<String> foodListModel = new DefaultListModel<>();
        JList<String> foodList = new JList<>(foodListModel);
        
        addFoodBtn.addActionListener((ActionEvent e) -> {
            FoodItem item = FoodDatabase.getFood(foodField.getText());
            if(item != null) {
                foodListModel.addElement(item.getNutrients());
                // Add to current nutrition log (implementation needed)
                foodField.setText("");
            }
        });
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(foodList), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createWorkoutPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Exercise Type Selection
        JComboBox<String> exerciseType = new JComboBox<>(
            new String[]{"Cardio", "Strength Training"}
        );
        
        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JTextField durationField = new JTextField();
        JButton logExerciseBtn = new JButton("Log Exercise");
        
        inputPanel.add(new JLabel("Duration (min):"));
        inputPanel.add(durationField);
        inputPanel.add(logExerciseBtn);
        
        // Exercise List Display
        DefaultListModel<String> exerciseListModel = new DefaultListModel<>();
        JList<String> exerciseList = new JList<>(exerciseListModel);
        
        logExerciseBtn.addActionListener(e -> {
            // Implement exercise logging logic
            exerciseListModel.addElement("Exercise: " + exerciseType.getSelectedItem() + 
                " - " + durationField.getText() + " mins");
            durationField.setText("");
        });
        
        panel.add(exerciseType, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(exerciseList), BorderLayout.SOUTH);
        
        return panel;
    }

    private void updateSummary(JTextArea summaryArea) {
        // Implement summary calculation
        StringBuilder summary = new StringBuilder();
        summary.append("Daily Summary:\n");
        summary.append("Calories Consumed: 0\n");  // Implement actual calculation
        summary.append("Calories Burned: 0\n");     // Implement actual calculation
        summaryArea.setText(summary.toString());
    }
}