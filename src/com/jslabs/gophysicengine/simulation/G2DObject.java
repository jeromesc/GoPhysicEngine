package com.jslabs.gophysicengine.simulation;

import com.jslabs.gophysicengine.maths.linear.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import com.jslabs.gophysicengine.renderer.GGeometricConverter;
import java.awt.geom.GeneralPath;

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
public abstract class G2DObject
        extends GObject
{
	/**
	 * This is the default constructor for a
	 * 2D object. It initializes all vectors
	 * and matrix objects contained in a
	 * 2D rigid body.
	 */
    public G2DObject()
    {
        _inertia = new GMatrix(2, 2);
        _inverseInertia = new GMatrix(2, 2);
        _l = new GVector(2);
        _q = new GQuaternion();
        _r = new GMatrix(2, 2);
        _p = new GVector(2);
        _v = new GVector(2);
        _x = new GVector(2);
        _w = new GVector(2);
    }

	/**
	 * This method has to be overloaded to specify
	 * how the object must be draw on the screen.
	 * @param g Graphics graphic object
	 */
    public abstract void drawObject(Graphics g);

	/**
	 * This method can't be overloaded. It implements
	 * the pattern/hook pattern. This method process all
	 * affine (2D) transformations and then calls the
	 * drawObject method in order to draw the 2D object.
	 * @param g Graphics The graphic object.
	 */
    public final void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        Color oldColor = g.getColor();
        // translate the object from its body referential
        // to a inertial referential.
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation((int)_iX.getValue(0), (int)_iX.getValue(1));
        // process transformation
        g2d.setTransform(transform);
        // draw object on the offscreen graphics
        drawObject(g2d);
        g.setColor(oldColor);
    }

}
