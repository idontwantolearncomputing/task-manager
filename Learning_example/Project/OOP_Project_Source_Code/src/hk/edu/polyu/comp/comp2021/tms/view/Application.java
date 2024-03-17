package hk.edu.polyu.comp.comp2021.tms.view;
import hk.edu.polyu.comp.comp2021.tms.controller.TMS;

import java.util.*;

/**
 * The Application class serves as the entry point for the Task Management System (TMS).
 * This class contains the main method which initializes the system and handles user input
 * to perform various task management operations.
 */
public class Application {

    /**
     * The main method is the entry point of the application.
     * It creates an instance of the TMS, displays a welcome message, and then enters a loop
     * to process user commands. It supports various commands for managing tasks, such as
     * creating, deleting, and modifying tasks, as well as reporting and storing/loading system state.
     *
     * @param args The command line arguments passed to the application (not used).
     * {@code @auther} CHEN Chi-wei
     */
    public static void main(String[] args) {

        TMS tms = new TMS();
        tms.addIsPrimitiveCriterion();
        // Initialize and run the system

        System.out.println("Wassup, welcome to the Task Management System!");

        while (true) {
            Scanner input = new Scanner(System.in);

            System.out.println("Enter command(quit to exit):");

            // String input
            String command = input.nextLine();

            if (command.equalsIgnoreCase("quit")) {
                System.out.println("Exiting...");
                break;
            }

            // Split the string by space
            String[] commandlist = command.split(" ");

            //make the first letter of the command lowercase
            commandlist[0] = commandlist[0].toLowerCase();

            try {
                //switch case for different commands
                switch (commandlist[0]) {
                    case ("createsimpletask"): {
                        if(commandlist.length < 5){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        // make a list of prerequisites
                        List<String> prerequisites = new ArrayList<>(Arrays.asList(commandlist).subList(4, commandlist.length));
                        //create the task
                        System.out.println(tms.createSimpleTask(commandlist[1], commandlist[2], commandlist[3], prerequisites));
                        break;
                    }
                    case ("createcompositetask"): {
                        if(commandlist.length < 5){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        // make a list of subtasks
                        List<String> subtasks = new ArrayList<>(Arrays.asList(commandlist).subList(3, commandlist.length));
                        //create the task
                        System.out.println(tms.createCompositeTask(commandlist[1], commandlist[2], subtasks));
                        break;
                    }
                    case ("deletetask"): {
                        if(commandlist.length != 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.deleteTask(commandlist[1]));
                        break;
                    }
                    case ("changetask"): {
                        if(commandlist.length < 4){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        if(commandlist[2].equalsIgnoreCase("prerequisites")){
                            List<String> prerequisites = new ArrayList<>(Arrays.asList(commandlist).subList(3, commandlist.length));
                            System.out.println(tms.changeTask(commandlist[1], commandlist[2], prerequisites));
                            break;
                        }
                        if(commandlist[2].equalsIgnoreCase("subtasks")){
                            List<String> subtasks = new ArrayList<>(Arrays.asList(commandlist).subList(3, commandlist.length));
                            System.out.println(tms.changeTask(commandlist[1], commandlist[2], subtasks));
                            break;
                        }
                        System.out.println(tms.changeTask(commandlist[1], commandlist[2], commandlist[3]));
                        break;
                    }
                    case ("print"): {
                        if(commandlist.length != 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.printTask(commandlist[1]));
                        break;
                    }
                    case ("printalltasks"): {
                        if(commandlist.length != 1){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.printAllTasks());
                        break;
                    }
                    case ("reportduration"): {
                        if(commandlist.length < 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.reportDuration(commandlist[1]));
                        break;
                    }
                    case ("reportearliestfinishtime"): {
                        if(commandlist.length != 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.reportEarliestFinishTime(commandlist[1]));
                        break;
                    }
                    case ("Search"):{
                        if(commandlist.length != 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.search(commandlist[1]));
                    }
                    case ("definebasiccriterion"): {
                        if(commandlist.length < 5){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.addBasicCriterion(commandlist[1], commandlist[2], commandlist[3], commandlist[4]));
                        break;
                    }
                    case ("definenegatedcriterion"): {
                        if(commandlist.length < 3){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.addNegatedCriterion(commandlist[1], commandlist[2]));
                        break;
                    }
                    case ("definebinarycriterion"): {
                        if(commandlist.length < 5){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.addBinaryCriterion(commandlist[1], commandlist[2], commandlist[3], commandlist[4]));
                        break;
                    }
                    case ("search"): {
                        if(commandlist.length != 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.search(commandlist[1]));
                        break;
                    }
                    case ("printallcriteria"): {
                        if(commandlist.length != 1){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.outPutAllCriteria());
                        break;
                    }
                    case ("store"): {
                        if(commandlist.length != 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.store(commandlist[1]));
                        break;
                    }
                    case ("load"): {
                        if(commandlist.length != 2){
                            throw new IndexOutOfBoundsException("Invalid number of arguments.");
                        }
                        System.out.println(tms.load(commandlist[1]));
                        break;
                    }

                    case "help": {
                        System.out.println("Available Commands:");
                        System.out.println("createSimpleTask <name> <description> <duration> <prerequisites>: Creates a new simple task with the specified name, description, duration (in hours), and a list of prerequisite task names, separated by spaces.");
                        System.out.println("createCompositeTask <name> <description> <subtasks>: Creates a new composite task with the specified name, description, and a list of subtask names, separated by spaces.");
                        System.out.println("deleteTask <name>: Deletes the task with the specified name.");
                        System.out.println("changeTask <name> <property> <value>: Changes the specified property (e.g., 'description', 'duration') of the task with the given name to the new value provided.");
                        System.out.println("print <name>: Prints details of the task with the specified name.");
                        System.out.println("printAllTasks: Prints the names of all the tasks currently managed by the system.");
                        System.out.println("reportDuration <name>: Reports the total duration of the task with the specified name, including the durations of its prerequisites if it's a simple task, or the sum of durations of all its subtasks if it's a composite task.");
                        System.out.println("reportEarliestFinishTime <name>: Reports the earliest finish time for the task with the specified name based on its prerequisites and their durations.");
                        System.out.println("defineBasicCriterion <name> <property> <operator> <value>: Defines a basic search criterion with the specified name that compares the specified property (e.g., 'duration', 'name') of tasks to the given value using the provided operator (e.g., '>', '<', '=', 'contains').");
                        System.out.println("defineNegatedCriterion <name> <criterion>: Defines a new criterion that negates the result of an existing criterion specified by name.");
                        System.out.println("defineBinaryCriterion <name> <criterion1> <operator> <criterion2>: Defines a new criterion that combines two existing criteria with a logical operator (e.g., 'AND', 'OR').");
                        System.out.println("printAllCriteria: Prints all defined search criteria.");
                        System.out.println("store <filename>: Stores the current state of the task management system to a file with the specified filename.");
                        System.out.println("load <filename>: Loads the state of the task management system from a file with the specified filename.");
                        System.out.println("quit: Exits the Task Management System.");
                        break;
                    }
                    case "quit": {
                        return;
                    }
                    default: {
                        System.out.println("Invalid command.");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: " + e.getMessage());

            }
        }
    }
}
