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
public class GenericVector<E> {

    private Object[] vector;

    public GenericVector(int size, E elem) {
        vector = new Object[size];
        for (int i = 0; i < size; i++) {
            vector[i] = elem;
        }
    }

    public GenericVector(int size) {
        this(size, null);
    }

    public GenericVector() {
        this(3);
    }

    public GenericVector(E values[]) {

        vector = new Object[values.length];
        System.arraycopy(values, 0, vector, 0, values.length);

    }

    public void setElem(int index, E elem) {
        vector[index] = elem;
    }

    public E getElem(int index) {
        return (E)vector[index];
    }

    public void operation(int index1, int index2) {
        Object aux = vector[index1];
        vector[index1] = vector[index2];
        vector[index2] = aux;
    }

    public int size() {
        return vector.length;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < size(); i++) {

            s.append(String.format("%15s", (vector[i])));

            s.append("\n");
        }
        return s.toString();
    }
    
    public String showEqualed(Vector v){
        
        if (v.size()!=this.size()){
            throw new IllegalArgumentException("Vectors must be same size.");
        }
        DecimalFormat df = new DecimalFormat("#.####");
        StringBuilder s = new StringBuilder();
        s.append("\n");
        for (int i = 0; i < size(); i++) {
            
            s.append(String.format("%20s", (vector[i]+"=")))
                    .append(String.format("%15s", df.format(v.getElem(i))));

            s.append("\n");
        }
        return s.toString();
    }
}
