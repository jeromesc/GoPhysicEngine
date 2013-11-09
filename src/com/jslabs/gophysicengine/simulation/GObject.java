package com.jslabs.gophysicengine.simulation;

import com.jslabs.gophysicengine.physics.*;
import java.awt.Graphics;
import com.jslabs.gophysicengine.maths.linear.GVector;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Moteur physique permettant la simulation de la dynamique des
 * corps rigides.</p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public abstract class GObject
        extends GRigidBody
{
    /**
     * Position of the object in the inertial referential.
     */
    protected GVector _iX = new GVector(3);

    /**
     * Dimensions of the object.
     */
    protected int _width, _height, _depth;

////////////////////////////////////////////////////////////////////////////////

    /**
     * Draw method that will be implemented by each
     * object in order to display the rigid body.
     * @param hdc Object
     */
    public abstract void draw(Graphics g);

    /**
     * Define the position of the object relative
     * to the origin.
     * @param x int
     * @param y int
     * @param z int
     */
    public void setInertialReferentialPosition(double x, double y, double z)
    {
        _iX.setValue(0, x);
        _iX.setValue(1, y);
        _iX.setValue(2, z);
    }

    /**
     * Define the dimension of the object in 3 dimensions.
     * @param width int The width of the object
     * @param height int It's height
     * @param depth int And its depth
     */
    public void setDimensions(int width, int height, int depth)
    {
        _width = width;
        _height = height;
        _depth = depth;
    }

    /**
     * Initialize all parameters of the object.
     * @throws InitializationException
     */
    public abstract void initializeParameters()
            throws InitializationException;

    /**
     * Initialize method implements the
     * template-hook pattern.
     * @throws InitializationException
     */
    public final void initialize()
            throws InitializationException
    {
        if (getForceFunction() == null)
        {
            throw new InitializationException("Force function must be defined.");
        }
        if (getTorqueFunction() == null)
        {
            throw new InitializationException(
                    "Torque function must be defined.");
        }
        // call the hook
        initializeParameters();
        // initialize final rigid body.
        initializeRigidBody();
    }


}
