package dev.kingtux.recordmanager;

public class MissingPropertyException extends Exception{
    public MissingPropertyException(String property) {
        super("Missing property " + property);
    }
}
