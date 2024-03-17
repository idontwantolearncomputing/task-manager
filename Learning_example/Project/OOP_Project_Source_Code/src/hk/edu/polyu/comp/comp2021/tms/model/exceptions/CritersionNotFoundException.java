package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * Exception thrown when a specified criterion is not found.
 * <p>
 * This exception is used within the Task Management System (TMS) to indicate that
 * an operation involving a criterion failed because the criterion with the specified name
 * does not exist in the system. This can occur in various scenarios, such as attempting to
 * access, modify, or delete a criterion that is not present in the current context.
 * </p>
 *
 * @author WU Qixuan
 */
public class CritersionNotFoundException extends Exception {
    /**
     * Constructs a new CritersionNotFoundException with the specified detail message.
     *
     * @param message The detail message. The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public CritersionNotFoundException(String message) {
        super(message);
    }
}
