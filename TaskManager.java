package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.model.exceptions.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The TaskManager class is responsible for managing tasks within the Task Management System (TMS).
 * It provides functionalities for adding, updating, removing, and searching for tasks based on various criteria.
 * The class also handles task storage and retrieval, and maintains a mapping of tasks and criteria.
 */
public class TaskManager {
    private static final double DOUBLE = 0.00001;
    private final Map<String, Task> taskMap;
    private final Map<String, Criterion> criteriaMap;
    private static final int CHARACTERS_TO_REMOVE = 4;

    /**
     * Constructs a new instance of TaskManager.
     * This constructor initializes the taskMap and criteriaMap,
     * which are used to manage tasks and criteria within the system.
     */
    public TaskManager() {
        taskMap = new HashMap<>();
        criteriaMap = new HashMap<>();
    }

    /**
     * Adds a simple task to the task management system.
     *
     * @param name              The name of the task.
     * @param description       The description of the task.
     * @param duration          The duration of the task.
     * @param prerequisiteNames A list of names of prerequisite tasks.
     * @throws TaskAlreadyExistsException   if a task with the same name already exists.
     * @throws InvalidPrerequisiteException if any of the prerequisite tasks are invalid.
     */
    // Add a simple task
    public void addSimpleTask(String name, String description, String duration, List<String> prerequisiteNames) throws TaskAlreadyExistsException, InvalidPrerequisiteException {
        // throw exception if task already exists
        if (taskMap.containsKey(name)) {
            throw new TaskAlreadyExistsException("Task with name '" + name + "' already exists.");
        }

        // throw exception if duration cannot be parsed
        try {
            Double.parseDouble(duration);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The input is not a valid duration.");
        }

        // throw exception if prerequisites are invalid
        List<Task> prerequisites = convertNamesToTasks(prerequisiteNames);

        // create the task
        SimpleTask simpleTask = new SimpleTask(name, description, Double.parseDouble(duration), prerequisites);
        taskMap.put(name, simpleTask);

        for (Task prerequisite : prerequisites) {
            prerequisite.getPrerequisiteOf().add(taskMap.get(name));
        }
    }

    /**
     * Adds a composite task to the task management system.
     *
     * @param name         The name of the composite task.
     * @param description  The description of the composite task.
     * @param subtaskNames A list of names of subtasks.
     * @throws TaskAlreadyExistsException if a task with the same name already exists.
     * @throws InvalidSubtaskException    if any of the subtasks are invalid.
     */
    public void addCompositeTask(String name, String description, List<String> subtaskNames) throws TaskAlreadyExistsException, InvalidSubtaskException {
        // throw exception if task already exists
        if (taskMap.containsKey(name)) {
            throw new TaskAlreadyExistsException("Task with name '" + name + "' already exists.");
        }

        // throw exception if subtasks are invalid
        List<Task> subtasks = convertNamesToSubtasks(subtaskNames);
        CompositeTasks compositeTask = new CompositeTasks(name, description, subtasks);

        // set the composite task as isComposite
        compositeTask.setIsComposite(true);

        // set the subtasks as isSubtask
        for (Task subtask : subtasks) {
            subtask.setIsSubtask(true);
        }

        // put the composite task into the task map
        taskMap.put(name, compositeTask);
    }

    // Convert a list of task names to a list of tasks
    private List<Task> convertNamesToSubtasks(List<String> subtaskNames) throws InvalidSubtaskException {
        List<Task> subtasks = new ArrayList<>();
        if (subtaskNames.isEmpty()) {
            return subtasks;
        }
        if (subtaskNames.get(0).equals(",")) {
            return subtasks;
        }
        for (String subtaskName : subtaskNames) {
            Task subtask = taskMap.get(subtaskName);
            if (subtask == null) {
                throw new InvalidSubtaskException("Task '" + subtaskName + "' does not exist."); // Or InvalidSubtaskException based on context
            }
            subtasks.add(subtask);
        }
        return subtasks;
    }

    private List<Task> convertNamesToTasks(List<String> taskNames) throws InvalidPrerequisiteException {
        List<Task> tasks = new ArrayList<>();
        if (taskNames.get(0).equals(",")) {
            return tasks;
        }
        for (String taskName : taskNames) {
            Task task = taskMap.get(taskName);
            if (task == null) {
                throw new InvalidPrerequisiteException("Task '" + taskName + "' does not exist."); // Or InvalidSubtaskException based on context
            }
            tasks.add(task);
        }
        return tasks;
    }


