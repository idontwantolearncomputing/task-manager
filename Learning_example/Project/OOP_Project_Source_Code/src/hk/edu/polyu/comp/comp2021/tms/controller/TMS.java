package hk.edu.polyu.comp.comp2021.tms.controller;
import hk.edu.polyu.comp.comp2021.tms.model.*;
import hk.edu.polyu.comp.comp2021.tms.model.exceptions.*;

import java.io.IOException;
import java.util.*;


/**
 * The TMS class serves as the controller for the Task Management System.
 * It provides a high-level interface for interacting with the TaskManager,
 * offering methods to create, update, delete, and report tasks and criteria.
 * This class handles the business logic and delegates lower-level operations
 * to the TaskManager.
 * {@code @auther} CHEN Chi-wei
 */
public class TMS {
    private final TaskManager taskManager;

    /**
     * Constructs a new instance of the TMS controller.
     * Initializes the TaskManager to manage the tasks and criteria within the system.
     */
    public TMS() {
        this.taskManager = new TaskManager();

    }

    /**
     * Creates a simple task in the Task Management System.
     * Note: This method handles exceptions internally and returns appropriate error messages as part of the response string.
     *
     * @param name The name of the simple task to be created.
     * @param description The description of the simple task.
     * @param duration The duration of the task in a format that can be parsed to a double.
     * @param prerequisites A list of names of prerequisite tasks for this task.
     * @return A string indicating the success or failure of the task creation. Returns "Task created successfully." if the operation is successful, or an error message otherwise.
     * {@code @auther} CHEN Chi-wei
     */
    public String createSimpleTask(String name, String description, String duration, List<String> prerequisites) {
        try {
            taskManager.addSimpleTask(name, description, duration, prerequisites);
            return "Task created successfully.";
        } catch (TaskAlreadyExistsException e) {
            return "Error: Task already exists.";
        } catch (InvalidPrerequisiteException e) {
            return "Error: Invalid prerequisite.";
        } catch (NumberFormatException e) {
            return "Error: The input is not a valid duration.";
        }
    }

    /**
     * Creates a composite task in the Task Management System.
     * This method attempts to create a composite task composed of several subtasks. If an error occurs, such as
     * if the composite task already exists or if any of the subtasks are invalid, the method will catch these exceptions
     * and return an appropriate error message.
     *
     * @param name The name of the composite task to be created.
     * @param description The description of the composite task.
     * @param subTasks A list of names of subtasks that form this composite task.
     * @return A string indicating the success or failure of the composite task creation, along with an error message if applicable.
     * {@code @auther} CHEN Chi-wei
     */
    public String createCompositeTask(String name, String description, List<String> subTasks) {
        try {
            taskManager.addCompositeTask(name, description, subTasks);
            return "Composite task created successfully.";
        } catch (TaskAlreadyExistsException e) {
            return "Error: The task already exists.";
        } catch (InvalidSubtaskException e) {
            return "Error: Invalid subtask.";
        }
    }

    /**
     * Deletes a task from the Task Management System.
     *
     * @param name The name of the task to be deleted.
     * @return A string indicating the success or failure of the task deletion, along with an error message if applicable.
     *         Returns "Task deleted successfully." if the operation is successful, or an error message otherwise.
     * {@code @auther} CHEN Chi-wei
     */
    public String deleteTask(String name) {
        try {
            taskManager.removeTask(name);
            return "Task deleted successfully.";
        } catch (TaskNotFoundException e) {
            return "Error: The task does not exist.";
        } catch (TaskCannotReomoveException e) {
            return "Error: The task cannot be removed.";
        } catch (InvalidSubtaskException e) {
            return "Error: Invalid subtask.";
        } catch (InvalidPrerequisiteException e) {
            return "Error: Invalid prerequisite.";
        }
    }

