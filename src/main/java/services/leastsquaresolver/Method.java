/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.leastsquaresolver;

/**
 *
 * @author Gonzalo
 */
public enum Method {
    GAUSS,
    GAUSS_JORDAN;

    public static Method getMethodFromString(String string){
        if (string.equalsIgnoreCase(getGaussString())){
            return GAUSS;
        } else if (string.equalsIgnoreCase(getGaussJordanString())){
            return GAUSS_JORDAN;
        } else{
            return null;
        }
    }

    public static String getGaussString(){
        return "GAUSS";
    }

    public static String getGaussJordanString(){
        return "GAUSS-JORDAN";
    }


}



