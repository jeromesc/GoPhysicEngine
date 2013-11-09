package com.jslabs.gophysicengine.maths.linear;

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
public class GQuaternion
{
    /**
     * Stored values for this quaterion.
     * q = [w,x,y,z]
     */
    private GVector _vector = new GVector(4);

////////////////////////////////////////////////////////////////////////////////

    /**
     * Construct a new Quaterion with an array of double
     * values.
     * @param val double[]
     */
    public GQuaternion(double[] val)
    {
        _vector.setValues(val);
    }

    /**
     * Construct a new Quaterion with an existing vector.
     * @param vector GVector
     */
    public GQuaternion(GVector vector)
    {
        if (vector != null && vector.getSize() == 4)
        {
            _vector.setValues(vector.getValues());
        }
    }

    /**
     * Default constructor.
     */
    public GQuaternion()
    {

    }

    /**
     * Build a quaterion with the one passed
     * in parameter.
     * @param q GQuaternion
     */
    public GQuaternion(GQuaternion q)
    {
        setValues(q.getValues());
    }

    /**
     * Normalize the current quaternion.
     * @return GQuaternion
     */
    public GQuaternion getUnitQuaternion()
            throws IllegalArgumentException
    {
        return new GQuaternion(_vector.getUnitVector());
    }

    /**
     * Return a double value at a specific position
     * in the quaterion.
     * @param index int
     * @return double
     * @throws IllegalArgumentException
     */
    public double getValue(int index)
            throws IllegalArgumentException
    {
        return _vector.getValue(index);
    }

    /**
     * Define a set of values to be stored in the
     * current quaterion.
     * @param values double[]
     * @throws IllegalArgumentException
     */
    public void setValues(double[] values)
            throws IllegalArgumentException
    {
        _vector.setValues(values);
    }

    /**
     * Return the size of the quaterion. By default
     * return 4.
     * @return int
     */
    public int getSize()
    {
        return _vector.getSize();
    }

    /**
     * Define a value at a specific position in the
     * quaterion.
     * @param index int
     * @param value double
     * @throws IllegalArgumentException
     */
    public void setValue(int index, double value)
            throws IllegalArgumentException
    {
        _vector.setValue(index, value);
    }

    /**
     * Return an array of all double values contained
     * in the vector.
     * @return double[]
     */
    public double[] getValues()
    {
        return _vector.getValues();
    }

    /**
     * Implement the multiply algorithm to the current
     * quaterion.
     * @param q GQuaternion
     * @return GQuaternion
     * @throws IllegalArgumentException
     */
    public GQuaternion multiply(GQuaternion q)
            throws IllegalArgumentException
    {
        if (q != null)
        {
            GQuaternion result = new GQuaternion();
            // w = w0w1-x0x1-y0y1-z0z1
            setValue(0,
                     getValue(0) * q.getValue(0) - getValue(1) * q.getValue(1) -
                     getValue(2) * q.getValue(2) - getValue(3) * q.getValue(3));
            // i = w0x1+w1x0+y1z0-y0z1
            setValue(1,
                     getValue(0) * q.getValue(1) + q.getValue(0) * getValue(1) +
                     q.getValue(2) * getValue(3) - getValue(2) * q.getValue(3));
            // j = w0y1+w1y0+z1x0-z0x1
            setValue(2,
                     getValue(0) * q.getValue(2) + q.getValue(0) * getValue(2) +
                     q.getValue(3) * getValue(1) - getValue(3) * q.getValue(1));
            // k = w0z1+w1z0+x1y0-x0y1
            setValue(3,
                     getValue(0) * q.getValue(3) + q.getValue(0) * getValue(3) +
                     q.getValue(1) * getValue(2) - getValue(1) * q.getValue(2));
            return result;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Multiply the current quaterion by a vector of
     * the same size.
     * @param vector GVector
     * @return GQuaternion
     * @throws IllegalArgumentException
     */
    public GQuaternion multiply(GVector vector)
            throws IllegalArgumentException
    {
        return multiply(new GQuaternion(vector));
    }

    /**
     * Multiply the quaterion by a scalar.
     * @param scalar double
     * @return GQuaternion
     */
    public GQuaternion multiply(double scalar)
    {
        return new GQuaternion(_vector.multiply(scalar));
    }

    /**
     * Add a quaterion to another one.
     * @param q GQuaternion
     * @return GQuaternion
     * @throws IllegalArgumentException
     */
    public GQuaternion add(GQuaternion q)
            throws IllegalArgumentException
    {
        return new GQuaternion(_vector.add(q._vector));
    }

    /**
     * Substract a quaterion from this one.
     * @param q GQuaternion
     * @return GQuaternion
     * @throws IllegalArgumentException
     */
    public GQuaternion substract(GQuaternion q)
            throws IllegalArgumentException
    {
        return new GQuaternion(_vector.substract(q._vector));
    }

    /**
     * Convert the current quaterinion to a rotation
     * matrix.
     * @param r GMatrix
     * @return GMatrix
     * ref. : http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/index.htm
     */
    public GMatrix convertToRotationMatrix(GMatrix r)
            throws IllegalArgumentException
    {
        if( r.getRowCount() == 3 && r.getColCount() == 3 )
        {
            GMatrix result = new GMatrix(3, 3);

            // 1 - 2*qy2 - 2*qz2
            result.setValue(0, 0, 1 - 2 * Math.pow(getValue(2), 2));
            // 2*qx*qy - 2*qz*qw
            result.setValue(1, 0,
                            2 * getValue(1) * getValue(2) -
                            2 * getValue(3) * getValue(0));
            // 2*qx*qz + 2*qy*qw
            result.setValue(2, 0,
                            2 * getValue(1) * getValue(3) +
                            2 * getValue(2) * getValue(0));

            // 2*qx*qy + 2*qz*qw
            result.setValue(0, 1,
                            2 * getValue(1) * getValue(2) +
                            2 * getValue(3) * getValue(0));
            // 1 - 2*qx2 - 2*qz2
            result.setValue(1, 1,
                            1 - 2 * Math.pow(getValue(1), 2) -
                            2 * Math.pow(getValue(3), 2));
            // 2*qy*qz - 2*qx*qw
            result.setValue(1, 2,
                            2 * getValue(2) * getValue(3) -
                            2 * getValue(1) * getValue(0));

            // 2*qx*qz - 2*qy*qw
            result.setValue(2, 0,
                            2 * getValue(1) * getValue(3) -
                            2 * getValue(2) * getValue(0));
            // 2*qy*qz + 2*qx*qw
            result.setValue(2, 1,
                            2 * getValue(2) * getValue(3) +
                            2 * getValue(1) * getValue(0));
            // 1 - 2*qx2 - 2*qy2
            result.setValue(2, 2,
                            1 - 2 * Math.pow(getValue(1), 2) -
                            2 * Math.pow(getValue(2), 2));
            return result;
        }
        else
            throw new IllegalArgumentException();
    }


}
