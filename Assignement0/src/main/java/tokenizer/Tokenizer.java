package tokenizer;

import scanner.Scanner;

import java.io.IOException;

public class Tokenizer implements ITokenizer{
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
    private Lexeme current = null;

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner.open(fileName);

        moveNext();
    }

    @Override
    public Lexeme current() {
        return current;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {
        scanner.moveNext();
        char current = scanner.current();
        Token token = Token.EOF;

        if (current == Scanner.NULL)
            token = Token.NULL;
        if (Character.isDigit(current))
            token = Token.INT_LIT;
        if (Character.isAlphabetic(current))
            token = Token.IDENT;
        if (current == ADD_OP)
            token = Token.ADD_OP;
        if (current == SUB_OP)
            token = Token.SUB_OP;
        if (current ==  MULT_OP)
            token = Token. MULT_OP;
        if (current == DIV_OP)
            token = Token.DIV_OP;
        if (current == ASSIGN_OP)
            token = Token.ASSIGN_OP;
        if (current == LEFT_PAREN)
            token = Token.LEFT_PAREN;
        if (current == RIGHT_PAREN)
            token = Token.RIGHT_PAREN;
        if (current == SEMICOLON)
            token = Token.SEMICOLON;
        if (current == LEFT_CURLY)
            token = Token.LEFT_CURLY;
        if (current == RIGHT_CURLY)
            token = Token.RIGHT_CURLY;


        this.current = new Lexeme(current, token);
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
