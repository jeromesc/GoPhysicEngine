package com.jslabs.gophysicengine.simulation;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Moteur physique permettant la simulation de la dynamique des
 * corps rigides.</p>
 * <p>This exception is thrown whenever a scene contains objects that cannot be 
   intialized correctly. Initialization procedure assigns mass, velocity and inertia.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class CompilationException
        extends Exception
{
	/**
	 * Message sentence of this exception.
	 */
    private String _msg;

	/**
	 * Return the message to the user.
	 */
    public String getMessage()
    {
        return _msg;
    }

	/**
	 * Default constructor of this class.
	 */
    public CompilationException(String message)
    {
        _msg = message;
    }
}
