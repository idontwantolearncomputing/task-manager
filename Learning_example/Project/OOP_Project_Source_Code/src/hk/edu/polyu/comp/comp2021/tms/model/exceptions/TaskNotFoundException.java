package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The TaskNotFoundException is a custom exception used in the Task Management System (TMS).
 * This exception is thrown to indicate that the specified task could not be found within the system.
 * It is typically thrown when an operation is attempted on a task that does not exist or has been deleted.
 */
public class TaskNotFoundException extends Exception {

    /**
     * Constructs a new TaskNotFoundException with a detailed message.
     * The message typically describes which task could not be found and the context in which this issue occurred.
     *
     * @param message A string representing the detailed explanation of why the task was not found.
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}
