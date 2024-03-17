package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.*;

/**
 * class of the task
 * {@code @auther} CHEN Chi-wei
 */
public abstract class Task {
    /**
     * Check if the task is a composite task
     */
    protected boolean isComposite = false;
    /**
     * Check if the task is a subtask
     */
    protected boolean isSubtask = false;
    /**
     * Name of the task
     */
    protected String name;
    /**
     * Description of the task
     */
    protected String description;
    /**
     * Duration of the task
     */
    protected double duration;
    /**
     * List of prerequisites of the task
     */
    protected List<Task> prerequisites;
    /**
     * List of tasks that are prerequisite of this task
     */
    protected List<Task> prerequisiteOf;
    // --Commented out by Inspection (2023/11/22, 12:55 PM):protected final String parentTask;

    /**
     * Constructor of the task
     * @param name name of the task
     * @param description description of the task
     * @param prerequisites List of prerequisites of the task
     * {@code @auther} CHEN Chi-wei
     */
    public Task(String name, String description, List<Task> prerequisites) {
        this.name = name;
        this.description = description;
        this.prerequisites = new ArrayList<>(prerequisites);
        //this.parentTask = null;
    }

    /**
     * Get the name of the task
     * @return name of the task
     * {@code @auther} CHEN Chi-wei
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the task
     * @return description of the task
     * {@code @auther} CHEN Chi-wei
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the tasks that are prerequisite of this task
     * @return List of tasks that are prerequisite of this task
     * {@code @auther} CHEN Chi-wei
     */
    public List<String> getPrerequisites() {
        List<String> prerequisiteNames = new ArrayList<>();
        for (Task prerequisite : this.prerequisites) {
            prerequisiteNames.add(prerequisite.getName());
        }
        return prerequisiteNames;
    }

    /**
     * Set the tasks that are prerequisite of this task
     * @param name name of the task
     * {@code @auther} CHEN Chi-wei
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description of the task
     * @param description description of the task
     * {@code @auther} CHEN Chi-wei
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the prerequisites of the task
     * @param prerequisites List of prerequisites of the task
     * {@code @auther} CHEN Chi-wei
     */
    public void setPrerequisites(List<Task> prerequisites) {
        this.prerequisites = new ArrayList<>(prerequisites);
    }

    /**
     * Get the subtasks of the task
     * @return List of subtasks of the task
     * {@code @auther} CHEN Chi-wei
     */
    public List<String> getSubtasks() {
        return null;
    }


    /**
     * Set the duration of the task
     * @param duration duration of the task
     * {@code @auther} CHEN Chi-wei
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Get the duration of the task
     * @return duration of the task
     * {@code @auther} CHEN Chi-wei
     */
    public double getDuration() {
        return duration;
    }


    /**
     * Check if the task is a prerequisite of another task
     * @return true if the task is a prerequisite of another task
     * {@code @auther} CHEN Chi-wei
     */
    public boolean isPrerequisite() {
        return !(this.prerequisiteOf == null || this.prerequisiteOf.isEmpty());
    }

    /**
     * Get the task as a composite task
     * @return true if the task is a composite task
     * {@code @auther} CHEN Chi-wei
     */
    public boolean getIsComposite() {
        return isComposite;
    }

    /**
     * Set the task as a composite task
     * @param isComposite true if the task is a composite task
     * {@code @auther} CHEN Chi-wei
     */
    public void setIsComposite(boolean isComposite) {
        this.isComposite = isComposite;
    }

    /**
     * Get the task as a subtask
     * @return true if the task is a subtask
     * {@code @auther} CHEN Chi-wei
     */
    public boolean getIsSubtask() {
        return isSubtask;
    }

    /**
     * Set the task as a subtask
     * @param isSubtask true if the task is a subtask
     * {@code @auther} CHEN Chi-wei
     */
    public void setIsSubtask(boolean isSubtask) {
        this.isSubtask = isSubtask;
    }

    /**
     * Get the tasks that are prerequisite of this task
     * @return List of tasks that are prerequisite of this task
     * {@code @auther} CHEN Chi-wei
     */
    public List<Task> getPrerequisiteOf() {
        return prerequisiteOf;
    }

    /**
     * Sets the list of subtasks for this task.
     * <p>
     * This method replaces the current list of subtasks with the provided list.
     * It should be used to update the structure of a composite task, where each subtask
     * represents a part of the overall task. Ensure that the list is not null and does
     * not contain any null elements. Depending on the implementation, this method may also
     * trigger updates or notifications if the list of subtasks changes.
     * </p>
     *
     * @param subtasks The list of subtasks to be set for this task. This list should be non-null and
     *                 may be empty if the task does not have any subtasks. Each element in the list
     *                 should be a valid {@link Task} object.
     * @throws NullPointerException if the subtasks list is null or contains null elements.
     * @see Task
     */
    public abstract void setSubtasks(List<Task> subtasks);
}
