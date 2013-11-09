package com.jslabs.gophysicengine.simulation;

import com.jslabs.gophysicengine.maths.linear.*;
import com.jslabs.gophysicengine.physics.*;

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
public class G2DSpringBuilder
        implements IG2DObjectBuilder
{
    // object created
    private G2DSpring _spring;

    // initial state
    private GVector _initialX;
    private GVector _initialP;
    private GVector _initialL;
    private GQuaternion _initialQ;

////////////////////////////////////////////////////////////////////////////////

    class ForceFunction
            implements IGFunction
    {
        /**
         * Apply a force or torque user defined function at time t with the
         * different parameters. Function must return a vector.
         * @param t double
         * @param position GVector
         * @param qOrientation GQuaternion
         * @param linearMomentum GVector
         * @param angularMomentum GVector
         * @param mOrientation GMatrix
         * @param linearVelocity GVector
         * @param angularVelocity GVector
         * @return GVector
         */
        public GVector apply(double t, GVector position,
                             GQuaternion qOrientation,
                             GVector linearMomentum,
                             GVector angularMomentum,
                             GMatrix mOrientation,
                             GVector linearVelocity,
                             GVector angularVelocity)
        {
            GVector force = new GVector(2);
		   /**
		    *
		    */
            if (position.getValue(0) < _initialX.getValue(0))
            {
                position.setValue(0, _initialX.getValue(0));
            }

            force.setValue(0,
                           -_spring.getSpringForce() *
                           (position.getValue(0) - _initialX.getValue(0) -
                            _spring.getSpringRestLength()));
            return force;
        }
    }


    class TorqueFunction implements IGFunction
    {
        /**
         * Apply a force or torque user defined function at time t with the
         * different parameters. Function must return a vector.
         * @param t double
         * @param position GVector
         * @param qOrientation GQuaternion
         * @param linearMomentum GVector
         * @param angularMomentum GVector
         * @param mOrientation GMatrix
         * @param linearVelocity GVector
         * @param angularVelocity GVector
         * @return GVector
         */
        public GVector apply(double t, GVector position,
                             GQuaternion qOrientation,
                             GVector linearMomentum,
                             GVector angularMomentum,
                             GMatrix mOrientation,
                             GVector linearVelocity,
                             GVector angularVelocity)
        {
            GVector torque = new GVector(2);
            return torque;
        }
    }


    /**
     * Create a new 2D spring object and initialize all state
     * values and torque and force function.
     * @param x0 double
     * @param y0 double
     * @param px0 double
     * @param py0 double
     * @param lx0 double
     * @param ly0 double
     * @return GObject
     */
    public GObject build(double x0, double y0, double px0, double py0,
                         double lx0, double ly0)
    {
        // object created
        _spring = new G2DSpring();

        // initial state
        _initialX = new GVector(2);
        _initialP = new GVector(2);
        _initialL = new GVector(2);
        _initialQ = new GQuaternion();

        // set initial state
        _initialX.setValue(0, x0);
        _initialX.setValue(1, y0);
        _initialP.setValue(0, px0);
        _initialP.setValue(1, py0);
        _initialL.setValue(0, lx0);
        _initialL.setValue(1, ly0);

        // set parameters
        _spring.setState(_initialX, _initialQ, _initialP, _initialL);
        // set torque and force
        _spring.setForceFunction(new ForceFunction());
        _spring.setTorqueFunction(new TorqueFunction());
        return _spring;
    }
}
