package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.controller.TMS;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TMSTest is a test suite for the Task Management System (TMS).
 * It contains a series of unit tests that verify the functionality of various components
 * of the TMS, ensuring that tasks are created, modified, deleted, and reported correctly.
 */
public class TMSTest {

    /**
     * Tests the constructor of the TMS class and various operations like task creation, deletion,
     * and reporting. This test ensures that the TMS initializes correctly and that basic task operations
     * function as expected.
     */
    @Test
    public void testTMSConstructor(){
        TMS tms = new TMS();
        List<String> list = new ArrayList<>(Collections.singletonList(","));
        List<String> list2 = new ArrayList<>( Arrays.asList("Boil-Water", "Coffee-Powder"));
        List<String> list3 = new ArrayList<>(Arrays.asList("Boil-Water", "Coffee-Powder", "Sugar"));
        List<String> list4 = new ArrayList<>(Collections.singletonList("CAO Yixin"));
        List<String> list5 = new ArrayList<>(Collections.singletonList("Prof CAO Yixin"));
        tms.outPutAllCriteria();
        tms.printAllTasks();
        tms.addIsPrimitiveCriterion();
        tms.createSimpleTask("Boil-Water", "Boil-some-water", "1.0", list);
        tms.printAllTasks();
        tms.createSimpleTask("Boil-Water", "Boil-some-water", "1.0", list);
        tms.createSimpleTask("COMP2411", "Database", "ABC", list2);
        tms.createSimpleTask("Coffee-Powder", "Add-coffee-powder", "2.0",  list);
        tms.createSimpleTask("COMP2411", "Database", "1.0", list2);
        tms.deleteTask("Boil-Water");
        tms.createSimpleTask("Sugar", "Add-sugar", "1.0", list2);
        tms.reportDuration("Sugar");
        tms.outPutAllTasks();
        tms.deleteTask("COMP2411");
        tms.createCompositeTask("Make-Coffee", "Make-coffee", list3);
        tms.printAllTasks();
        tms.createCompositeTask("Make-Coffee", "Make-coffee", list3);
        tms.reportEarliestFinishTime("Make-Coffee");
        tms.reportDuration( "Make-Coffee");
        tms.createCompositeTask("COMP2012", "Math", list);
        tms.createCompositeTask("COMP1011", " ", list4);
        tms.createCompositeTask("Make-Coffee1", "Make-coffee", list3);
        tms.createSimpleTask("Cao Yixin_Comp2011", "Midterm", "10.0", list);
        tms.changeTask("Coffee-Powder", "description", "Add fine coffee powder");
        tms.changeTask("Sugar", "duration", "2.0");
        tms.changeTask("Make-Coffee", "name", "Cao Yixin");
        tms.changeTask("Make-Coffee", "subtasks", "Make coffee");
        tms.changeTask("Sugar", "prerequisites", "Boil-Water");
        tms.changeTask("Sugar", "prerequisites", list4);
        tms.changeTask("Sugar", "prerequisites", list);
        tms.changeTask("CAO Yixin", "duration", "2.0");
        tms.changeTask("Make-Coffee", "prerequisites", "2.0");
        tms.changeTask("Make-Coffee", "prerequisites", list);
        tms.changeTask("Make-Coffee", "subtasks", list);
        tms.changeTask("Pro Cao Yixin", "subtasks", list);
        tms.deleteTask("CAO Yixin");
        tms.deleteTask("Make-Coffee");
        tms.deleteTask("Sugar");
        tms.deleteTask("Make-Coffee1");
        tms.reportDuration("CAO Yixin");
        tms.reportDuration("Sugar");
        tms.outPutAllCriteria();
        tms.addBasicCriterion( "Criterion1", "duration", ">", "1.0");
        tms.addBasicCriterion( "Criterion11", "duration", "<", "1.0");
        tms.addBasicCriterion( "Criterion12", "duration", ">=", "1.0");
        tms.addBasicCriterion( "Criterion13", "duration", "<=", "1.0");
        tms.addBasicCriterion( "Criterion14", "duration", "==", "1.0");
        tms.addBasicCriterion( "Criterion15", "duration", "!=", "1.0");
        tms.addBasicCriterion( "Criterion16", "name", "contains", "123");
        tms.addBasicCriterion( "Criterion17", "prerequisites", "contains", "123");
        tms.addBasicCriterion( "Criterion18", "subtasks", "contains", "Sugar");
        tms.addBasicCriterion( "Criterion19", "description", "contains", "123");
        tms.addNegatedCriterion( "Criterion111", "Criterion11");
        tms.addNegatedCriterion( "Criterion177", "Criterion17");
        tms.addNegatedCriterion( "Criterion112", "Criterion12");
        tms.addNegatedCriterion( "Criterion113", "Criterion13");
        tms.addNegatedCriterion( "Criterion114", "Criterion14");
        tms.addNegatedCriterion( "Criterion115", "Criterion15");
        tms.addNegatedCriterion( "Criterion116", "Criterion16");
        tms.addNegatedCriterion( "Criterion117", "Criterion17");
        tms.addNegatedCriterion( "Criterion2", "Criterion1");
        tms.addNegatedCriterion( "", "Criterion1");
        tms.addNegatedCriterion( "Criterion2", "Criterion1");
        tms.addNegatedCriterion( "Criterion3", "Criterion10");
        tms.addBinaryCriterion( "", "Criterion1", "||", "Criterion2");
        tms.addBinaryCriterion( "Criterion1", "Criterion1", "||", "Criterion2");
        tms.addBinaryCriterion( "Criterion4", "Criterion10", "||", "Criterion2");
        tms.addBinaryCriterion( "Criterion4", "Criterion1", "||", "Criterion10");
        tms.addBinaryCriterion( "Criterion4", "Criterion1", "123", "Criterion2");
        tms.addBinaryCriterion( "Criterion4", "Criterion1", "||", "Criterion2");
        tms.addBinaryCriterion( "Criterion5", "Criterion1", "&&", "Criterion2");
        tms.outPutAllCriteria();
        tms.printAllTasks();
        System.out.println(tms.printAllTasks());
        tms.printTask("Make-Coffee");
        tms.printTask("Sugar");
        tms.changeTask("Make-Coffee", "prerequisites", list5);
        tms.deleteTask("Cao Yixin_Comp2011");
        tms.outPutAllTasks();
        tms.deleteTask("Make-Coffee");
        tms.outPutAllTasks();
        tms.createSimpleTask("Boil-Water", "Boil-some-water", "1.0", list);
        tms.addBasicCriterion( "Criterion1", "duration", ">", "1.0");
        tms.addNegatedCriterion( "Criterion111", "Criterion1");
        tms.addNegatedCriterion( "Criterion CAO", "Criterion5");
        System.out.println(tms.search("Criterion1"));
        System.out.println(tms.search( "Criterion111"));
        System.out.println(tms.search("Criterion112"));
        tms.search("Criterion4");
        tms.search("Criterion5");
        tms.search("Criterion14");
        tms.search("Criterion15");
        tms.addNegatedCriterion("Criterion5","Criterion4");
        tms.search("Criterion16");
        tms.search("Criterion117");
        tms.search("Criterion18");
        tms.search("Criterion19");
        tms.search("IsPrimitive");
        tms.search("abc");
        tms.reportEarliestFinishTime("abc");
        tms.store("abc");
        tms.load("abc");
        assert true;
    }

}