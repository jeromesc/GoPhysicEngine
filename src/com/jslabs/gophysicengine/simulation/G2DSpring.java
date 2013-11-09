package com.jslabs.gophysicengine.simulation;

import com.jslabs.gophysicengine.physics.*;
import java.awt.*;
import com.jslabs.gophysicengine.renderer.GGeometricConverter;


/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Moteur physique permettant la simulation de la dynamique des
 * corps rigides.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class G2DSpring
        extends G2DObject
{
    private double _rest;

    private double _k;

    public G2DSpring()
    {
        super();
    }

    /**
     * Draw the current object on the
     * offscreen graphics for double buffering
     * features.
     * @param g Graphics The offscreen graphic object.
     */
    public void drawObject(Graphics g)
    {
        g.setColor(Color.blue);
        // draw the spring
        g.fillRect(0, (int) ((0.5 * _height) - 5), (int) _x.getValue(0), 10);
        // draw the mass
        g.setColor(Color.red);
        g.fillRect((int) _x.getValue(0), (int) _x.getValue(1), _width, _height);
    }

	/**
	 * Define the length of the spring at rest.
	 * @param r double The length of the spring
	 */
    public void setSpringRestLength(double r)
    {
        _rest = r;
    }

	/**
	 * Return the length of the spring at rest.
	 */
    public double getSpringRestLength()
    {
        return _rest;
    }

	/**
	 * Define the force of the spring.
	 * @param k double The force in N/m
	 */
    public void setSpringForce(double k)
    {
        _k = k;
    }

	/**
	 * Return the spring force.
	 * @return double The spring force
	 */
    public double getSpringForce()
    {
        return _k;
    }


	/**
	 * This overloaded function initialize all
	 * values for the spring object. It checks
	 * if the mass, length and force are not
	 * equal to zero.
	 * @throws InitializationException If a value
	 * is equal to zero.
	 */
    public void initializeParameters()
            throws InitializationException
    {
        if (_mass == 0)
        {
            throw new InitializationException(
                    "G2DSpring : Mass must be defined.");
        }
        if (getSpringRestLength() == 0)
        {
            throw new InitializationException(
                    "G2DSpring : Spring length at rest must be more than zero.");
        }
        if (getSpringForce() <= 0)
        {
            throw new InitializationException(
                    "G2DSpring : Spring force must be more than zero.");
        }
    }
}
