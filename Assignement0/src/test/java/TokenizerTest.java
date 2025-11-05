import org.junit.jupiter.api.Test;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TokenizerTest {

    private final static String PROGRAM_1_PATH = "src/main/resources/program1.txt";
    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";

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


}
