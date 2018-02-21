/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.leastsquaresolver.entryparser.lexer;

/**
 *
 * @author estre
 */
public class Lexeme {

    private String pat;
    private String type;
    public static final int SIZE_ONE = 1;
    public static final int BIG_SIZE = 0;

    public Lexeme(String type, String pat) {
        this.type = type;
        this.pat = pat;
    }

    public String getPat() {
        return pat;
    }

    public void setPat(String pat) {
        this.pat = pat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Lexeme(" + this.type + ",'" + this.pat.replace("\\", "") + "')";
    }

    public int size() {
        if (pat.matches("\\[.-.\\](?![\\*\\+\\[])")) {
            return SIZE_ONE;
        }

        int tam = 0;

        for (int i = 0; i < pat.length(); i++) {
            char c = pat.charAt(i);
            if (c == '\\') {
                continue;
            }
            tam++;

        }

        if (tam > 0 && (pat.contains("*") || pat.contains("+"))) {
            return BIG_SIZE;
        }
        return tam;
    }
}
