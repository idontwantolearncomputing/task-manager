package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The InvalidPrerequisiteException is a custom exception used in the Task Management System (TMS).
 * This exception is thrown to indicate that an operation has encountered an issue with a task's prerequisite,
 * such as when a prerequisite task does not exist or is not valid in the given context.
 */
public class InvalidPrerequisiteException extends Exception {

    /**
     * Constructs a new InvalidPrerequisiteException with a detailed message.
     * This message typically provides insight into why the exception was thrown,
     * such as specifying what aspect of the prerequisite was invalid.
     *
     * @param message A string representing the detailed message explaining the exception.
     */
    public InvalidPrerequisiteException(String message) {
        super(message);
    }
}

