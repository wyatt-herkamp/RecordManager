package dev.kingtux.recordmanager;

/**
 * Thrown when loading a Record and a property is missing
 */
public class MissingPropertyException extends Exception {
    /**
     * The missing property
     * @param property the missing property
     */
    public MissingPropertyException(String property) {
        super("Missing property " + property);
    }
}
