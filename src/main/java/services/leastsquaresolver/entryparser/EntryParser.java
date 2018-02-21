/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.leastsquaresolver.entryparser;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import services.leastsquaresolver.mathematics.Function;

/**
 *
 * @author Gonzalo
 */
public class EntryParser {

    private final ArrayList<String> constants;
    private final ArrayList<Function> functions;

    public EntryParser(String input) {
        constants = new ArrayList<>();
        functions = new ArrayList<>();
        split(input);
    }

    private void split(String input) {
        ArrayList<String> terms = splitTerms(input);
        splitConstantsAndFunctions(terms);
    }

    private ArrayList splitTerms(String input) {
        ArrayList<String> terms = new ArrayList<>();
        int i = 0;
        int p = 0;
        char c;

        while (i < input.length()) {
            c = input.charAt(i);
            if (c == '+') {
                terms.add(input.substring(p, i));
                i++;
                p = i;
            } else if (c == '(') {
                Deque q = new ArrayDeque();
                q.push(c);

                while (!q.isEmpty()) {
                    i++;
                    c = input.charAt(i);
                    if (c == '(') {
                        q.push(c);
                    } else if (c == ')') {
                        q.pop();
                    }
                    if (i >= input.length()) {
                        throw new IllegalStateException("Parentesis are not balanced.");
                    }
                }
            }
            i++;
        }
        terms.add(input.substring(p, i));
        return terms;
    }

    private void splitConstantsAndFunctions(ArrayList<String> terms) {
        int i = 0;
        for (String t : terms) {
            String[] factors = t.split("\\*");
            ArrayList<String> cleanFactors = new ArrayList();            
            int count = 0;
            for (String f : factors) {
                if (f.matches("[A-Z][0-9]*")) {
                    count++;
                    if (count > 1) {
                        throw new IllegalStateException("One constant is needed per term, and only one.");
                    }
                    constants.add(f);
                } else {
                    cleanFactors.add(f);
                }
            }
            if (count == 0) {
                throw new IllegalStateException("One constant is needed per term.");
            }
            i++;

            if (cleanFactors.size()==0){
                cleanFactors.add("1");
            }

            functions.add(new Function("O"+i+"(x)="+String.join("*", cleanFactors)));
            
        }
    }

    public ArrayList<String> getConstants() {
        return constants;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }
    
    public String[] getConstantsArray(){
        return this.constants.toArray(new String[1]);
    }
    
    public Function[] getFunctionsArray(){
        return this.functions.toArray(new Function[1]);
    }
    
}
