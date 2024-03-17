package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The CriterionAlreadyExistsException is a custom exception used within the Task Management System (TMS).
 * It is thrown to indicate that an attempt to add a new criterion has failed because a criterion with the
 * same identifier already exists in the system.
 */
public class CriterionAlreadyExistsException extends Exception {

    /**
     * Constructs a new CriterionAlreadyExistsException with a detailed message.
     *
     * @param message A string representing the detail message (which is saved for later retrieval
     *                by the Throwable.getMessage() method).
     */
    public CriterionAlreadyExistsException(String message) {
        super(message);
    }
}