    /**
     * Updates a specific property of a task in the Task Management System.
     *
     * @param name The name of the task to be updated.
     * @param property The property of the task to be updated (e.g., 'description', 'duration').
     * @param value The new value(s) for the specified property. If the property is a list (like prerequisites),
     *              this should be a list of new values.
     * @return A string indicating the success or failure of the task update, along with an error message if applicable.
     * {@code @auther} CHEN Chi-wei
     */
    public String changeTask(String name, String property, List<String> value) {
        try {
            taskManager.updateTask(name, property, value);
            return "Task updated successfully.";
        } catch (TaskNotFoundException e) {
            return "Error: The task does not exist.";
        } catch (InvalidPrerequisiteException e) {
            return "Error: Invalid prerequisite.";
        } catch (InvalidSubtaskException e) {
            return "Error: Invalid subtask.";
        }
        // Catch other potential exceptions
    }

    /**
     * Updates a specific property of an existing task in the Task Management System.
     *
     * @param name The name of the task to be updated.
     * @param property The property of the task to update (e.g., 'description', 'duration').
     * @param value The new value for the specified property.
     * @return A string message indicating the success or failure of the update operation.
     * Returns "Task updated successfully." if the operation is successful, or an error message otherwise.
     * {@code @auther} CHEN Chi-wei
     */
    public String changeTask(String name, String property, String value) {
        try {
            taskManager.updateTask(name, property, value);
            return "Task updated successfully.";
        } catch (TaskNotFoundException e) {
            return "Error: The task does not exist.";
        } catch (InvalidPropertyException e) {
            return "Error: The property does not exist.";
        }
        // Catch other potential exceptions
    }


    /**
     * Retrieves and formats the details of a specific task in the Task Management System.
     *
     * @param name The name of the task whose details are to be printed.
     * @return A formatted string containing the details of the task, or an error message if the task does not exist.
     * {@code @auther} Zeng Junqi
     */
    public String printTask(String name) {
        try {
            List<String> taskDetails = taskManager.printTask(name); // 调用第一个printTask方法
            StringBuilder result = new StringBuilder("Task list printed successfully\n");
            for (String detail : taskDetails) {
                result.append(detail).append("\n");
            }
            return result.toString();
        } catch (TaskNotFoundException e) {
            return "Error: Task does not exist.";
        }
    }

