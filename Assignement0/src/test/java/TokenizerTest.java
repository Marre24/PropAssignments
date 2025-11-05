import org.junit.jupiter.api.Test;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {

    private final static String PROGRAM_1_PATH = "src/main/resources/program1.txt";
    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";

    private final static Lexeme PROGRAM_1_FIRST_LEXEME = new Lexeme('a', Token.IDENT);


    @Test
    public void tokenizerCanOpenAProgram() {
        Tokenizer tokenizer = new Tokenizer();
        assertDoesNotThrow(() -> tokenizer.open(PROGRAM_1_PATH));
    }

    @Test
    public void tokenizerCanCloseAProgram() {
        Tokenizer tokenizer = new Tokenizer();
        try {
            tokenizer.open(PROGRAM_1_PATH);
        } catch (IOException | TokenizerException e) {
            System.err.println(e.getMessage());
            return;
        }
        assertDoesNotThrow(tokenizer::close);
    }

    @Test
    public void currentLexemeIsNotNull(){
        Tokenizer tokenizer = new Tokenizer();

        try { tokenizer.open(PROGRAM_1_PATH); }
        catch (IOException | TokenizerException e) { System.err.println(e.getMessage()); return; }

        var current = tokenizer.current();

        try { tokenizer.close(); }
        catch (IOException e) { System.err.println(e.getMessage()); return; }

        assertNotNull(current);
    }

    @Test
    public void currentLexemeValueHasFirstLexemeValue(){
        Tokenizer tokenizer = new Tokenizer();

        try { tokenizer.open(PROGRAM_1_PATH); }
        catch (IOException | TokenizerException e) { System.err.println(e.getMessage()); return; }

        var actual = tokenizer.current();

        try { tokenizer.close(); }
        catch (IOException e) { System.err.println(e.getMessage()); return; }

        assertEquals(PROGRAM_1_FIRST_LEXEME.value(), actual.value());
    }

}
