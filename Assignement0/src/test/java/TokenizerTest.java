import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    public void currentLexemeIsNotNull(){
        assertNotNull(tokenizer.current());
    }

    @Test
    public void currentLexemeValueEqualsFirstLexemeValue(){
        var actual = tokenizer.current();

        assertEquals(PROGRAM_1_FIRST_LEXEME.value(), actual.value());
    }

    @Test
    public void currentLexemeTokenEqualsFirstLexemeToken(){
        var actual = tokenizer.current();

        assertEquals(PROGRAM_1_FIRST_LEXEME.token(), actual.token());
    }
}
