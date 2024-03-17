package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The CriterionNameNullException is a custom exception used in the Task Management System (TMS).
 * This exception is thrown to indicate that an operation involving a criterion failed due to the
 * absence of a criterion name or when the criterion name is null.
 */
public class CriterionNameNullException extends Exception {

    /**
     * Constructs a new CriterionNameNullException with a specific message.
     * The message provides more details about the reason why the exception was thrown.
     *
     * @param message A string representing the detailed message explaining the exception.
     */
    public CriterionNameNullException(String message) {
        super(message);
    }
}

