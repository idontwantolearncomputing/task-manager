package hk.edu.polyu.comp.comp2021.tms.view;

import hk.edu.polyu.comp.comp2021.tms.controller.TMS;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The TMS_GUI class represents the GUI for the Task Management System.
 * {@code @auther} CHEN Chi-wei and WU Qi Xuan
 */
public class TMS_GUI extends JFrame {
    /**
     * The percentage of the screen width and height to be used for the GUI.
     */
    public static final double SCREEN = 0.9;
    /**
     * The width of the input panel.
     */
    public static final int PANELHEIGHT = 50;
    private final TMS tms;
    private final JComboBox<String> commandBox;
    private final JPanel inputPanel;
    private final List<JTextField> inputFields;
    private final JTextArea tasksOutputArea;
    private final JTextArea commandOutputArea;

    /**
     * Constructs a TMS_GUI object.
     * {@code @auther} CHEN Chi-wei
     */
    public TMS_GUI() {
        tms = new TMS();
        tms.addIsPrimitiveCriterion();

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        double panelWidth = width * SCREEN; // 50% of screen width
        double panelHeight = height * SCREEN; // 50% of screen height

        setTitle("Task Management System");
        setSize((int) panelWidth, (int) panelHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Command Buttons Panel
        JPanel commandsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Command Selection
        String[] commands = {"createSimpleTask", "createCompositeTask", "deleteTask", "changeTask","print",
                "creatBasicCriterion", "creatNegatedCriterion", "creatBinaryCriterion", "printAllCriterion", "searchTasksBaseOnCriterion", "store", "load"};
        commandBox = new JComboBox<>(commands);
        commandBox.addActionListener(e -> commandChanged());
        add(commandBox, BorderLayout.NORTH);

        // Create a button for each command
        for (String command : commands) {
            JButton button = new JButton(command);
            button.addActionListener(e -> {
                commandBox.setSelectedItem(command);
                commandChanged(); // Update input panel for the selected command
            });
            commandsPanel.add(button);
        }

        add(commandsPanel, BorderLayout.NORTH);


        JScrollPane commandsScrollPane = new JScrollPane(commandsPanel);
        commandsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        commandsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        commandsScrollPane.setPreferredSize(new Dimension((int) panelWidth, PANELHEIGHT)); // You may adjust the height here as needed


        add(commandsScrollPane, BorderLayout.NORTH);

        // Input Panel
        inputPanel = new JPanel();
        inputFields = new ArrayList<>();
        updateInputPanel(commands[0]);
        add(inputPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setText("I don't believe you! By Yixin Cao");
        bottomPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(e -> executeCommand());
        bottomPanel.add(executeButton, BorderLayout.EAST);


        add(bottomPanel, BorderLayout.SOUTH);


        // Initialize commandOutputArea as a field
        commandOutputArea = new JTextArea(5, 20); // Correct this line
        commandOutputArea.setEditable(false);
        JScrollPane commandOutputScrollPane = new JScrollPane(commandOutputArea);
        commandOutputScrollPane.setPreferredSize(new Dimension((int) panelWidth/3, (int) panelHeight/3)); // Set preferred size if needed

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        tasksOutputArea = new JTextArea(10, 20);
        tasksOutputArea.setEditable(false);
        rightPanel.add(new JScrollPane(tasksOutputArea));
        rightPanel.add(commandOutputScrollPane);

        // Add Right Panel to the Main Frame
        add(rightPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void commandChanged() {
        String selectedCommand = (String) commandBox.getSelectedItem();
        assert selectedCommand != null;
        updateInputPanel(selectedCommand);
    }

    private void updateInputPanel(String command) {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridLayout(0, 2));
        inputFields.clear();

        switch (command) {
            case "createSimpleTask":
                addInputField("Name");
                addInputField("Description");
                addInputField("Duration");
                addInputField("Prerequisites (comma separated)");
                break;
            case "createCompositeTask":
                addInputField("Name");
                addInputField("Description");
                addInputField("Subtasks (comma separated)");
                break;
            case "deleteTask":
                addInputField("Task Name");
                break;
            case "changeTask":
                addInputField("Task Name");
                addInputField("Property");
                addInputField("Value");
                break;
            case "print":
                addInputField("Task Name (leave empty to print all tasks)");
                break;
            case "creatBasicCriterion":
                addInputField("Name");
                addInputField("Property");
                addInputField("Operation");
                addInputField("Value");
                break;
            case "creatNegatedCriterion":
                addInputField("New Criterion name");
                addInputField("Old Criterion name");
                break;
            case "creatBinaryCriterion":
                addInputField("New Criterion name");
                addInputField("First Criterion name");
                addInputField("Operation");
                addInputField("Second Criterion name");
            case "printAllCriterion":
                break;
            case "searchTasksBaseOnCriterion":
                addInputField("Criterion name");
                break;
            case "store":
                addInputField("File Path to store");
                break;
            case "load":
                addInputField("File Path to load");
                break;
        }

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void addInputField(String label) {
        inputPanel.add(new JLabel(label));
        JTextField textField = new JTextField();
        inputPanel.add(textField);
        inputFields.add(textField);
    }

    private void executeCommand() {
        String command = (String) commandBox.getSelectedItem();
        try {
            String result = " ";
            if (command != null) {
                switch (command) {
                    case "createSimpleTask":
                        String prerequisitesinput = inputFields.get(3).getText();
                        String[] prerequisites = prerequisitesinput.isEmpty() ? new String[]{","} : prerequisitesinput.split(",");
                        result = tms.createSimpleTask(inputFields.get(0).getText(),
                                inputFields.get(1).getText(),
                                inputFields.get(2).getText(),
                                Arrays.asList(prerequisites));
                        break;
                    case "createCompositeTask":
                        String subtasksInput = inputFields.get(2).getText();
                        String[] subtaskArray = subtasksInput.split(",");
                        // Convert the array to a list
                        List<String> subtasks = new ArrayList<>(Arrays.asList(subtaskArray));
                        result = tms.createCompositeTask(inputFields.get(0).getText(),
                                inputFields.get(1).getText(),
                                subtasks);
                        break;
                    case "deleteTask":
                        result = tms.deleteTask(inputFields.get(0).getText());
                        break;

                    case "changeTask":
                        result = tms.changeTask(inputFields.get(0).getText(),
                                inputFields.get(1).getText(),
                                inputFields.get(2).getText());
                        break;
                    case "print":
                        String taskName = inputFields.get(0).getText().trim();
                        if (taskName.isEmpty()) {
                            result = tms.printAllTasks();
                        } else {
                            result = tms.printTask(taskName);
                        }
                        break;
                    case "creatBasicCriterion":
                        result = tms.addBasicCriterion(inputFields.get(0).getText(),
                                inputFields.get(1).getText(),
                                inputFields.get(2).getText(), inputFields.get(3).getText());
                        break;
                    case "creatNegatedCriterion":
                        result = tms.addNegatedCriterion(inputFields.get(0).getText(),
                                inputFields.get(1).getText());
                        break;
                    case "creatBinaryCriterion":
                        result = tms.addBinaryCriterion(inputFields.get(0).getText(),
                                inputFields.get(1).getText(), inputFields.get(2).getText(), inputFields.get(3).getText());
                    case "printAllCriterion":
                        result = tms.outPutAllCriteria();
                        break;
                    case "searchTasksBaseOnCriterion":
                        result = tms.search(inputFields.get(0).getText());
                        break;
                    case "store":
                        result = tms.store(inputFields.get(0).getText());
                        break;
                    case "load":
                        result = tms.load(inputFields.get(0).getText());
                        break;
                    default:
                        result = "Command not implemented";
                }
            }
            commandOutputArea.setText(result);
            updateTasksList();
        } catch (Exception ex) {
            commandOutputArea.setText("Error: " + ex.getMessage());
        }
    }

    private void updateTasksList() {
        // Redirect output temporarily to capture the task names
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        tms.outPutAllTasks();

        // Set back System.out
        System.out.flush();
        System.setOut(old);

        tasksOutputArea.setText(baos.toString());
    }

    /**
     * The main method.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TMS_GUI::new);
    }
}