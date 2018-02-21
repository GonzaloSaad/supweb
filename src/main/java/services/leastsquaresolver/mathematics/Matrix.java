/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.leastsquaresolver.mathematics;

import java.text.DecimalFormat;

/**
 *
 * @author Gonzalo
 */
public class Matrix {

    private double[][] matrix;
    private final int COLS;
    private final int ROWS;

    public Matrix(int rows, int cols, double elem) {
        ROWS = rows;
        COLS = cols;
        matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = elem;
            }
        }
    }

    public Matrix(int rows, int cols) {
        this(rows, cols, 0);
    }

    public Matrix(int dim, double elem) {
        this(dim, dim, elem);
    }

    public Matrix(int dim) {
        this(dim, dim);
    }

    public Matrix() {
        this(3, 3);
    }

    public Vector getColumn(int colIndex) {

        Vector col = new Vector(ROWS);

        for (int i = 0; i < ROWS; i++) {
            col.setElem(i, matrix[i][colIndex]);
        }

        return col;
    }

    public Vector getRow(int rowIndex) {
        return new Vector(matrix[rowIndex]);
    }

    public void setElem(int row, int col, double elem) {
        matrix[row][col] = elem;
    }

    public double getElem(int row, int col) {
        return matrix[row][col];
    }

    public void applyFunctionToColumn(int col, Function f) {

        for (int i = 0; i < ROWS; i++) {
            matrix[i][col] = f.apply(new double[]{matrix[i][col]});
        }
    }

    public void applyFunctionToRow(int row, Function f) {
        for (int j = 0; j < COLS; j++) {
            matrix[row][j] = f.apply(new double[]{matrix[row][j]});
        }
    }

    public void oneToOneColumn(int col, double vector[]) {

        if (vector.length != ROWS) {
            throw new IllegalArgumentException("Vector size must the same as the columns of the matrix.");
        }

        for (int i = 0; i < ROWS; i++) {
            matrix[i][col] *= vector[i];
        }
    }

    public void oneToOneRow(int row, double vector[]) {
        for (int j = 0; j < COLS; j++) {
            matrix[row][j] *= vector[j];
        }
    }

    public void rowOperation(int row1, int row2, double scalar) {
        for (int j = 0; j < COLS; j++) {
            matrix[row1][j] += scalar * matrix[row2][j];
        }
    }

    public void rowOperation(int row1, int row2) {
        double[] aux = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = aux;
    }

    public void rowOperation(int row, double scalar) {
        for (int j = 0; j < COLS; j++) {
            matrix[row][j] *= scalar;
        }
    }

    public void colOperation(int col1, int col2, double scalar) {
        for (int i = 0; i < ROWS; i++) {
            matrix[i][col1] += scalar * matrix[i][col2];
        }
    }

    public void colOperation(int col1, int col2) {
        double aux;
        for (int i = 0; i < ROWS; i++) {
            aux = matrix[i][col1];
            matrix[i][col1] = matrix[i][col2];
            matrix[i][col2] = aux;
        }
    }

    public void colOperation(int col, double scalar) {
        for (int i = 0; i < ROWS; i++) {
            matrix[i][col] *= scalar;

        }
    }

    public int[] getMaxIndex() {
        double max = matrix[0][0];
        int imax = 0, jmax = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    imax = i;
                    jmax = j;
                }
            }
        }
        return new int[]{imax, jmax};
    }

    public int getIndexMaxOfColumn(int col) {
        double max = matrix[0][col];
        int imax = 0;

        for (int i = 1; i < ROWS; i++) {
            if (matrix[i][col] > max) {
                imax = i;
                max = matrix[i][col];
            }
        }
        return imax;
    }

    public int getIndexMaxOfRow(int row) {
        double max = matrix[row][0];
        int jmax = 0;

        for (int j = 1; j < COLS; j++) {
            if (matrix[row][j] > max) {
                jmax = j;
                max = matrix[row][j];
            }
        }
        return jmax;
    }

    public boolean needsTotalPivot() {
        int indexes[] = this.getMaxIndex();
        int imax = indexes[0];
        int jmax = indexes[1];

        return !(imax == 0 && jmax == 0);
    }

    public boolean needsPartialPivot() {
        int imax = getIndexMaxOfColumn(0);
        int jmax = getIndexMaxOfRow(0);

        return (imax != 0 || jmax != 0);
    }

    public boolean isSymmetrical() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i == j) {
                    continue;
                }
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void copyVectorToColumn(int col, Vector v) {
        if (v.size() != ROWS) {
            throw new IllegalArgumentException("Vector must be same size that column.");
        }

        for (int i = 0; i < ROWS; i++) {
            matrix[i][col] = v.getElem(i);
        }
    }

    public void copyVectorToRow(int row, Vector v) {
        if (v.size() != COLS) {
            throw new IllegalArgumentException("Vector must be same size that row.");
        }
        System.arraycopy(v.getVector(), 0, matrix[row], 0, COLS);

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.####");

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                s.append(String.format("%15s", df.format(matrix[i][j])));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
