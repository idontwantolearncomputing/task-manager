package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The InvalidPropertyException is a custom exception used in the Task Management System (TMS).
 * This exception is thrown to indicate that an operation has encountered an invalid property.
 * This might occur if a property provided for a task or a criterion does not meet the required
 * criteria or is not recognized by the system.
 */
public class InvalidPropertyException extends Exception {

    /**
     * Constructs a new InvalidPropertyException with a specific message.
     * The message typically provides a detailed explanation of why the property is considered invalid,
     * helping to identify the source of the problem.
     *
     * @param message A string representing the detailed explanation of the exception.
     */
    public InvalidPropertyException(String message) {
        super(message);
    }
}

