package tokenizer;

import scanner.Scanner;

import java.io.IOException;

public class Tokenizer implements ITokenizer{
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

        this.current = new Lexeme(current, Token.EOF);
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
