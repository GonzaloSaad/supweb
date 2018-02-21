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
public enum Pivot {
    TOTAL,
    PARTIAL;


    public static Pivot getPivotFromString(String string){
        if (string.equalsIgnoreCase(getTotalString())){
            return TOTAL;
        } else if (string.equalsIgnoreCase(getPartialString())){
            return PARTIAL;
        } else{
            return null;
        }
    }

    public static String getTotalString(){
        return "TOTAL";
    }

    public static String getPartialString(){
        return "PARTIAL";
    }
}

