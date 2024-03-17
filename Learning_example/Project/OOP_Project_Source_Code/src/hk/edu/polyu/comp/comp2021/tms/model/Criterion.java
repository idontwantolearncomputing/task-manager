package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * The Criterion class represents a criterion for conditional evaluation.
 * This class supports basic condition checks, negated conditions, and binary logical operations like AND OR.
 */
public class Criterion {
    private String property;
    private String operation;
    private Object value;
    private String type;

    private String propertyType;

    private Criterion firstCriterion;

    private Criterion secondCriterion;

    private String logicOp; // "&&" or "||"

    /**
     * Constructs a basic criterion.
     *
     * @param property   The name of the property.
     * @param operation  The operation to be applied to the property. (name, description, duration, or prerequisites)
     * @param value      The value associated with the property and operation.
     * @param type       The type of the Criterion.
     */
    public Criterion(String property, String operation, Object value, String type) {
        this.property = property;
        this.operation = operation;
        this.value = value;
        this.type = "basic";
        this.propertyType = type;
    }

    /**
     * Constructor for creating a negated criterion based on another criterion.
     *
     * @param negatedCriterion The criterion to be negated.
     */
    public Criterion(Criterion negatedCriterion) {
        this.property = negatedCriterion.getProperty();
        this.operation = negatedCriterion.getOperation();
        this.value = negatedCriterion.getValue();
        this.type = "negated";
        this.propertyType = negatedCriterion.getPropertyType();
    }

    /**
     * Constructor for creating a binary criterion combining two criteria with a logical operator.
     *
     * @param firstCriterion   The first criterion.
     * @param logicOp          The logical operator ("&&" for AND, "||" for OR).
     * @param secondCriterion  The second criterion.
     */
    public Criterion(Criterion firstCriterion, String logicOp, Criterion secondCriterion) {
        this.firstCriterion = firstCriterion;
        this.logicOp = logicOp;
        this.secondCriterion = secondCriterion;
        this.type = "binary";
    }

    /**
     * Returns the property name associated with this criterion.
     *
     * @return The property name.
     */
    public String getProperty() {
        return property;
    }

    /**
     * Gets the value associated with the criterion.
     *
     * @return The value of the criterion.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value for this criterion.
     *
     * @param value The value to be set for the criterion.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Returns the operation associated with this criterion.
     *
     * @return The operation as a string.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the operation for this criterion.
     *
     * @param operation The operation to be set.
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Returns the type of this criterion.
     * This can be "basic", "negated", or "binary".
     *
     * @return The type of the criterion.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of this criterion.
     * <p>
     * The type can be one of several predefined values, such as "basic", "negated", or "binary",
     * representing different kinds of criteria. This method allows changing the type of this criterion
     * to reflect its current role or functionality within the system.
     * </p>
     * @author WU Qixuan
     * @param type The new type to be set for this criterion. Expected to be a valid type string.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the property type associated with this criterion.
     *
     * @return The property type as a string.
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * Returns the logical operator used in this criterion.
     * This is relevant for binary criteria and can be "&&" (AND) or "||" (OR).
     *
     * @return The logical operator as a string.
     */
    public String getLogicOp() {
        return logicOp;
    }

    /**
     * Returns the first criterion used in this criterion.
     * This is relevant for binary criteria
     *
     * @return The first criterion as a Criterion.
     */
    public Criterion getFirstCriterion() {
        return firstCriterion;
    }

    /**
     * Returns the second criterion used in this criterion.
     * This is relevant for binary criteria
     *
     * @return The second criterion as a Criterion.
     */
    public Criterion getSecondCriterion() {
        return secondCriterion;
    }

    /**
     * Returns a string representation of the criterion.
     * The format of the string depends on the type of criterion (basic, negated, binary).
     *
     * @return A string representation of the criterion.
     */
    @Override
    public String toString() {
        switch (type) {
            case "basic":
            case "negated":
                return property + " " + operation + " " + value;
            case "binary":
                return "(" + firstCriterion.toString() + ") " + logicOp + " (" + secondCriterion.toString() + ")";
        }
        return null;
    }
}