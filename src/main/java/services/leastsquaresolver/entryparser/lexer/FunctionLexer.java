/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.leastsquaresolver.entryparser.lexer;

import java.util.ArrayList;

/**
 *
 * @author estre
 */
public class FunctionLexer {

    private Lexer lexer;

    public FunctionLexer(String input, String[] variables) {
        lexer = new Lexer(input);
        defineLexemes();
        variablesLexemes(variables);
    }

    public FunctionLexer(String input) {
        this(input, new String[]{"x"});
    }

    private void defineLexemes() {
        simpleLexemes();
        dualLexemes();
        triLexemes();
        multiLexemes();
        
    }

    private void simpleLexemes() {
        Lexeme[] lexemes = new Lexeme[]{
            new Lexeme("OP", "\\+"),
            new Lexeme("OP", "-"),
            new Lexeme("OP", "\\*"),
            new Lexeme("OP", "/"),
            new Lexeme("LP", "\\("),
            new Lexeme("RP", "\\)"),
            new Lexeme("CONSTANT", "e")
        };
        lexer.addLexemes(lexemes);
    }

    private void dualLexemes() {
        Lexeme[] lexemes = new Lexeme[]{
            new Lexeme("CONSTANT", "pi"),};
        lexer.addLexemes(lexemes);
    }

    private void triLexemes() {
        Lexeme[] lexemes = new Lexeme[]{
            new Lexeme("FUNCTION", "sin"),
            new Lexeme("FUNCTION", "cos"),
            new Lexeme("FUNCTION", "log"),};
        lexer.addLexemes(lexemes);
    }

    private void multiLexemes() {
        Lexeme[] lexemes = new Lexeme[]{
            new Lexeme("NUMBER", "[0-9]+(\\.[0-9]*)?(?!.)"),
            new Lexeme("NUMBER", "\\.[0-9]*(?!.)"),
            new Lexeme("COEFF", "[A-Z][0-9]*"),};
        lexer.addLexemes(lexemes);
    }

    private void variablesLexemes(String[] vars) {
        int qt = vars.length;
        Lexeme[] l = new Lexeme[qt];
        for (int i = 0; i < qt; i++) {
            l[i] = new Lexeme("VAR", vars[i]);
        }
        lexer.addLexemes(l);
    }
    
    public ArrayList<Token> tokenize(){
        return lexer.tokenize();
    }
}
