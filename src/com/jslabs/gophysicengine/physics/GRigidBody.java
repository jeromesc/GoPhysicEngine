package com.jslabs.gophysicengine.physics;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: This class encapsulates all required values and parameters
 * to describe a rigid body. It contains information about the current position,
 * velocity and orientation of the body. Also the update() function is used to
 * approximate the differential equation that dictates the bahaviour of the
 * body. The update() function uses the Runge Kutta (fourth order) to
 * approximate the differential equation at a specific time for a specific time
 * delta. Efforts have been made to ensure a compatibility between the second (2)
 * and third (3) dimensions.</p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */

import com.jslabs.gophysicengine.maths.linear.*;

public class GRigidBody
{
    // constant quantities
    // the mass is excepted to be in kg.
    protected double _mass;
    // 1 / _mass
    // the inverse mass is used often in some calculations.
    protected double _inverseMass;
    // matrix of inertia
    protected GMatrix _inertia;
    // inverse matrix
    protected GMatrix _inverseInertia;
    // position
    // define the position of the center of the mass of the
    // rigid body to the origin of the inertial axes.
    protected GVector _x;
    // orientation
    // represent the orientation of the rigid body with
    // a quaternion,
    protected GQuaternion _q;
    // linear momentum
    // linear momentum is simply the (p = m*v) mass times the linear
    // speed of the rigid body.
    protected GVector _p;
    // angular momentum
    protected GVector _l;
    // orientation matrix
    protected GMatrix _r;
    // linear velocity
    protected GVector _v;
    // angular velocity
    protected GVector _w;
    // torque function describing the behaviour of the
    // rigid body.
    protected IGFunction _torqueFct;
    // force function describing the bahaviour of the
    // rigid body.
    protected IGFunction _forceFct;

////////////////////////////////////////////////////////////////////////////////

    /**
     * Convert the orientation quaternion q, the linear momentum p and the
     * angular momentum to a rotation matrix, a linear velocity and an angular
     * velocity contained in an array of objects returned by the function.
     * @param Q GQuaternion Orientation The orientation quaternion q
     * @param P GVector Linear momentum The linear momentum p
     * @param L GVector Angular momentum
     * @return Object[] First element is a Matrix, the others are
     * vectors.
     */
    protected Object[] ConvertQPLToRVW(GQuaternion Q, GVector P, GVector L)
    {
        Object[] ret = { new GMatrix(), new GVector(P.getSize()),
                         new GVector(L.getSize())};
        // convert only in third dimension
        if( P.getSize() == 3 )
        {
            // R =
            ret[0] = Q.convertToRotationMatrix((GMatrix) ret[0]);
        }
        // V =
        ((GVector) ret[1]).setValues(P.multiply(_inverseMass).getValues());
        // W =
        ((GVector) ret[2]).setValues(((GMatrix) ret[0]).multiply(
                _inverseInertia).multiply((GMatrix) ret[0]).multiply(_l).
                                     getValues());
        return ret;
    }

    /**
     * Define the current state of the rigid body.
     * @param x GVector Represent the current position
     * @param q GQuaternion Represent its orientation
     * @param p GVector Represent its linear momentum
     * @param l GVector Represent its angular momentum
     */
    public void setState(GVector x, GQuaternion q, GVector p, GVector l)
    {
        // position
        _x.setValues(x.getValues());
        // orientation
        _q.setValues(q.getValues());
        // linear momentum
        _p.setValues(p.getValues());
        // set linear velocity
        for( int index = 0; index < _p.getValues().length; index++ )
        {
            _v.setValue(index, _p.getValue(0)/_mass );
        }
        // angular momentum
        _l.setValues(l.getValues());
    }

	/**
	 * Return the mass of the rigid body.
	 * @return double The mass of the rigid body.
	 */
	public double getMass()
	{
		return _mass;
	}


    /**
     * Return the position of the current
     * rigid body.
     * @return GVector Vector representing
     * the current position from the center of mass to
     * the origin of the inertial repere.
     */
    public GVector getPosition()
    {
        return _x;
    }

