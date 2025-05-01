package org.shedenys.timestamps;

/**
 * Represents a command interface to define operations that can be executed.
 * Classes implementing this interface should encapsulate a specific operation
 * or task and provide the implementation of the operation through the
 * {@code execute} method.
 * <p>
 * This interface is designed to follow the Command design pattern, which
 * allows the caller to encapsulate a request as an object, store, queue, or
 * log the request, and execute the request as needed.
 */
public interface CommandInterface {

    /**
     * Executes the encapsulated operation defined by the implementing class.
     * This method represents the main entry point for the action or task
     * encapsulated by the command and should be implemented to perform
     * the desired operation when called.
     */
    void execute();
}

