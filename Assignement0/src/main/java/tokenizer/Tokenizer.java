package tokenizer;

import scanner.Scanner;

import java.io.IOException;

public class Tokenizer implements ITokenizer {
    private static final char ADD_OP = '+';
    private static final char SUB_OP = '-';
    private static final char MULT_OP = '*';
    private static final char DIV_OP = '/';
    private static final char ASSIGN_OP = '=';
    private static final char LEFT_PAREN = '(';
    private static final char RIGHT_PAREN = ')';
    private static final char LEFT_CURLY = '{';
    private static final char RIGHT_CURLY = '}';
    private static final char SEMICOLON = ';';

    private final Scanner scanner = new Scanner();
    private Lexeme currentLexeme = null;
    private Lexeme nextLexeme = null;

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner.open(fileName);

        moveNext();
    }

    @Override
    public Lexeme current() {
        return currentLexeme;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {

        if (nextLexeme == null){
            this.currentLexeme = getNextLexeme();
            return;
        }

        this.currentLexeme = nextLexeme;
        nextLexeme = null;
    }

    private Lexeme getNextLexeme() throws IOException, TokenizerException {
        
        scanner.moveNext();

        char newCurrent = getValidChar();
        Token token = getTokenFor(newCurrent);

        if (token == Token.EOF)
            return  new Lexeme(String.valueOf(Scanner.EOF), token);
        if (token == Token.IDENT || token == Token.INT_LIT)
            return new Lexeme(getMultipleCharLexeme(newCurrent, token), token);

        return new Lexeme(String.valueOf(newCurrent), token);
    }

    private char getValidChar() throws IOException {
        char newCurrent = scanner.current();

        while (isInvalid(newCurrent)) {
            scanner.moveNext();
            newCurrent = scanner.current();
        }
        return newCurrent;
    }

    private Token getTokenFor(char newCurrent) throws TokenizerException {
        switch (newCurrent) {
            case Scanner.EOF:
                return Token.EOF;
            case Scanner.NULL:
                return Token.NULL;
            case ADD_OP:
                return  Token.ADD_OP;
            case SUB_OP:
                return Token.SUB_OP;
            case MULT_OP:
                return Token.MULT_OP;
            case DIV_OP:
                return Token.DIV_OP;
            case ASSIGN_OP:
                return Token.ASSIGN_OP;
            case LEFT_PAREN:
                return Token.LEFT_PAREN;
            case RIGHT_PAREN:
                return Token.RIGHT_PAREN;
            case SEMICOLON:
                return Token.SEMICOLON;
            case LEFT_CURLY:
                return Token.LEFT_CURLY;
            case RIGHT_CURLY:
                return Token.RIGHT_CURLY;
        }
        if (Character.isDigit(newCurrent))
            return Token.INT_LIT;
        if (Character.isAlphabetic(newCurrent))
            return Token.IDENT;

        throw new TokenizerException("Could not match " + newCurrent + " to any token type");
    }

    private String getMultipleCharLexeme(char c, Token token) throws IOException, TokenizerException {
        StringBuilder builder = new StringBuilder();

        while ((token == Token.INT_LIT && Character.isDigit(c))
                || (token == Token.IDENT && Character.isAlphabetic(c))) {
            builder.append(c);
            scanner.moveNext();
            c = scanner.current();
            if (isInvalid(c))
                break;

            Token scannerCurrentToken = getTokenFor(c);
            if (scannerCurrentToken != token){
                nextLexeme = new Lexeme(String.valueOf(c), scannerCurrentToken);
                break;
            }
        }

        return builder.toString();
    }

    private boolean isInvalid(char c) {
        if (Character.isDigit(c) || Character.isAlphabetic(c))
            return false;
        return switch (c) {
            case Scanner.EOF, ADD_OP, Scanner.NULL, SUB_OP, MULT_OP, DIV_OP, ASSIGN_OP, LEFT_PAREN, RIGHT_PAREN,
                 SEMICOLON, LEFT_CURLY, RIGHT_CURLY -> false;
            default -> true;
        };
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
