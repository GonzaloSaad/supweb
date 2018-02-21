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
public class StringIterator {

    private final String string;
    private int current;
    public static final String NONE = "";
    private final int DEFAULT_OFFSET = 1;

    public StringIterator(String s) {
        string = s;
        current = 0;
    }
    public String getChar(){
        return getChars(DEFAULT_OFFSET);
    }
    public String getChars(int offset) {
        int nextIndex = current + offset;

        if (nextIndex > string.length()) {
            return NONE;
        }
        return string.substring(current, nextIndex);
    }

    public void moveNext() {
        current++;
    }
    public void move(int steps){
        current+=steps;
    }

    public void movePrev() {
        current--;
    }

    public boolean hasNext() {
        return current < string.length();
    }

    public String next() {
        return getChars(DEFAULT_OFFSET);
    }
}