    /**
     * Return the orientation of the body
     * by using a quaternion.
     * @return GQuaternion Quaternion of
     * orientation.
     */
    public GQuaternion getOrientation()
    {
        return _q;
    }

    /**
     * Return the (current) linear momentum
     * associated with this rigid body.
     * @return GVector Vector representing
     * the linear momentum.
     */
    public GVector getLinearMomentum()
    {
        return _p;
    }

    /**
     * Return the angular momentum associated
     * with this rigid body.
     * @return GVector Vector of angular momentum.
     */
    public GVector getAngularMomentum()
    {
        return _l;
    }

    /**
     * Define the force function which dictates
     * the rigid body's behaviour through time.
     * @param f IFunction Force function.
     */
    public void setForceFunction(IGFunction f)
    {
        _forceFct = f;
    }

    public IGFunction getForceFunction()
    {
        return _forceFct;
    }

    public IGFunction getTorqueFunction()
    {
        return _torqueFct;
    }

    /**
     * Define the torque function to be associated
     * with the current rigid body.
     * @param f IFunction Interface implemented by
     * the torque function.
     */
    public void setTorqueFunction(IGFunction f)
    {
        _torqueFct = f;
    }

    /**
     * Resolving differential equation numerically
     * for a specific time t and a specific interval
     * of time dt using Runge Kutta solver of the fourth
     * order. This function is actually using the position
     * and velocity for both linear and angular modes. It
     * also include the force and torque portions.
     * @param t double Time
     * @param dt double
     */
    public void update(double t, double dt)
    {
        double half_dt = 0.5 * dt;
        double sixth_dt = dt / 6.0;
        double tp_half_dt = t + half_dt;
        double tp_dt = t + dt;

        GVector XN = new GVector(_x.getSize());
        GVector PN = new GVector(_p.getSize());
        GVector LN = new GVector(_l.getSize());
        GVector VN = new GVector(_v.getSize());
        GVector WN = new GVector(_w.getSize());
        GQuaternion QN = new GQuaternion();
        GMatrix RN = new GMatrix(_r.getRowCount(), _r.getColCount());

        // A1 = G(t,S0), B1 = S0 + (dt/2)*A1
        GVector A1DXDT = new GVector(_v.getSize());
        A1DXDT.setValues(_v.getValues());
        GQuaternion A1DQDT = new GQuaternion(_q.multiply(_w).multiply(0.5));
        GVector A1DPDT = _forceFct.apply(t, _x, _q, _p, _l, _r, _v, _w);
        GVector A1DLDT = _torqueFct.apply(t, _x, _q, _p, _l, _r, _v, _w);
        XN.setValues(A1DXDT.multiply(half_dt).add(_x).getValues());
        QN.setValues(A1DQDT.multiply(half_dt).add(_q).getValues());
        PN.setValues(A1DPDT.multiply(half_dt).add(_p).getValues());
        LN.setValues(A1DLDT.multiply(half_dt).add(_l).getValues());
        Object[] conv = ConvertQPLToRVW(QN, PN, LN);
        RN = (GMatrix) conv[0];
        VN = (GVector) conv[1];
        WN = (GVector) conv[2];

        // A2 = G(t+dt/2,B1), B2 = S0 + (dt/2)*A2
        GVector A2DXDT = new GVector(VN.getSize());
        A2DXDT.setValues(VN.getValues());
        GQuaternion A2DQDT = new GQuaternion(QN.multiply(WN).multiply(0.5));
        GVector A2DPDT = _forceFct.apply(tp_half_dt, XN, QN, PN, LN, RN, VN, WN);
        GVector A2DLDT = _torqueFct.apply(tp_half_dt, XN, QN, PN, LN, RN, VN,
                                          WN);
        XN.setValues(A2DXDT.multiply(half_dt).add(_x).getValues());
        QN.setValues(A2DQDT.multiply(half_dt).add(_q).getValues());
        PN.setValues(A2DPDT.multiply(half_dt).add(_p).getValues());
        LN.setValues(A2DLDT.multiply(half_dt).add(_l).getValues());
        conv = ConvertQPLToRVW(QN, PN, LN);
        RN = (GMatrix) conv[0];
        VN = (GVector) conv[1];
        WN = (GVector) conv[2];

        // A3 = G(t+dt/2,B2), B3 = S0 + (dt/2)*A3
        GVector A3DXDT = new GVector(VN.getSize());
        A3DXDT.setValues(VN.getValues());
        GQuaternion A3DQDT = new GQuaternion(QN.multiply(WN).multiply(0.5));
        GVector A3DPDT = _forceFct.apply(tp_half_dt, XN, QN, PN, LN, RN, VN, WN);
        GVector A3DLDT = _torqueFct.apply(tp_half_dt, XN, QN, PN, LN, RN, VN,
                                          WN);
        XN.setValues(A3DXDT.multiply(half_dt).add(_x).getValues());
        QN.setValues(A3DQDT.multiply(half_dt).add(_q).getValues());
        PN.setValues(A3DPDT.multiply(half_dt).add(_p).getValues());
        LN.setValues(A3DLDT.multiply(half_dt).add(_l).getValues());
        conv = ConvertQPLToRVW(QN, PN, LN);
        RN = (GMatrix) conv[0];
        VN = (GVector) conv[1];
        WN = (GVector) conv[2];

        // A4 = G(t+dt/2,B3), S1 = S0 + (dt/6)*(A1+2*A2*A3+A4)
        GVector A4DXDT = new GVector(VN.getSize());
        A4DXDT.setValues(VN.getValues());
        GQuaternion A4DQDT = new GQuaternion(QN.multiply(WN).multiply(0.5));
        GVector A4DPDT = _forceFct.apply(tp_dt, XN, QN, PN, LN, RN, VN, WN);
        GVector A4DLDT = _torqueFct.apply(tp_dt, XN, QN, PN, LN, RN, VN, WN);
        _x.setValues(A2DXDT.add(A3DXDT).multiply(2.0).add(A1DXDT).add(A4DXDT).
                     multiply(sixth_dt).add(_x).getValues());
        _q.setValues(A2DQDT.add(A3DQDT).multiply(2.0).add(A1DQDT).add(A4DQDT).
                     multiply(sixth_dt).add(_q).getValues());
        _p.setValues(A2DPDT.add(A3DPDT).multiply(2.0).add(A1DPDT).add(A4DPDT).
                     multiply(sixth_dt).add(_p).getValues());
        _l.setValues(A2DLDT.add(A3DLDT).multiply(2.0).add(A1DLDT).add(A4DLDT).
                     multiply(sixth_dt).add(_p).getValues());

        conv = ConvertQPLToRVW(_q, _p, _l);
        _r = (GMatrix) conv[0];
        _v = (GVector) conv[1];
        _w = (GVector) conv[2];

    }

    /**
     * Initialization procedure of this class. It computes
     * all default values.
     * @throws InitializationException If a value as not been
     * set correctly.
     */
    public void initializeRigidBody()
            throws InitializationException
    {
        if (_mass == 0)
        {
            throw new InitializationException("Rigid body must have a mass.");
        }

        _inverseMass = 1.0 / _mass;

        if (_inertia != null)
        {
            // initialize the inverse inertia matrix.
            for (int i = 0; i < _inertia.getRowCount(); i++)
            {
                for (int j = 0; j < _inertia.getColCount(); j++)
                {
                    _inverseInertia.setValue(i, j, 1.0 / _inertia.getValue(i, j));
                }
            }
        }
    }

    /**
     * Define the mass of the current rigid
     * body.
     * @param mass double
     */
    public void setMass(double mass)
    {
        _mass = mass;
    }

    /**
     * Define the inertia matrix of the current
     * rigid body.
     * @param inertia GMatrix
     */
    public void setInertiaMatrix(GMatrix inertia)
    {
        _inertia = inertia;
    }

    /**
     * Initialize a rigid body by specifying its mass
     * and its matrix of inertia.
     */
    public GRigidBody()
    {
    }
}
