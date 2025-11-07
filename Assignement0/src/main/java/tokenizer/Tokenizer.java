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

        while (!isValid(current)){
            scanner.moveNext();
            current = scanner.current();
        }

        Token token = Token.EOF;

        switch (current){
            case Scanner.EOF:
                this.current = new Lexeme(String.valueOf(Scanner.EOF), token);
                return;
            case Scanner.NULL:
                token = Token.NULL;
                break;
            case ADD_OP:
                token = Token.ADD_OP;
                break;
            case SUB_OP:
                token = Token.SUB_OP;
                break;
            case MULT_OP:
                token = Token.MULT_OP;
                break;
            case DIV_OP:
                token = Token.DIV_OP;
                break;
            case ASSIGN_OP:
                token = Token.ASSIGN_OP;
                break;
            case LEFT_PAREN:
                token = Token.LEFT_PAREN;
                break;
            case RIGHT_PAREN:
                token = Token.RIGHT_PAREN;
                break;
            case SEMICOLON:
                token = Token.SEMICOLON;
                break;
            case LEFT_CURLY:
                token = Token.LEFT_CURLY;
                break;
            case RIGHT_CURLY:
                token = Token.RIGHT_CURLY;
                break;
        }

        if (Character.isDigit(current))
            token = Token.INT_LIT;
        if (Character.isAlphabetic(current))
            token = Token.IDENT;

        this.current = new Lexeme(String.valueOf(current), token);
    }

    private boolean isValid(char c){
        if (Character.isDigit(c) || Character.isAlphabetic(c))
            return true;
        return switch (c) {
            case Scanner.EOF, ADD_OP, Scanner.NULL, SUB_OP, MULT_OP, DIV_OP, ASSIGN_OP, LEFT_PAREN, RIGHT_PAREN,
                 SEMICOLON, LEFT_CURLY, RIGHT_CURLY -> true;
            default -> false;
        };
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
