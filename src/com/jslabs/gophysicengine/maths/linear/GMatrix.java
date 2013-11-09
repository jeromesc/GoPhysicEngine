package com.jslabs.gophysicengine.maths.linear;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: This class contains the logic and operands of a matrix.</p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class GMatrix
{
    /**
     * Values of the matrix.
     */
    private double[][] _val;

////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor. Must provide number of rows
     * and columns.
     * @param row int Number of rows.
     * @param col int Number of columns.
     */
    public GMatrix(int row, int col)
    {
        _val = new double[row][col];
    }

    /**
     * Default constructor. By default build
     * a 2x2 matrix.
     */
    public GMatrix()
    {
        _val = new double[2][2];
    }

    /**
     * Create a new matrix filled with ones.
     * @return GMatrix Matrix filled with ones.
     */
    public GMatrix createOnes()
    {
        GMatrix result = new GMatrix(getRowCount(), getColCount());
        for (int i = 0; i < getRowCount(); i++)
        {
            for (int j = 0; j < getColCount(); j++)
            {
                result.setValue(i, j, 1.0);
            }
        }
        return result;
    }

    /**
     * Return a value contained in the matrix
     * at position (row, col).
     * @param row int index of the row.
     * @param col int index of the column.
     * @return double Value at position (row, col)
     * @throws IllegalArgumentException If row or
     * column in invalid.
     */
    public double getValue(int row, int col)
            throws IllegalArgumentException
    {
        if (row >= 0 && row < _val.length && col >= 0 && col < _val[0].length)
        {
            return _val[row][col];
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Define a value at position row, col in the
     * matrix.
     * @param row int Index of the row.
     * @param col int Index of the column.
     * @param value double Value
     * @throws IllegalArgumentException If index or
     * column is invalid.
     */
    public void setValue(int row, int col, double value)
            throws IllegalArgumentException
    {
        if (row >= 0 && row < _val.length && col >= 0 && col < _val[0].length)
        {
            _val[row][col] = value;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Copies all values of the matrix in
     * parameter to the current matrix.
     * @param matrix GMatrix Values of the matrix.
     * @throws IllegalArgumentException If the
     * dimension of the matrix is different with
     * the current matrix.
     */
    public void setValue(GMatrix matrix)
            throws IllegalArgumentException
    {
        if (matrix != null && matrix.getColCount() == getColCount() &&
            matrix.getRowCount() == getRowCount())
        {
            GMatrix result = new GMatrix(getRowCount(), getColCount());
            for (int i = 0; i < getRowCount(); i++)
            {
                for (int j = 0; j < getColCount(); j++)
                {
                    setValue(i, j, matrix.getValue(i, j));
                }
            }
        }
    }

    /**
     * Multiplies all elements from the matrix
     * with the scalar provided in parameter.
     * @param scalar double Scalar
     * @return GMatrix The modified matrix.
     */
    public GMatrix multiply(double scalar)
    {
        GMatrix result = new GMatrix(getRowCount(), getColCount());
        for (int i = 0; i < getRowCount(); i++)
        {
            for (int j = 0; j < getColCount(); j++)
            {
                result.setValue(i, j, getValue(i, j) * scalar);
            }
        }
        return result;
    }

    /**
     * Divide each element of the matrix by
     * the provided scalar.
     * @param scalar double Scalar
     * @return GMatrix Return the modified matrix.
     * @throws NumberFormatException If the scalar
     * is equal to 0.
     */
    public GMatrix divide(double scalar)
            throws NumberFormatException
    {
        if (scalar > 0)
        {
            GMatrix result = new GMatrix(getRowCount(), getColCount());
            for (int i = 0; i < getRowCount(); i++)
            {
                for (int j = 0; j < getColCount(); j++)
                {
                    result.setValue(i, j, getValue(i, j) / scalar);
                }
            }
            return result;
        }
        else
        {
            throw new NumberFormatException();
        }
    }

    /**
     * Multiply the current matrix by a vector.
     * @param vector GVector
     * @return GVector
     * @throws IllegalArgumentException
     */
    public GVector multiply(GVector vector)
            throws IllegalArgumentException
    {
        if (vector.getSize() == getColCount())
        {
            GVector result = new GVector(getRowCount());
            double sum = 0.0;
            for (int i = 0; i < getRowCount(); i++)
            {
                for (int j = 0; j < getColCount(); j++)
                {
                    sum += getValue(i, j) * vector.getValue(j);
                }
                result.setValue(i, sum);
                sum = 0.0;
            }
            return result;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Multiplies the matrix by the current matrix.
     * @param matrix GMatrix
     * @return GMatrix The multiplied matrix.
     * @throws IllegalArgumentException
     */
    public GMatrix multiply(GMatrix matrix)
            throws IllegalArgumentException
    {
        if (matrix != null && getColCount() == matrix.getRowCount())
        {
            GMatrix result = new GMatrix(getRowCount(), matrix.getColCount());
            for (int i = 0; i < getRowCount(); i++)
            {
                for (int j = 0; j < getColCount(); j++)
                {
                    for (int k = 0; k < matrix.getRowCount(); k++)
                    {
                        result.setValue(i, j,
                                        result.getValue(i, j) +
                                        getValue(i, k) *
                                        matrix.getValue(k, j));
                    }
                }
            }
            return result;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Transpose the current matrix.
     * @return GMatrix Return the transposed matrix.
     */
    public GMatrix transpose()
    {
        GMatrix result = new GMatrix(getColCount(), getRowCount());
        for (int i = 0; i < getRowCount(); i++)
        {
            for (int j = 0; j < getColCount(); j++)
            {
                result.setValue(j, i, getValue(i, j));
            }
        }
        return result;
    }

    /**
     * Return the number of rows
     * of the current matrix.
     * @return int Number of rows.
     */
    public int getRowCount()
    {
        return _val.length;
    }

    /**
     * Return the number of columns
     * in the current matrix.
     * @return int Number of columns.
     */
    public int getColCount()
    {
        if (_val.length > 0)
        {
            return _val[0].length;
        }
        return -1;
    }

}
