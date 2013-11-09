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
 * @todo Implementation of the OrthoNormalize function for the third order.
 */
public class GVector
{
    /**
     * array of values contained in the vector.
     */
    private double[] _val;

////////////////////////////////////////////////////////////////////////////////

    /**
     * Return an array of values contained in the vector.
     * @return double[]
     */
    public double[] getValues()
    {
        return _val;
    }

    /**
     * Construct a vector of dimension specified
     * by the size parameter.
     * @param size int Dimension of the vector.
     */
    public GVector(int size)
    {
        _val = new double[size];
    }

    /**
     * Return the dimension of the vector.
     * @return int The size or dimension of the vector.
     */
    public int getSize()
    {
        return _val.length;
    }

    /**
     * Return a specific value stored in the vector. It must
     * be used considering that the first index (0) is considered
     * as been the x-axis related scalar and so on.
     * @param index int The index of the value to be retreived
     * @return double Return the value of the axis specified
     * @throws IndexOutOfBoundsException If the index parameter
     * is not congruent with the dimension of the vector.
     */
    public double getValue(int index)
            throws IndexOutOfBoundsException
    {
        if (index >= 0 && index < getSize())
        {
            return _val[index];
        }
        else
        {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Set a value to a specific dimension of the vector.
     * @param index int index must be between 0 (x-axis and the total
     * size of the vector).
     * @param value double Value
     * @throws IndexOutOfBoundsException If the index is not between
     * 0 and getSize().
     */
    public void setValue(int index, double value)
            throws IndexOutOfBoundsException
    {
        if (index >= 0 && index < getSize())
        {
            _val[index] = value;
        }
        else
        {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Define the values to be created in the vector object.
     * @param values double[] Values to be created in the vector.
     * @throws IllegalArgumentException If array is null.
     */
    public void setValues(double[] values)
            throws IllegalArgumentException
    {
        if (values != null && (values.length == getSize()))
        {
            for (int index = 0; index < getSize(); index++)
            {
                _val[index] = values[index];
            }
        }
        else
        {
            throw new IllegalArgumentException("Vector in parameter is null " +
                                               "or is not the same dimension.");
        }
    }

    /**
     * Compute an addition operator to the current vector.
     * For instance, if _a_ is a vector specified by (1,2,3) and
     * _b_ as (2,3,4) then _a_ + _b_ = (1+2,2+3,3+4).
     * @param vector GVector Add this vector.
     * @return GVector The sum of the this vector with the one
     * specified in parameter.
     * @throws IllegalArgumentException If the vector passed in
     * parameter is incorrect.
     */
    public GVector add(GVector vector)
            throws IllegalArgumentException
    {
        GVector result = new GVector(getSize());
        if (vector != null && (getSize() == vector.getSize()))
        {
            for (int index = 0; index < getSize(); index++)
            {
                result.setValue(index, getValue(index) + vector.getValue(index));
            }
            return result;
        }
        else
        {
            throw new IllegalArgumentException("Vector in parameter is null " +
                                               "or is not the same dimension.");
        }

    }

    /**
     * Substract the values from this vector with the one specified in
     * parameter. Return the substracted vector.
     * @param vector GVector
     * @return GVector The substracted vector.
     * @throws IllegalArgumentException If vector is null or size is different.
     */
    public GVector substract(GVector vector)
            throws IllegalArgumentException
    {
        GVector result = new GVector(getSize());
        if (vector != null && (getSize() == vector.getSize()))
        {
            for (int index = 0; index < getSize(); index++)
            {
                result.setValue(index, getValue(index) - vector.getValue(index));
            }
            return result;
        }
        else
        {
            throw new IllegalArgumentException("Vector in parameter is null " +
                                               "or is not the same dimension.");
        }
    }

    /**
     * Divide the current vector with a scalar.
     * @param scalar double
     * @return GVector
     * @throws ArithmeticException
     */
    public GVector divide(double scalar)
            throws ArithmeticException
    {
        GVector result = new GVector(getSize());
        if (scalar > 0)
        {
            for (int index = 0; index < getSize(); index++)
            {
                result.setValue(index, getValue(index) / scalar);
            }
            return result;
        }
        else
        {
            throw new ArithmeticException("Parameter can't be equal to zero.");
        }
    }

    /**
     * Multiple the current vector with a scalar.
     * @param scalar double
     * @return GVector
     */
    public GVector multiply(double scalar)
    {
        GVector result = new GVector(getSize());
        for (int index = 0; index < getSize(); index++)
        {
            result.setValue(index, getValue(index) * scalar);
        }
        return result;
    }

    /**
     * Return the length of the vector.
     * @return double
     */
    public double getLength()
    {
        return Math.sqrt(getLength());
    }

    /**
     * Return the squared length of the vector.
     * @return double
     */
    public double getSquaredLength()
    {
        double sum = 0.0;
        for (int index = 0; index < getSize(); index++)
        {
            sum += Math.pow(getValue(index), 2.0);
        }
        return sum;
    }

    /**
     * Return a vector perpendicular to this one.
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector getPerpendicularVector()
            throws IllegalArgumentException
    {
        GVector result;
        if (getSize() == 2)
        {
            // perp(x,y) = (y,-x)
            result = new GVector(2);
            result.setValue(0, getValue(1));
            result.setValue(1, getValue(0));
            return result;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Return a unit vector perpendicular to this one.
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector getUnitPerpendicularVector()
            throws IllegalArgumentException
    {
        GVector result;
        if (getSize() == 2)
        {
            return getPerpendicularVector().getUnitVector();
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Return a vector perpendicular to this one.
     * (compatible with vector of the third dimension)
     * @param vector GVector
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector getPerpendicularVector(GVector vector)
            throws IllegalArgumentException
    {
        GVector result;
        if (getSize() == 3)
        {
            // perpendicular vector for third order form
            // is the same thing as computing the cross
            // product. (ISBN 0-12-229064-X p.73)
            return getCrossProduct(vector);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Compute a unit vector perpendicular to this one.
     * (compatible with vector of the third dimension)
     * @param vector GVector
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector getUnitPerpendicularVector(GVector vector)
            throws IllegalArgumentException
    {
        GVector result;
        if (getSize() == 3)
        {
            return getPerpendicularVector(vector).getUnitVector();
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Return a unit vector
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector getUnitVector()
            throws IllegalArgumentException
    {
        GVector result = new GVector(getSize());
        // vector can't be filled of zeros, otherwise
        // it will cause division by zero exception.
        if (getLength() != 0)
        {
            for (int index = 0; index < getSize(); index++)
            {
                result.setValue(index, getValue(index) / getLength());
            }
            return result;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Compute a dot product.
     * @param vector GVector
     * @return double
     * @throws IllegalArgumentException
     */
    public double getDotProduct(GVector vector)
            throws IllegalArgumentException
    {
        double result = 0.0;
        if (vector != null && (vector.getSize() == getSize()))
        {
            for (int index = 0; index < getSize(); index++)
            {
                result += getValue(index) * vector.getValue(index);
            }
            return result;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Compute the cross product.
     * @param vector GVector
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector getCrossProduct(GVector vector)
            throws IllegalArgumentException
    {
        if( vector != null && (vector.getSize() == getSize()) &&
            (getSize() == 2 || getSize() == 3) )
        {
            GVector result = new GVector(getSize());
            if ( getSize() == 3)
            {
                // a2b3-a3b2
                result.setValue(0, getValue(1) * vector.getValue(2) -
                                getValue(2) * vector.getValue(1));
                // a3b1-a1b3
                result.setValue(1, getValue(2) * vector.getValue(0) -
                                getValue(0) * vector.getValue(3));
                // a1b2-a2b1
                result.setValue(2, getValue(0) * vector.getValue(1) -
                                getValue(1) * vector.getValue(0));
                return result;
            }
            else
            {
                // ref. http://www.myphysicslab.com/collision.html
                // [Rx, Ry, 0] x [Tx, Ty, 0] = [0, 0, RxTy-RyTx ]
                result.setValue(0,0);
                result.setValue(1,0);
                result.setValue(2, getValue(0)*vector.getValue(1)-
                                getValue(1)*vector.getValue(0));
                return result;
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Compute the cross product and return a unit vector.
     * @param vector GVector
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector getUnitCrossProduct(GVector vector)
            throws IllegalArgumentException
    {
        return getCrossProduct(vector).getUnitVector();
    }

    /**
     * Compute two vectors that are perpendicular to each other by specifying
     * two nonparallel vectors.
     * @param v0 GVector First vector
     * @param v1 GVector Second vector nonparallel to the first one.
     * {@code v0 and v1 are two nonparallel vectors. u0 is the vo vector
     * normalized. u1 is the perpendicular vector to u0.}
     * @return GVector[] Array of two vectors perpendicular from one to the
     * other.
     * @throws IllegalArgumentException If the order of the vector is not two.
     */
    public GVector[] OrthoNormalize(GVector v0, GVector v1)
            throws IllegalArgumentException
    {
        // only second order supported
        if (v0 != null && v1 != null &&
            (v0.getLength() == v1.getLength()) &&
            v0.getLength() == 2)
        {
            GVector[] u =
                    {
                    new GVector(2), new GVector(2)};
            // U0
            u[0] = v0.getUnitVector();
            // U1
            u[1] = v1.substract(u[0].multiply(u[0].getDotProduct(v1)));
            u[1] = u[1].getUnitVector();
            return u;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method was implemented to ensure the compatibility
     * with the junit framework.
     * @param o Object If the parameter is equal to the current
     * vector (values and dimension).
     * @return boolean True if it's equal.
     */
    public boolean equals(Object o)
    {
        if( o != null )
        {
            if( o instanceof GVector )
            {
                if( ((GVector)o).getSize() == getSize() )
                {
                    for( int i = 0; i < getSize(); i++ )
                    {
                        if( getValue(i) != ((GVector)o).getValue(i) )
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
