import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import tokenizer.TokenizerException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserTest {

    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";

    private Parser parser;

    @BeforeEach
    public void parserCanOpenProgram1() {
        parser  = new Parser();
        assertDoesNotThrow(() -> parser.open(PROGRAM_2_PATH));
    }

    @AfterEach
    public void parserCanCloseProgram1() {
        assertDoesNotThrow(() -> parser.close());
    }

    @Test
    public void parserDonNotThrowException() {
        assertDoesNotThrow(() -> parser.parse());
    }

    @Test
    public void parserReturnsNotNull() throws ParserException, TokenizerException, IOException {
        assertNotNull(parser.parse());
    }
}
