package com.jslabs.gophysicengine.physics;

import com.jslabs.gophysicengine.maths.linear.*;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: This interface must be implemented by a class to
 * define the behaviour of a force or a torque. The function returns
 * a vector indicating the coefficient of the force depending on time.
 * </p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public interface IGFunction
{
    /**
     * Apply a force or torque user defined function at time t with the
     * different parameters. Function must return a vector.
     * @param t double
     * @param point GVector
     * @param qOrientation GQuaternion
     * @param linearMomentum GVector
     * @param angularMomentum GVector
     * @param mOrientation GMatrix
     * @param linearVelocity GMatrix
     * @param angularVelocity GVector
     * @return GVector
     */
    public GVector apply(double t, GVector point, GQuaternion qOrientation,
                         GVector linearMomentum, GVector angularMomentum,
                         GMatrix mOrientation, GVector linearVelocity,
                         GVector angularVelocity);
}
