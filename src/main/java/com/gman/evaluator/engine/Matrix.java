package com.gman.evaluator.engine;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Simple class which implements basic matrix operations
 *
 * @author gman
 * @version 1.0
 */
public class Matrix implements Serializable, Cloneable {

    /**
     * Exception class to indicate of operation impossibility
     */
    public class MatrixException extends RuntimeException {

        /**
         * Default matrix exception
         */
        public MatrixException() {
            super();
        }

        /**
         * Matrix exception
         *
         * @param message - exception message
         */
        public MatrixException(String message) {
            super(message);
        }
    }

    /**
     * to store matrix data
     */
    private double data[][];
    /**
     * rows amount
     */
    private final int rowCount;
    /**
     * colnums amount
     */
    private final int colCount;

    /**
     * Default constructor
     */
    public Matrix() {
        this(0, 0);
    }

    /**
     * Creates matrix row x col matrix filled with 0
     */
    public Matrix(int row, int col) {
        if (row < 0 || col < 0) {
            throw new MatrixException("Dimensions must not be negative");
        }
        this.rowCount = row;
        this.colCount = col;
        this.data = new double[row][col];
    }

    /**
     * Creates matrix 1 x col from array
     */
    public Matrix(double[] data) {
        this(1, data.length);
        this.data[0] = data; //NOSONAR
    }

    /**
     * Creates matrix row x col from array-matrix
     */
    public Matrix(double[][] data) {
        this(data.length, data.length > 0 ? data[0].length : 0);
        this.data = data; //NOSONAR
    }

    /**
     * @return row number of the matrix
     */
    public final int getRowCount() {
        return rowCount;
    }

    /**
     * @return colnum number of the matrix
     */
    public final int getColCount() {
        return colCount;
    }

    /**
     * Determins if the matrix square
     */
    public final boolean isSquare() {
        return rowCount == colCount;
    }

    /**
     * @return the matrix element on row*col position
     */
    public final double getElement(int row, int col) {
        return data[row][col];
    }

    /**
     * Set the element of the matrix on the row*col position
     *
     * @param value - new element
     */
    public final void setElement(int row, int col, double value) {
        data[row][col] = value;
    }

    /**
     * Replace row with new values
     *
     * @param row   - to be replaced
     * @param value - elements to be set
     */
    public final void setRow(int row, double[] value) {
        if (colCount != value.length) {
            throw new MatrixException("New row is different size!");
        }
        System.arraycopy(value, 0, data[row], 0, colCount);
    }

    /**
     * Replace row with new values
     *
     * @param col   - to be replaced
     * @param value - elements to be set
     */
    public final void setCol(int col, double[] value) {
        if (rowCount != value.length) {
            throw new MatrixException("New col is different size!");
        }
        for (int i = 0; i < rowCount; i++) {
            data[i][col] = value[i]; //NOSONAR
        }
    }

