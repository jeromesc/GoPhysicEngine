package com.jslabs.gophysicengine.physics;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: This class represent an implementation of an exception
 * occuring when the initialization of a rigid body fails.</p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class InitializationException
        extends Exception
{
    // default message.
    private String _msg = new String();

    /**
     * Return the error message.
     * @return String
     */
    public String getMessage()
    {
        return _msg;
    }

    /**
     * Default constructor of this class.
     * @param message String
     */
    public InitializationException(String message)
    {
        _msg = message;
    }
}
