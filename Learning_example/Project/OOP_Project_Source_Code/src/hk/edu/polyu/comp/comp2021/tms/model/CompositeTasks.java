package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.*;

/**
 * The CompositeTasks class extends the Task class to represent a composite task.
 * A composite task is a task that consists of multiple subtasks. This class allows
 * for the creation and management of such composite tasks, including adding,
 * retrieving, and modifying its subtasks.
 * {@code @auther} CHEN Chi-wei
 */
public class CompositeTasks extends Task {
    private List<Task> subtasks;

    /**
     * Constructs a new CompositeTasks object with the specified name, description, and list of subtasks.
     *
     * @param name        The name of the composite task.
     * @param description The description of the composite task.
     * @param subtasks    A list of subtasks that make up this composite task.
     * {@code @auther} CHEN Chi-wei
     */
    public CompositeTasks(String name, String description, List<Task> subtasks) {
        super(name, description, new ArrayList<>());
        this.subtasks = new ArrayList<>(subtasks);
    }

    /**
     * Retrieves the names of all the subtasks in this composite task.
     *
     * @return A list of names of the subtasks.
     * {@code @auther} CHEN Chi-wei
     */
    @Override
    public List<String> getSubtasks() {
        List<String> subtaskNames = new ArrayList<>();
        for (Task subtask : subtasks){
            subtaskNames.add(subtask.getName());
        }
        return subtaskNames;
    }


    /**
     * Sets or replaces the list of subtasks for this composite task.
     *
     * @param subtasks A list of Task objects to be set as subtasks of this composite task.
     * {@code @auther} CHEN Chi-wei
     */
    @Override
    public void setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
    }
}
