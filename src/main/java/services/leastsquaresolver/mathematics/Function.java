/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.leastsquaresolver.mathematics;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * Clase que simula
 *
 * @author Gonzalo
 */
public class Function {

    private String name;
    private String[] arguments;
    private final String expressionString;
    private Expression expression;

    public Function(String function) {
        String header = function.split("=")[0];

        if (!header.matches("[a-zA-Z][0-9]*\\((([a-z],)*[a-z])\\)")) {
            throw new IllegalArgumentException("The header of the function is not correct");
        }

        for (int i = 0; i < header.length(); i++) {
            if (header.charAt(i) == '(') {
                name = header.substring(0, i);
                arguments = header.substring(i + 1, header.length() - 1).split(",");
                break;
            }
        }

        expressionString = function.split("=")[1];

        this.createExpression();
    }

    private void createExpression() {
        ExpressionBuilder builder = new ExpressionBuilder(expressionString);
        for (String var : arguments) {
            builder.variable(var);
        }
        expression = builder.build();

    }

    public double apply(double[] values) throws IllegalArgumentException {

        int argCount = arguments.length;
        int valCount = values.length;

        if (valCount != argCount) {
            throw new IllegalArgumentException("La function tiene " + argCount + ", se le pasaron " + valCount + ".");
        }

        for (int i = 0; i < argCount; i++) {
            expression.setVariable(arguments[i], values[i]);
        }
        return expression.evaluate();

    }

    public double apply(String value) throws IllegalArgumentException {
        String[] values = value.split(",");
        int valCount = values.length;
        double[] doublesValues = new double[valCount];

        for (int i = 0; i < valCount; i++) {
            doublesValues[i] = Double.parseDouble(values[i]);
        }
        return this.apply(doublesValues);
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return this.expressionString;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append("(")
                .append(String.join(",", arguments))
                .append(")=")
                .append(getExpression());
        return sb.toString();
        
    }

}
