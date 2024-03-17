package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The TaskMapIsEmpty exception is thrown when an operation in the Task Management System (TMS)
 * expects a non-empty collection of tasks, but the collection is found to be empty.
 * This might occur in scenarios like trying to list or manipulate tasks when none are present.
 */
public class TaskMapIsEmpty extends Exception {
    /**
     * Constructs a new TaskMapIsEmpty exception with a detailed message.
     * This constructor is typically used to provide an informative error message indicating
     * that a requested operation in the Task Management System (TMS) cannot proceed
     * because the collection of tasks is unexpectedly empty.
     *
     * @param message A string providing details about the specific condition that led to the exception.
     */
    public TaskMapIsEmpty(String message) {
        super(message);
    }
}
