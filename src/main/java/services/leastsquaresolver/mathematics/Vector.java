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
public class Vector {

    private double[] vector;

    public Vector(int size, double elem) {
        vector = new double[size];
        for (int i = 0; i < size; i++) {
            vector[i] = elem;
        }
    }

    public Vector(int size) {
        this(size, 0);
    }

    public Vector() {
        this(3);
    }

    public Vector(double values[]) {

        vector = new double[values.length];
        System.arraycopy(values, 0, vector, 0, values.length);

    }

    public void setElem(int index, double elem) {
        vector[index] = elem;
    }

    public double getElem(int index) {
        return vector[index];
    }

    public void operation(int index1, int index2, double scalar) {
        vector[index1] += scalar * vector[index2];
    }

    public void operation(int index1, int index2) {
        double aux = vector[index1];
        vector[index1] = vector[index2];
        vector[index2] = aux;
    }

    public void operation(int index, double scalar) {
        vector[index] *= scalar;
    }

    public int size() {
        return vector.length;
    }

    public double scalarProduct(Vector v) {

        if (v.size() != this.size()) {
            throw new IllegalArgumentException("Vectors must be same size.");
        }

        double result = 0;

        for (int i = 0; i < this.size(); i++) {
            result += this.getElem(i) * v.getElem(i);
        }
        return result;
    }

    public Vector oneToOne(Vector v) {

        if (v.size() != this.size()) {
            throw new IllegalArgumentException("Vectors must be same size.");
        }

        Vector res = new Vector(v.size());
        for (int i = 0; i < this.size(); i++) {
            res.setElem(i, vector[i] * v.getElem(i));
        }
        return res;

    }

    public void applyFunction(Function f) {
        System.arraycopy(this.operateFunction(f).getVector(), 0, vector, 0, this.size());
    }

    public Vector operateFunction(Function f) {
        Vector res = new Vector(this.size());
        for (int i = 0; i < this.size(); i++) {
            res.setElem(i, f.apply(new double[]{vector[i]}));
        }
        return res;
    }

    public double[] getVector() {
        return vector;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.####");

        for (int i = 0; i < size(); i++) {

            s.append(String.format("%15s", df.format(vector[i])));

            s.append("\n");
        }
        return s.toString();
    }

}