    /**
     * Retrieves and formats the details of all tasks currently managed by the Task Management System.
     *
     * @return A formatted string containing the details of all tasks. If no tasks are available, returns an error message.
     * {@code @auther} Zeng Junqi
     */
    public String printAllTasks() {
        try {
            Map<String, List<String>> allTasksOutput = taskManager.printAllTasks();

            StringBuilder output = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : allTasksOutput.entrySet()) {
                output.append("Task: ").append(entry.getKey()).append("\n");
                for (String line : entry.getValue()) {
                    output.append(line).append("\n");
                }
                output.append("\n");
            }

            return output.toString();

        } catch (TaskNotFoundException e) {
            return "Error: The task does not exist.";
        } catch (TaskMapIsEmpty e) {
            return "Error: Task list is empty.";
        }
    }

    /**
     * Reports the total duration of a specified task. If the task is a composite task,
     * it calculates the cumulative duration including its subtasks.
     * Note: This method catches and handles exceptions internally, returning appropriate error messages
     * as part of the response string, rather than throwing these exceptions to the caller.
     *
     * @param name The name of the task for which the duration is being reported.
     * @return A string indicating the duration of the task, or an error message if the task does not exist
     *         or if any subtask or prerequisite is invalid.
     * &#064;catches  TaskNotFoundException if the task with the specified name does not exist.
     * &#064;catches  InvalidSubtaskException if any subtask is invalid.
     * &#064;catches  InvalidPrerequisiteException if any prerequisite is invalid.
     * {@code @auther} Zeng Junqi
     */
    public String reportDuration(String name) {
        try {
            return "Duration: " + taskManager.ReportDuration(name);
        } catch (TaskNotFoundException e) {
            return "Error: The task does not exist.";
        } catch (InvalidSubtaskException e) {
            return "Error: Invalid subtask.";
        } catch (InvalidPrerequisiteException e) {
            return "Error: Invalid prerequisite.";
        }
    }


    /**
     * Reports the earliest finish time for a specified task. If the task is a composite task,
     * it calculates the earliest finish time considering the duration of subtasks and their dependencies.
     * Note: This method catches and handles various exceptions internally, returning appropriate error messages
     * as part of the response string, rather than throwing these exceptions to the caller.
     *
     * @param name The name of the task for which the earliest finish time is being reported.
     * @return A string indicating the earliest finish time of the task, or an error message if the task does not exist
     *         or if any subtask or prerequisite is invalid.
     * &#064;catches  TaskNotFoundException if the task with the specified name does not exist.
     * &#064;catches  InvalidPrerequisiteException if any prerequisite is invalid.
     * &#064;catches  InvalidSubtaskException if any subtask is invalid.
     * {@code @auther} Zeng Junqi
     */
    public String reportEarliestFinishTime(String name) {
        try {
            taskManager.ReportEarliestFinishTime(name);
            return "Earliest finish time reported: " + taskManager.ReportEarliestFinishTime(name);
        } catch (TaskNotFoundException e) {
            return "Error: The task does not exist.";
        } catch (InvalidPrerequisiteException e) {
            return "Error: Invalid prerequisite.";
        }
    }

    /**
     * Adds a basic criterion to the Task Management System for task searching and filtering.
     *
     * @param name The name of the criterion.
     * @param property The property of the task to be evaluated (e.g., 'duration', 'name').
     * @param op The operation used for comparison (e.g., '>', '<', '==').
     * @param value The value to be compared with the task property.
     * @return A string message indicating the success or failure of adding the criterion.
     */
    public String addBasicCriterion(String name, String property, String op, String value) {
        // Check if the name is valid (non-null and non-empty)
        if (name == null || name.isEmpty()) {
            return "Error: Criterion name is invalid.";
        }
        // Check for valid property
        if (property == null || (!property.equals("duration") && !property.equals("name")
                && !property.equals("description") && !property.equals("prerequisites")
                && !property.equals("subtasks"))) {
            return "Error: Invalid property.";
        }
        // Handling 'duration' property
        if (property.equals("duration")) {
            Set<String> operators = new HashSet<>(Arrays.asList(">", "<", "==", ">=", "<=", "!="));
            double number;
            try {
                number = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return "The string is not a valid double.";
            }
            if (!operators.contains(op)) return "The op is not a valid value.";
            taskManager.defineBasicCriterion(name, property, op, number, property);
            return "Basic Criterion updated successfully.";
        }
        // Handling 'name' or 'description' properties
        if (property.equals("name") || property.equals("description")) {
            op = "contains";
            // Ensure value is a valid string
            if (value == null) {
                return "Error: Value for name or description is invalid.";
            }
            taskManager.defineBasicCriterion(name, property, op, value, property);
            return "Basic Criterion updated successfully.";
        }

        // Handling 'prerequisites' or 'subtasks' properties
        op = "contains";
        // Additional checks can be added here for the format of the value
        taskManager.defineBasicCriterion(name, property, op, value, property);
        return "Basic Criterion updated successfully.";
    }

    /**
     * Adds a negated criterion based on an existing criterion in the Task Management System.
     *
     * @param newCriterionName The name of the new negated criterion.
     * @param existingCriterionName The name of the existing criterion to be negated.
     * @return A string message indicating the success or failure of adding the negated criterion.
     */
    public String addNegatedCriterion(String newCriterionName, String existingCriterionName) {
        try {
            taskManager.defineNegatedCriterion(newCriterionName, existingCriterionName);
            return "Negated Criterion updated successfully.";
        } catch (CriterionNameNullException e) {
            // Return a message indicating the criterion name is null or empty
            return "Error: The criterion name cannot be null or empty.";
        } catch (CriterionAlreadyExistsException e) {
            // Return a message indicating the criterion already exists
            return "Error: A criterion with this name already exists.";
        } catch (TaskNotFoundException e) {
            // Return the specific error message from the exception
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Adds a binary criterion that combines two existing criteria in the Task Management System.
     *
     * @param newCriterionName The name of the new binary criterion.
     * @param firstCriterionName The name of the first existing criterion.
     * @param logicOp The logical operator (e.g., 'AND', 'OR').
     * @param secondCriterionName The name of the second existing criterion.
     * @return A string message indicating the success or failure of adding the binary criterion.
     */
    public String addBinaryCriterion(String newCriterionName, String firstCriterionName,
                                     String logicOp, String secondCriterionName) {
        try {
            taskManager.defineBinaryCriterion(newCriterionName, firstCriterionName, logicOp, secondCriterionName);
            return "Binary Criterion updated successfully.";
        } catch (CriterionNameNullException | CriterionAlreadyExistsException e) {
            // Handle specific exceptions for criterion name being null or already existing
            return "Error: " + e.getMessage();
        } catch (CritersionNotFoundException e) {
            // Handle the case where one of the criteria does not exist
            // Return the specific error message from the exception
            return "Error: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            // Log the exception details for debugging
            return "Invalid logic operation.";
        }
    }


    /**
     * Defines a criterion to check if a task is primitive in the Task Management System.
     */
    public void addIsPrimitiveCriterion() {
        taskManager.defineIsPrimitiveCriterion();
    }


    /**
     * Outputs all defined criteria in the Task Management System.
     *
     * @return A string listing all the criteria.
     */
    public String outPutAllCriteria() {
        return taskManager.printAllCriteria();
    }

    /**
     * Outputs all task names and their types in the Task Management System.
     * {@code @auther} CHEN Chi-wei
     */
    public void outPutAllTasks() {
        taskManager.PrintAllTaskNames();
    }


    /**
     * Stores the current state of the Task Management System to a file.
     *
     * @param caoyixin The name of the file to store the state.
     * @return A string indicating the success or failure of the store operation.
     */
    public String store(String caoyixin) {

        try{taskManager.storing(caoyixin);
            return "Store successfully.";
        } catch (RuntimeException e){
            return "Error: Runtime error.";
        }
    }

    /**
     * Loads the state of the Task Management System from a file.
     *
     * @param caoyixin The name of the file from which to load the state.
     * @return A string indicating the success or failure of the load operation.
     */
    public String load(String caoyixin) {
        try {taskManager.loading(caoyixin);
            return "Load successfully.";
        }
        catch (IOException e) {
            return "Error: IO ERROR.";
        }
        catch (TaskAlreadyExistsException e) {
            return "Error: Task already exists.";
        } catch (InvalidPrerequisiteException e) {
            return "Error: Invalid prerequisite.";
        } catch (NumberFormatException e) {
            return "Error: The input is not a valid duration.";
        } catch (InvalidSubtaskException e) {
            return "Error: Invalid subtask.";
        } catch (CritersionNotFoundException e) {
            // Return the specific error message from the exception
            return "Error: " + e.getMessage();
        } catch (CriterionNameNullException e) {
            return "Error: The criterion name cannot be null or empty.";
        } catch (CriterionAlreadyExistsException e) {
            return "Error: A criterion with this name already exists.";
        }
    }

    /**
     * Searches for tasks or criteria based on a given search string.
     * <p>
     * This method delegates the search operation to the taskManager's search method,
     * passing the given string {@code s} as the search criterion. If the specified criterion
     * is not found in the task manager, the method catches a {@link CritersionNotFoundException}
     * and returns an error message indicating that the criterion is not found.
     * </p>
     *
     * @param s The search string used to query tasks or criteria.
     * @return The result of the search operation. If the criterion specified in the search string
     *         is not found, an error message is returned.
     * @author WU Qixuan
     */
    public String search(String s) {
        try {
            return taskManager.search(s);
        }catch (CritersionNotFoundException e){
            return "Error: criterion is not found";
        }
    }
}
