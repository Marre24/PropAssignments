import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scanner.Scanner;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {

    private final static String[] PROGRAM_1_LEXEMES = {"a", "=", "1", "*", "2", "+", "(", "3", "-", "4", ")", "/", "5", ";"};
    private final static String[] PROGRAM_3_LEXEMES = {"hej", "=", "169", "*", "42", "+", "(", "6", "-", "8", ")", "/", "8", ";"};

    private final static String PROGRAM_1_PATH = "src/main/resources/program1.txt";
    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";
    private final static String PROGRAM_3_PATH = "src/main/resources/program3.txt";

    private final static Lexeme PROGRAM_1_FIRST_LEXEME = new Lexeme("a", Token.IDENT);

    private Tokenizer tokenizer;

    @BeforeEach
    public void tokenizerCanOpenAProgram() {
        tokenizer = new Tokenizer();
        assertDoesNotThrow(() -> tokenizer.open(PROGRAM_1_PATH));
    }

    @AfterEach
    public void tokenizerCanCloseAProgram() {
        assertDoesNotThrow(tokenizer::close);
    }

    @Test
    public void currentLexemeIsNotNull() {
        assertNotNull(tokenizer.current());
    }

    @Test
    public void currentLexemeValueEqualsFirstLexemeValue() {
        var actual = tokenizer.current();

        assertEquals(PROGRAM_1_FIRST_LEXEME.value(), actual.value());
    }

    @Test
    public void currentLexemeTokenEqualsFirstLexemeToken() {
        var actual = tokenizer.current();

        assertEquals(PROGRAM_1_FIRST_LEXEME.token(), actual.token());
    }

    @Test
    public void totalLexemesIsCorrect() throws TokenizerException, IOException {
        var current = tokenizer.current();
        int i = 0;

        while (current.value() instanceof String str && !str.equals(String.valueOf(Scanner.EOF))) {
            i++;
            tokenizer.moveNext();
            current = tokenizer.current();
        }

        assertEquals(PROGRAM_1_LEXEMES.length, i);
    }

    @Test
    public void lexemesIsCorrect() throws TokenizerException, IOException {
        var current = tokenizer.current();

        StringBuilder actual = new StringBuilder();

        while (current.value() instanceof String str && !str.equals(String.valueOf(Scanner.EOF))) {
            actual.append(current.value().toString()).append(";");
            tokenizer.moveNext();
            current = tokenizer.current();
        }

        StringBuilder expected = new StringBuilder();

        for (String s : PROGRAM_1_LEXEMES)
            expected.append(s).append(";");

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void totalLexemesIsCorrectForMultipleCharLexemes() throws TokenizerException, IOException {
        tokenizer = new Tokenizer();
        tokenizer.open(PROGRAM_3_PATH);

        var current = tokenizer.current();
        int i = 0;

        while (current.value() instanceof String str && !str.equals(String.valueOf(Scanner.EOF))) {
            i++;
            tokenizer.moveNext();
            current = tokenizer.current();
        }

        assertEquals(PROGRAM_3_LEXEMES.length, i);
    }
}
