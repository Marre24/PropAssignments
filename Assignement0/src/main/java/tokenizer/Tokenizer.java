package tokenizer;

import scanner.Scanner;

import java.io.IOException;

public class Tokenizer implements ITokenizer{
    private final Scanner scanner = new Scanner();

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner.open(fileName);
    }

    @Override
    public Lexeme current() {
        return null;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {

    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
