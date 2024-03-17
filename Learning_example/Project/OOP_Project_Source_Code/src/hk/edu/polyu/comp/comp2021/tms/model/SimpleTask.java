package hk.edu.polyu.comp.comp2021.tms.model;
import java.util.*;

/**
 * The SimpleTask class represents a basic task in the Task Management System (TMS).
 * This class extends the Task class and includes additional properties specific to a simple task,
 * such as its duration and a list of prerequisite tasks. A simple task is a basic unit of work
 * with a defined duration and potentially dependent on the completion of other tasks.
 * {@code @auther} CHEN Chi-wei
 */
public class SimpleTask extends Task {

    /**
     * Constructs a new SimpleTask with the specified name, description, duration, and list of prerequisites.
     * The duration represents the time required to complete the task, while the prerequisites are a list of tasks
     * that must be completed before this task can start.
     *
     * @param name          The name of the task.
     * @param description   A brief description of the task.
     * @param duration      The duration of the task, typically in hours or other time units.
     * @param prerequisites A list of tasks that are prerequisites for this task.
     * {@code @auther} CHEN Chi-wei
     */
    public SimpleTask(String name, String description, double duration, List<Task> prerequisites) {
        super(name, description, prerequisites);
        this.duration = duration;
        this.prerequisiteOf = new ArrayList<>();
    }

    @Override
    public void setSubtasks(List<Task> subtasks) {

    }
}