    /**
     * Prints the details of a specific task.
     *
     * @param name The name of the task to be printed.
     * @return A list of strings representing the details of the task.
     * @throws TaskNotFoundException if the task with the specified name does not exist.
     * {@code @auther} Zeng Junqi
     */
    public List<String> printTask(String name) throws TaskNotFoundException {
        return printTask(name, 0); // Start with depth 0
    }

    private List<String> printTask(String name, int depth) throws TaskNotFoundException {
        Task task = taskMap.get(name);
        List<String> output = new ArrayList<>();

        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' does not exist.");
        }

        String indent = String.join("", Collections.nCopies(depth, "    "));

        if (task.getIsComposite()) {
            output.add(indent + "Name: " + task.getName());
            output.add(indent + "Type: composite task");
            output.add(indent + "Description: " + task.getDescription());
            output.add(indent + "Subtask: Exist " + task.getSubtasks().size() + " subtasks.");
            for (int i = 0; i < task.getSubtasks().size(); i++) {
                int number = i + 1;
                output.add(indent + number + ".");
                output.addAll(printTask(task.getSubtasks().get(i), depth + 1)); // Increase depth for subtasks
            }
        } else {
            output.add(indent + "Name: " + task.getName());
            output.add(indent + "Type: simple task");
            output.add(indent + "Description: " + task.getDescription());
            output.add(indent + "Duration: " + task.getDuration());
            output.add(indent + "Prerequisites: Exist " + task.getPrerequisites().size() + " prerequisites.");
            for (int j = 0; j < task.getPrerequisites().size(); j++) {
                int number = j + 1;
                output.add(indent + number + ".");
                output.addAll(printTask(task.getPrerequisites().get(j), depth + 1)); // Increase depth for prerequisites
            }
        }

