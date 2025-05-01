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

    void execute();
}

