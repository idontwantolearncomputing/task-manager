package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The InvalidSubtaskException is a custom exception used in the Task Management System (TMS).
 * This exception is thrown to indicate that an operation has encountered an invalid subtask.
 * This could occur in scenarios where a subtask does not exist, is not valid for the operation being
 * performed, or does not meet certain criteria required for it to be considered a valid subtask.
 */
public class InvalidSubtaskException extends Exception {

    /**
     * Constructs a new InvalidSubtaskException with a specific message.
     * The message typically provides a detailed explanation of why the subtask is considered invalid,
     * which helps in identifying and resolving the issue.
     *
     * @param message A string representing the detailed explanation of the exception.
     */
    public InvalidSubtaskException(String message) {
        super(message);
    }
}

