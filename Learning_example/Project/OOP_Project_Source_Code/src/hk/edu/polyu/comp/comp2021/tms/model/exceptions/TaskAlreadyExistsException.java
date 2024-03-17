package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The TaskAlreadyExistsException is a custom exception used in the Task Management System (TMS).
 * This exception is thrown to indicate that an attempt to add a new task has failed because
 * a task with the same name or identifier already exists in the system.
 */
public class TaskAlreadyExistsException extends Exception {

    /**
     * Constructs a new TaskAlreadyExistsException with a specific message.
     * The message provides more details about the reason why the exception was thrown,
     * typically indicating the task name or identifier that caused the duplication issue.
     *
     * @param message A string representing the detailed message explaining the exception.
     */
    public TaskAlreadyExistsException(String message) {
        super(message);
    }
}

