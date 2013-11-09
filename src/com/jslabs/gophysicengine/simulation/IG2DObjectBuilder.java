package com.jslabs.gophysicengine.simulation;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Default builder for a 2D object.</p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public interface IG2DObjectBuilder
{
    /**
     * Build a 2D object from the folowing initialization
     * parameters.
     * @param x0 double x initial position
     * @param y0 double y initial position
     * @param vx0 double x initial velocity
     * @param vy0 double y initial velocity
     * @param wx0 double x angular velovity
     * @param wy0 double y angular velovity
     * @return GObject
     */
    public GObject build(double x0, double y0, double px0, double py0,
                         double wx0, double wy0);
}