        return output;
    }


    /**
     * Prints the details of all tasks currently managed by the Task Manager.
     * Each task's information is retrieved and formatted into a list of strings,
     * with the task name as the key in the resulting map.
     *
     * @return A map where each key is a task name and the corresponding value is a list of strings
     * representing the details of that task.
     * @throws TaskMapIsEmpty        if there are no tasks currently in the task management system.
     * @throws TaskNotFoundException if a task referenced during the operation cannot be found.
     * {@code @auther} Zeng Junqi
     */
    public Map<String, List<String>> printAllTasks() throws TaskMapIsEmpty, TaskNotFoundException {
        if (taskMap.isEmpty()) {
            throw new TaskMapIsEmpty("Task map is empty.");
        }

        Map<String, List<String>> allTasksOutput = new HashMap<>();

        for (Task task : taskMap.values()) {
            List<String> taskOutput = printTask(task.getName());
            allTasksOutput.put(task.getName(), taskOutput);
        }

        return allTasksOutput;
    }

    /**
     * Reports the total duration of a specified task. For composite tasks, it calculates the
     * cumulative duration of all subtasks, considering prerequisites and overlaps.
     *
     * @param name The name of the task for which the duration is to be reported.
     * @return The total duration of the task.
     * @throws TaskNotFoundException        if the task with the specified name does not exist.
     * @throws InvalidSubtaskException      if any subtask is invalid.
     * @throws InvalidPrerequisiteException if any prerequisite is invalid.
     * {@code @auther} Zeng Junqi
     */
    public double ReportDuration(String name) throws TaskNotFoundException, InvalidSubtaskException, InvalidPrerequisiteException {
        Task task = taskMap.get(name);
        double duration = 0;
        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' does not exist.");
        }
        if (task.getIsComposite()) {
            for (int i = 0; i < task.getSubtasks().size(); i++) {
                duration += taskMap.get(task.getSubtasks().get(i)).getDuration();
            }
            List<Task> subtasks = convertNamesToSubtasks(task.getSubtasks());
            for (int j = 0; j < subtasks.size(); j++) {
                if (subtasks.get(j).getPrerequisites().size() >= 2) {
                    List<Task> prerequisites = convertNamesToTasks(subtasks.get(j).getPrerequisites());
                    prerequisites.retainAll(subtasks);
                    if (prerequisites.size() >= 2) {
                        List<Double> dur = new ArrayList<>();
                        for (Task subtask : prerequisites) {
                            dur.add(subtask.getDuration());
                        }
                        dur.sort(Collections.reverseOrder());
                        for (int l = 1; l < dur.size(); l++) {
                            duration = duration - dur.get(l);
                        }
                    }
                }
            }
            return duration;
        }
        duration += task.getDuration();
        return duration;
    }

    /**
     * Reports the earliest finish time for a specified task. For composite tasks, it calculates
     * the earliest finish time considering the duration of subtasks and their dependencies.
     *
     * @param name The name of the task for which the earliest finish time is to be reported.
     * @return The earliest finish time of the task.
     * @throws TaskNotFoundException        if the task with the specified name does not exist.
     * @throws InvalidPrerequisiteException if any prerequisite is invalid.
     * {@code @auther} Zeng Junqi
     */
    public double ReportEarliestFinishTime(String name) throws TaskNotFoundException, InvalidPrerequisiteException {
        Task task = taskMap.get(name);
        double duration = 0;
        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' does not exist.");
        }
        if (task.getIsComposite()) {
            for (int i = 0; i < task.getSubtasks().size(); i++) {
                duration += ReportEarliestFinishTime(task.getSubtasks().get(i));
            }
            List<Task> subtasks = convertNamesToTasks(task.getSubtasks());
            for (int j = 0; j < task.getSubtasks().size(); j++) {
                if (!subtasks.get(j).getPrerequisites().isEmpty()) {
                    List<Task> prerequisites = convertNamesToTasks(subtasks.get(j).getPrerequisites());
                    prerequisites.retainAll(subtasks);
                    if (prerequisites.size() >= 2) {
                        List<Double> dur = new ArrayList<>();
                        for (Task subtask : prerequisites) {
                            dur.add(subtask.getDuration());
                        }
                        for (Double aDouble : dur) {
                            duration = duration - aDouble;
                        }
                    }
                }
            }
            return duration;
        }
        List<Double> dur = new ArrayList<>();
        if (!task.getPrerequisites().isEmpty()) {
            List<Task> prerequisites = convertNamesToTasks(task.getPrerequisites());
            for (Task tasks : prerequisites) {
                dur.add(tasks.getDuration());
            }

            dur.sort(Collections.reverseOrder());
            duration += dur.get(0);
        }
        duration += task.getDuration();
        return duration;
    }

    /**
     * Searches for tasks that meet a specified criterion. Supports both basic and complex (binary) criteria.
     *
     * @param criterion The criterion to be used for searching tasks.
     * @return A list of task names that meet the specified criterion.
     * @throws CritersionNotFoundException if the criterion with the specified name does not exist.
     * {@code @auther} Zeng Junqi,Wu Qixuan
     */
    public String search(String criterion) throws CritersionNotFoundException {
        StringBuilder tasksnames = new StringBuilder();
        List<String> tasks;
        Criterion criterion1 = criteriaMap.get(criterion);
        if (criterion1 == null) {
            throw new CritersionNotFoundException("");
        }
        tasks = search(criterion1);
        List<String> uniqueList = new ArrayList<>();
        for (String element : tasks) {
            if (!uniqueList.contains(element)) {
                uniqueList.add(element);
            }
        }
        for (String element : uniqueList) {
            tasksnames.append(element).append(" ");
        }
        if (tasksnames.length() == 0) {
            return "No task found";
        }
        return tasksnames.toString();
    }
    /**
     * Searches for tasks that meet a specified criterion. Supports both basic and complex (binary) criteria.
     *
     * @param criterion The criterion to be used for searching tasks.
     * @return A list of task names that meet the specified criterion.
     */
    public List<String> search(Criterion criterion) {
        switch (criterion.getType()) {
            case "binary":
                return searchBinaryCriterion(criterion);
            case "negated":
                return searchNegatedCriterion(criterion);
            default :
                return searchBasicCriterion(criterion);
        }
    }

    private List<String> searchBinaryCriterion(Criterion criterion) {
        List<String> firstList = search(criterion.getFirstCriterion());
        List<String> secondList = search(criterion.getSecondCriterion());
        if ("&&".equals(criterion.getLogicOp())) {
            firstList.retainAll(secondList);
            return firstList;
        }
        {
            Set<String> uniqueTasks = new HashSet<>(firstList);
            uniqueTasks.addAll(secondList);  // This is the correct use of addAll()
            return new ArrayList<>(uniqueTasks);  // Now we pass the set to the ArrayList constructor
        }

    }


    private List<String> searchBasicCriterion(Criterion criterion) {
        return taskMap.values().stream()
                .filter(task -> taskMatchesCriterion(task, criterion))
                .map(Task::getName)
                .collect(Collectors.toList());
    }

    private List<String> searchNegatedCriterion(Criterion criterion) {
        // Check if criterion's value is a String and starts with "Not ".
        Criterion actualCriterion = getCriterion(criterion);

        // Filter tasks that do not match the actual criterion.
        if ("duration".equals(actualCriterion.getProperty())) {
            return searchBasicCriterion(actualCriterion);
        }
        return taskMap.values().stream()
                .filter(task -> !taskMatchesCriterion(task, actualCriterion))
                .map(Task::getName)
                .collect(Collectors.toList());
    }

    private static Criterion getCriterion(Criterion criterion) {
        Object actualValue;
        if (!Objects.equals(criterion.getProperty(), "duration")) {
            String criterionValue = (String) criterion.getValue();
            actualValue = criterionValue.substring(4);
        }
        else {
            actualValue = criterion.getValue();
        }
        // Create a new Criterion with the actual value (without "Not ") for comparison.
        return new Criterion(
                criterion.getProperty(),
                criterion.getOperation(),
                actualValue,
                criterion.getType()
        );
    }


    private boolean taskMatchesCriterion(Task task, Criterion criterion) {
        switch (criterion.getProperty()) {
            case "duration":
                // Assume value is Double for duration type criteria.
                return matchesOperation(task.getDuration(), (Double) criterion.getValue(), criterion.getOperation());
            case "name":
                // Assume value is String for name type criteria.
                return task.getName().contains((String) criterion.getValue());
            case "description":
                return task.getDescription().contains((String) criterion.getValue());
            case "prerequisites":
                if (!task.getIsComposite()) {
                    return task.getPrerequisites().contains((String) criterion.getValue());
                }
                return false;
            case "subtasks":
                if (task.getIsComposite()) {
                    return task.getSubtasks().contains((String) criterion.getValue());
                }
                return false;
            default:
                return !task.getIsComposite();
        }
    }

    private boolean matchesOperation(double taskValue, double criterionValue, String operation) {
        switch (operation) {
            case "<": return taskValue < criterionValue;
            case ">": return taskValue > criterionValue;
            case ">=": return taskValue >= criterionValue;
            case "<=": return taskValue <= criterionValue;
            case "==": return Math.abs(taskValue - criterionValue) < DOUBLE; // Use a small threshold for double comparison
            default: return Math.abs(taskValue - criterionValue) >= DOUBLE;
        }
    }


    /**
     * Removes a task from the task management system.
     *
     * @param name The name of the task to be removed.
     * @throws TaskNotFoundException        if the task with the specified name does not exist.
     * @throws TaskCannotReomoveException   if the task is a subtask or a prerequisite and cannot be removed.
     * @throws InvalidSubtaskException      if any subtask is invalid.
     * @throws InvalidPrerequisiteException if any prerequisite is invalid.
     */
    public void removeTask(String name) throws TaskNotFoundException, TaskCannotReomoveException, InvalidSubtaskException, InvalidPrerequisiteException {
        Task task = taskMap.get(name);
        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' does not exist.");
        }

        if (task.getIsComposite()) {
            //get the subtasks and remove the tasks
            List<Task> subtasks = convertNamesToSubtasks(task.getSubtasks());
            for (Task subtask : subtasks) {
                taskMap.remove(subtask.getName());
            }
            return;
        }

        if (task.getIsSubtask()) {
            throw new TaskCannotReomoveException("Subtask cannot be removed.");
        }

        // primitive task cannot be removed
        if (task.isPrerequisite()) {
            throw new TaskCannotReomoveException("Prerequisite task cannot be removed.");
        }

        // get its prerequisites and remove the task
        if (task.getPrerequisites() != null && !task.getPrerequisites().isEmpty()) {
            List<Task> prerequisites = convertNamesToTasks(task.getPrerequisites());
            for (Task prerequisite : prerequisites) {
                prerequisite.getPrerequisiteOf().remove(task);
            }
        }


        taskMap.remove(name);
    }


    /**
     * Updates the properties of a task in the task management system.
     *
     * @param name     The name of the task to update.
     * @param property The property of the task to update (like prerequisites or subtasks).
     * @param value    A list of values for the specified property.
     * @throws InvalidPrerequisiteException if any prerequisite is invalid.
     * @throws InvalidSubtaskException      if any subtask is invalid.
     * @throws TaskNotFoundException        if the task with the specified name does not exist.
     */
    public void updateTask(String name, String property, List<String> value) throws InvalidPrerequisiteException, InvalidSubtaskException, TaskNotFoundException {
        Task task = taskMap.get(name);
        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' does not exist.");
        }
        switch (property) {
            case "prerequisites": {
                if (task.getIsComposite()) {
                    throw new InvalidPrerequisiteException("Composite task cannot have prerequisites.");
                }
                List<Task> prerequisites = convertNamesToTasks(value);
                task.setPrerequisites(prerequisites);
                break;
            }
            case "subtasks": {
                List<Task> subtasks = convertNamesToSubtasks(value);
                task.setSubtasks(subtasks);
                break;
            }
        }
    }

    /**
     * Updates the properties of a task in the task management system.
     *
     * @param name     The name of the task to update.
     * @param property The property of the task to update.
     * @param value    The new value for the specified property.
     * @throws TaskNotFoundException    if the task with the specified name does not exist.
     * @throws InvalidPropertyException if the specified property does not exist.
     */
    public void updateTask(String name, String property, String value) throws TaskNotFoundException, InvalidPropertyException {
        Task task = taskMap.get(name);
        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' does not exist.");
        }

        switch (property) {
            case "name":
                task.setName(value);
                break;
            case "description":
                task.setDescription(value);
                break;
            case "duration":
                task.setDuration(Double.parseDouble(value));
                break;
            default:
                throw new InvalidPropertyException("Property '" + property + "' does not exist.");
        }
    }

    /**
     * Defines a basic criterion for searching tasks.
     *
     * @param name     The name of the new criterion.
     * @param property The property of the task to be evaluated by the criterion.
     * @param op       The operation to be used for comparison (like '<', '>', '=', etc.).
     * @param value    The value to be compared with.
     * @param type     The type of the property.
     */
    public void defineBasicCriterion(String name, String property, String op, Object value, String type) {
        Criterion newCriterion = new Criterion(property, op, value, type);
        criteriaMap.put(name, newCriterion);
    }

    /**
     * Defines a negated criterion based on an existing criterion.
     *
     * @param newCriterionName      The name of the new negated criterion.
     * @param existingCriterionName The name of the existing criterion to be negated.
     * @throws TaskNotFoundException           if the existing criterion does not exist.
     * @throws CriterionAlreadyExistsException if a criterion with the new name already exists.
     * @throws CriterionNameNullException      if the new criterion name is null or empty.
     */
    public void defineNegatedCriterion(String newCriterionName, String existingCriterionName)
            throws TaskNotFoundException, CriterionAlreadyExistsException, CriterionNameNullException {

        if (newCriterionName == null || newCriterionName.isEmpty()) {
            throw new CriterionNameNullException("New criterion name cannot be null or empty.");
        }
        if (criteriaMap.containsKey(newCriterionName)) {
            throw new CriterionAlreadyExistsException("A criterion with the given new name already exists.");
        }

        Criterion existingCriterion = criteriaMap.get(existingCriterionName);
        if (existingCriterion == null) {
            throw new TaskNotFoundException("Criterion with name " + existingCriterionName + " does not exist.");
        }

        Criterion negatedCriterion;

        if ("binary".equals(existingCriterion.getType())) {
            Criterion firstNegated = new Criterion(existingCriterion.getFirstCriterion());
            modify(firstNegated, isExistingCriterionNegated(existingCriterion.getFirstCriterion()));
            firstNegated.setType("negated");

            Criterion secondNegated = new Criterion(existingCriterion.getSecondCriterion());
            modify(secondNegated, isExistingCriterionNegated(existingCriterion.getSecondCriterion()));
            secondNegated.setType("negated");

            String negatedLogicOp = "&&".equals(existingCriterion.getLogicOp()) ? "||" : "&&";

            negatedCriterion = new Criterion(firstNegated, negatedLogicOp, secondNegated);
            negatedCriterion.setType("binary");
        } else {
            negatedCriterion = new Criterion(existingCriterion);
            modify(negatedCriterion, isExistingCriterionNegated(existingCriterion));
            negatedCriterion.setType("negated");
        }

        criteriaMap.put(newCriterionName, negatedCriterion);
    }

    private boolean isExistingCriterionNegated(Criterion existingCriterion) {
        return "negated".equals(existingCriterion.getType());
    }

    /**
     * Defines a binary criterion that combines two existing criteria with a logical operator.
     *
     * @param newCriterionName    The name of the new binary criterion.
     * @param firstCriterionName  The name of the first criterion to be combined.
     * @param logicOp             The logical operator to be used for combination ('&&' or '||').
     * @param secondCriterionName The name of the second criterion to be combined.
     * @throws CriterionNameNullException      if the new criterion name is null or empty.
     * @throws CriterionAlreadyExistsException if a criterion with the new name already exists.
     * @throws CritersionNotFoundException           if either of the criteria to be combined does not exist.
     */
    public void defineBinaryCriterion(String newCriterionName, String firstCriterionName,
                                      String logicOp, String secondCriterionName) throws CriterionNameNullException, CriterionAlreadyExistsException, CritersionNotFoundException {
        // Check if newCriterionName is null or empty
        if (newCriterionName == null || newCriterionName.isEmpty()) {
            throw new CriterionNameNullException("New criterion name cannot be null or empty.");
        }
        // Check if a criterion with newCriterionName already exists
        if (criteriaMap.containsKey(newCriterionName)) {
            throw new CriterionAlreadyExistsException("A criterion with the name '" + newCriterionName + "' already exists.");
        }
        // Retrieve and check the first criterion
        Criterion firstCriterion = criteriaMap.get(firstCriterionName);
        if (firstCriterion == null) {
            throw new CritersionNotFoundException("First criterion with name '" + firstCriterionName + "' does not exist.");
        }
        // Retrieve and check the second criterion
        Criterion secondCriterion = criteriaMap.get(secondCriterionName);
        if (secondCriterion == null) {
            throw new CritersionNotFoundException("Second criterion with name '" + secondCriterionName + "' does not exist.");
        }
        if (!logicOp.equals("&&") && !logicOp.equals("||")) {
            throw new IllegalArgumentException("Invalid logic operation.");
        }
        // Create and add the binary criterion
        Criterion binaryCriterion = new Criterion(firstCriterion, logicOp, secondCriterion);
        criteriaMap.put(newCriterionName, binaryCriterion);
    }


    /**
     * Modifies the specified criterion. For negated criteria, this method alters
     * the operation or value based on the criterion's type and property.
     *
     * @param criterion The criterion to be modified.
     */
    private static void modify(Criterion criterion, boolean isExistingCriterionNegated) {
        if ("duration".equals(criterion.getPropertyType())) {
            modifyOperation(criterion);
        } else {
            if ("negated".equals(criterion.getType()) && !isExistingCriterionNegated) {
                String temp = (String) criterion.getValue();
                criterion.setValue("Not " + temp);
            } else {
                String temp = (String) criterion.getValue();
                if (temp.length() > CHARACTERS_TO_REMOVE) {
                    String result = temp.substring(CHARACTERS_TO_REMOVE);
                    criterion.setValue(result);
                }
            }
        }
    }

    /**
     * Defines a criterion for checking if a task is primitive.
     * This method adds a predefined criterion to the criteria map.
     */
    public void defineIsPrimitiveCriterion() {
        Criterion isPrimitive = new Criterion("IsPrimitive", "", "", "");
        criteriaMap.put("IsPrimitive", isPrimitive);
    }


    private static void modifyOperation(Criterion criterion) {
        if ("duration".equals(criterion.getPropertyType())) {
            switch (criterion.getOperation()) {
                case ">":
                    criterion.setOperation("<=");
                    break;
                case "<":
                    criterion.setOperation(">=");
                    break;
                case ">=":
                    criterion.setOperation("<");
                    break;
                case "<=":
                    criterion.setOperation(">");
                    break;
                case "==":
                    criterion.setOperation("!=");
                    break;
                case "!=":
                    criterion.setOperation("==");
                    break;
            }
        }
    }

    /**
     * Prints all defined criteria in the task management system.
     *
     * @return A string representation of all defined criteria.
     */
    public String printAllCriteria() {
        if (criteriaMap.isEmpty()) {
            return "No criteria defined.";
        }

        StringBuilder output = new StringBuilder("Defined Criteria:\n");
        for (Map.Entry<String, Criterion> entry : criteriaMap.entrySet()) {
            String criterionName = entry.getKey();
            Criterion criterion = entry.getValue();
            output.append(criterionName).append(": ").append(criterion.toString()).append("\n");
        }

        return output.toString();
    }


    /**
     * Prints the names and types of all tasks currently managed by the Task Manager.
     */
    public void PrintAllTaskNames () {
        for (Map.Entry<String, Task> entry : taskMap.entrySet()) {
            //print task name and types
            String taskName = entry.getKey();
            Task task = entry.getValue();
            if (task.getIsComposite()) {
                System.out.println(taskName + ": composite task");
            } else {
                System.out.println(taskName + ": simple task");
            }
        }
        for (Map.Entry<String, Criterion> entry1 : criteriaMap.entrySet()) {
            String criterionName = entry1.getKey();
            Criterion criterion = entry1.getValue();
            System.out.println(criterionName + ": " + criterion.toString());
        }
    }

    /**
     * Stores the task and criterion information into a specified file.
     * <p>
     * This method iterates over the entries in the task map and criterion map, and writes
     * their details to the file specified by the parameter {@code yixincao}. The details are
     * formatted and separated by the "%" symbol. The method handles different types of criteria
     * (basic, negated, and binary) and writes them accordingly.
     * </p>
     *
     * @param yixincao The path of the file where the task and criterion information will be stored.
     * @throws RuntimeException if an IOException is caught during the writing process.
     * @author WU Qixuan
     */
    public void storing(String yixincao) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(yixincao))) {
            for (Map.Entry<String, Task> entry : taskMap.entrySet()) {
                Task task = entry.getValue();
                String line = task.getIsComposite()
                        ? String.join("%", "compositeTask", entry.getKey(), task.getDescription(), String.valueOf(task.getSubtasks()))
                        : String.join("%", "simpletask", entry.getKey(), task.getDescription(), String.valueOf(task.getPrerequisites()), String.valueOf(task.getDuration()));
                writer.write(line);
                writer.newLine();
            }

            for (Map.Entry<String, Criterion> entry : criteriaMap.entrySet()) {
                Criterion criterion = entry.getValue();
                String line;
                switch (criterion.getType()) {
                    case "basic":
                    case "negated":
                        line = String.join("%", criterion.getType(), entry.getKey(), criterion.getProperty(), criterion.getOperation(), String.valueOf(criterion.getValue()), criterion.getType());
                        writer.write(line);
                        writer.newLine();
                        break;
                    case "binary":
                        String foundKey1 = findKeyForCriterion(criterion.getFirstCriterion());
                        String foundKey2 = findKeyForCriterion(criterion.getSecondCriterion());
                        if (foundKey1 != null && foundKey2 != null) {
                            line = String.join("%", "binary", entry.getKey(), foundKey1, criterion.getLogicOp(), foundKey2);
                            writer.write(line);
                            writer.newLine();
                        }
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the key associated with a given criterion in the criteria map.
     * <p>
     * Iterates through the criteria map and returns the key for which the corresponding value
     * matches the given criterion. If no matching criterion is found, null is returned.
     * </p>
     *
     * @param criterion The criterion for which the key needs to be found.
     * @return The key associated with the given criterion or null if no match is found.
     * @author WU Qixuan
     */
    private String findKeyForCriterion(Criterion criterion) {
        for (Map.Entry<String, Criterion> entry : criteriaMap.entrySet()) {
            if (entry.getValue().equals(criterion)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Converts a string representation of a list into an actual List object.
     * <p>
     * This method is designed to process strings that represent lists in a format similar to arrays in Java.
     * For example, the string "[item1, item2, item3]" is converted into a list containing "item1", "item2", and "item3".
     * If the input string is "[]", indicating an empty list, an empty ArrayList is returned.
     * </p>
     * <p>
     * Note: This method assumes that individual items in the input string are separated by commas and possibly
     * followed by whitespace. Items are not trimmed of whitespace at the beginning or end.
     * </p>
     *
     * @param input The string to be converted into a list. Expected format: "[item1, item2, ...]".
     * @return A List containing the items from the input string. Returns an empty list if the input is "[]".
     * @author WU Qixuan
     */
    private static List<String> convertStringToList(String input) {
        if ("[]".equals(input)) {
            return new ArrayList<>();
        }
        else {
            input = input.substring(1, input.length() - 1);
            String[] items = input.split(",\\s*");
            return new ArrayList<>(Arrays.asList(items));
        }
    }

    /**
     * Loads and processes task and criterion data from a specified file.
     * <p>
     * This method reads data from a file, whose path is given by the parameter {@code cyx}. It parses each line
     * to identify the type of data (task or criterion) and processes it accordingly. Binary criteria and composite
     * tasks are stored temporarily and processed after initial parsing.
     * </p>
     *
     * @param cyx The path of the file from which to load the data.
     * @throws IOException If an I/O error occurs while reading from the file.
     * @throws InvalidPrerequisiteException If prerequisites for any task are invalid.
     * @throws InvalidSubtaskException If subtasks for any composite task are invalid.
     * @throws TaskAlreadyExistsException If a task being added already exists.
     * @throws CriterionNameNullException If a criterion name is null or empty.
     * @throws CriterionAlreadyExistsException If a criterion being added already exists.
     * @throws CritersionNotFoundException If a specified criterion for binary criteria is not found.
     * @author WU Qixuan
     */
    public void loading(String cyx) throws IOException, InvalidPrerequisiteException, InvalidSubtaskException, TaskAlreadyExistsException,
            CriterionNameNullException, CriterionAlreadyExistsException, CritersionNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(cyx))) {
            String line;
            List<String[]> binaryCriteria = new ArrayList<>();
            List<String[]> compositeTasks = new ArrayList<>();
            List<String> prerequisiteNames;

            while ((line = reader.readLine()) != null) {
                String[] array = line.split("%");
                switch (array[0]) {
                    case "binary":
                        binaryCriteria.add(array);
                        break;
                    case "compositeTask":
                        compositeTasks.add(array);
                        break;
                    case "simpletask":
                        prerequisiteNames = convertStringToList(array[3]);
                        if(prerequisiteNames.isEmpty()){
                            prerequisiteNames.add(",");
                        }
                        addSimpleTask(array[1], array[2], array[4], prerequisiteNames);
                        break;
                    case "basic":
                        if ("IsPrimitive".equals(array[1])) {
                            defineIsPrimitiveCriterion();
                        } else {
                            defineBasicCriterion(array[1], array[2], array[3], array[4], array[5]);
                        }
                        break;
                    case "negated":
                        defineBasicCriterion(array[1], array[2], array[3], array[4], array[5]);
                        criteriaMap.get(array[1]).setType("negated");
                        break;
                }
            }

            processBinaryCriteria(binaryCriteria);
            processCompositeTasks(compositeTasks);
        }
    }

    /**
     * Processes a list of binary criteria specifications.
     * <p>
     * Each array in the list is expected to contain details for one binary criterion, which are then used
     * to define and add the criterion to the system.
     * </p>
     *
     * @param binaryCriteria A list of string arrays, each representing a binary criterion.
     * @throws CritersionNotFoundException If a criterion specified in the binary criteria does not exist.
     * @throws CriterionNameNullException If the name of a new criterion is null or empty.
     * @throws CriterionAlreadyExistsException If a binary criterion with the same name already exists.
     * @author WU Qixuan
     */
    private void processBinaryCriteria(List<String[]> binaryCriteria) throws CritersionNotFoundException, CriterionNameNullException, CriterionAlreadyExistsException {
        for (String[] binaryArray : binaryCriteria) {
            defineBinaryCriterion(binaryArray[1], binaryArray[2], binaryArray[3], binaryArray[4]);
        }
    }

    /**
     * Processes a list of composite task specifications.
     * <p>
     * Each array in the list contains details for one composite task, which are used to define and add the task.
     * </p>
     *
     * @param compositeTasks A list of string arrays, each representing a composite task.
     * @throws TaskAlreadyExistsException If a task with the same name already exists.
     * @throws InvalidSubtaskException If the subtasks specified for a composite task are invalid.
     * @author WU Qixuan
     */
    private void processCompositeTasks(List<String[]> compositeTasks) throws TaskAlreadyExistsException, InvalidSubtaskException {
        for (String[] taskArray : compositeTasks) {
            List<String> subtaskNames = convertStringToList(taskArray[3]);
            addCompositeTask(taskArray[1], taskArray[2], subtaskNames);
        }
    }

}
