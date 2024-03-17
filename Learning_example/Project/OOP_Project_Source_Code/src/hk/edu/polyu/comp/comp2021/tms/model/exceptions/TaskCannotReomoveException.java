package hk.edu.polyu.comp.comp2021.tms.model.exceptions;

/**
 * The TaskCannotRemoveException is a custom exception used in the Task Management System (TMS).
 * This exception is thrown to indicate that an attempt to remove a task from the system has failed.
 * This could be due to various reasons such as the task being a prerequisite for another task,
 * the task being part of a composite task, or other system-defined constraints.
 */
public class TaskCannotReomoveException extends Exception{

    /**
     * Constructs a new TaskCannotRemoveException with a specific message.
     * The message typically provides a detailed explanation of why the task cannot be removed,
     * which helps in understanding and addressing the issue.
     *
     * @param message A string representing the detailed explanation of the exception.
     */
    public TaskCannotReomoveException(String message){
        super(message);
    }

}