    /**
     * Add current matrix to the given one
     *
     * @param m - another matrix
     * @return summ of this and another matrix
     */
    public final Matrix add(Matrix m) {
        if (getRowCount() != m.getRowCount() || getColCount() != m.getColCount()) {
            throw new MatrixException("The dimensions of the matrices must be identical");
        }
        Matrix result = new Matrix(rowCount, colCount);
        double matrixData[][] = result.toArray();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                matrixData[i][j] = data[i][j] + m.data[i][j];
            }
        }
        return result;
    }

    /**
     * Subtracts given mutrix from the current
     *
     * @param m - another matrix
     * @return subtraction of this and another matrix
     */
    public final Matrix sub(Matrix m) {
        if (getRowCount() != m.getRowCount() || getColCount() != m.getColCount()) {
            throw new MatrixException("The dimensions of the matrices must be identical");
        }
        Matrix result = new Matrix(rowCount, colCount);
        double matrixData[][] = result.toArray();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                matrixData[i][j] = data[i][j] - m.data[i][j];
            }
        }
        return result;
    }

    /**
     * Multiply current matrix on the given one
     *
     * @param m - another matrix
     * @return Multiplication of this and another matrix
     */
    public final Matrix mult(Matrix m) {
        if (getColCount() != m.getRowCount()) {
            throw new MatrixException("Number of columns in the first matrix must be equal to the number of rows in the second");
        }
        final int nrow = getRowCount();
        final int ncol = m.getColCount();
        final int count = getColCount();
        Matrix result = new Matrix(nrow, ncol);
        double matrixData[][] = result.toArray();
        double theColumn[] = new double[count];
        double e;
        for (int j = 0; j < ncol; j++) {
            for (int k = 0; k < count; k++) {
                theColumn[k] = m.data[k][j];
            }
            for (int i = 0; i < nrow; i++) {
                e = 0;
                for (int k = 0; k < count; k++) {
                    e += data[i][k] * theColumn[k];
                }
                matrixData[i][j] = e;
            }
        }
        return result;
    }

    /**
     * Multiply every element of the current matrix on the coef
     *
     * @param d - coef
     * @return Multiplication matrix on the coef
     */
    public final Matrix mult(double d) {
        Matrix result = new Matrix(rowCount, colCount);
        double matrixData[][] = result.toArray();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                matrixData[i][j] = data[i][j] * d;
            }
        }
        return result;
    }

    /**
     * Finds the minor of the matrix
     *
     * @param ki - minor row
     * @param kj - minor colnum
     * @return the minor
     */
    public final Matrix minor(int ki, int kj) {
        if (ki < 0 || kj < 0 || ki >= getRowCount() || kj >= getColCount()) {
            throw new MatrixException("Indexes must be within the matrix");
        }
        int mi = 0;
        Matrix result = new Matrix(rowCount - 1, colCount - 1);
        double matrixData[][] = result.toArray();
        for (int i = 0; i < rowCount; i++) {
            if (i != ki) {
                System.arraycopy(data[i], 0, matrixData[mi], 0, kj);
                System.arraycopy(data[i], kj + 1, matrixData[mi], kj, colCount - kj - 1);
                mi++;
            }
        }
        return result;
    }

    /**
     * If the matrix is square then it counts it`s determinant
     *
     * @return determinant
     */
    public final double determinant() {
        if (!isSquare()) {
            throw new MatrixException("Matrix must be square");
        }
        switch (getRowCount()) {
            case 0:
                return 0;
            case 1:
                return data[0][0];
            case 2:
                return data[0][0] * data[1][1] - data[0][1] * data[1][0];
            case 3:
                return data[0][0] * data[1][1] * data[2][2]
                        + data[1][0] * data[2][1] * data[0][2]
                        + data[0][1] * data[1][2] * data[2][0]
                        - data[0][2] * data[1][1] * data[2][0]
                        - data[1][0] * data[0][1] * data[2][2]
                        - data[2][1] * data[1][2] * data[0][0];
            default:
                double d = 0;
                for (int j = 0; j < colCount; j++) {
                    d += Math.pow(-1, j) * data[0][j] * minor(0, j).determinant();
                }
                return d;
        }
    }

    /**
     * Transposes current matrix
     *
     * @return transposed matrix
     */
    public final Matrix transpose() {
        Matrix result = new Matrix(colCount, rowCount);
        double matrixData[][] = result.toArray();
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                matrixData[i][j] = data[j][i];
            }
        }
        return result;
    }

    /**
     * Reverses current matrix
     *
     * @return reversed matrix
     */
    public final Matrix reverse() {
        if (!isSquare()) {
            throw new MatrixException("Matrix must be square");
        }
        double d = determinant();
        Matrix result = new Matrix(rowCount, colCount);
        double matrixData[][] = result.toArray();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                matrixData[j][i] = Math.pow(-1, i + j) * minor(i, j).determinant() / d;
            }
        }
        return result;
    }

    /**
     * Rounds mutrix
     *
     * @param decimals - precision
     */
    public final Matrix round(int decimals) {
        int round = (int) Math.pow(10, decimals);
        Matrix result = new Matrix(rowCount, colCount);
        double matrixData[][] = result.toArray();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                matrixData[j][i] = (double) Math.round(data[i][j] * round) / round;
            }
        }
        return result;
    }

    /**
     * @return matrix-array
     */
    public final double[][] toArray() {
        return data;
    }

    /**
     * Compares current matrix with the other one
     *
     * @param m   - another matrix
     * @param eps - eps of the comparing doubles
     */
    public final boolean epsEquals(Matrix m, double eps) {
        if (getRowCount() != m.getRowCount() || getColCount() != m.getColCount()) {
            return false;
        }
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (Math.abs(data[i][j] - m.data[i][j]) > eps) {
                    return false;
                }
            }
        }
        return true;
    }

    public final double norm() {
        double norm = Double.MIN_VALUE;
        for (int i = 0; i < rowCount; i++) {
            double sum = 0;
            for (int j = 0; j < colCount; j++) {
                sum += data[i][j];
            }
            if (norm < sum) {
                norm = sum;
            }
        }
        return norm;
    }

    public final boolean isDiagonalDominate() {
        if (rowCount != colCount) {
            return false;
        }
        for (int i = 0; i < rowCount; i++) {
            double sum = 0;
            for (int j = 0; j < colCount; j++) {
                if (i != j) {
                    sum += data[i][j];
                }
            }
            if (sum < data[i][i]) {
                return false;
            }
        }
        return true;
    }

    public final boolean isSymmetric() {
        return this.equals(this.transpose());
    }

    /**
     * Compares current matrix with the other one
     *
     * @param obj - another matrix
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Matrix other = (Matrix) obj;
        return epsEquals(other, Double.MIN_VALUE);
    }

    /**
     * Counts the hash code of the matrix
     *
     * @peturn the hash code
     */
    @Override
    public final int hashCode() {
        final int x = 7;
        final int y = 53;
        return x + y * Arrays.deepHashCode(data);
    }

    /**
     * @return string representation of the current matrix
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(Arrays.toString(data[i]));
            sb.append('\n');
        }
        return sb.toString();
    }
}
