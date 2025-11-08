import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ParserTest {

    private final static String PROGRAM_1_PATH = "src/main/resources/program1.txt";
    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";
    private final static String PROGRAM_3_PATH = "src/main/resources/program3.txt";

    private Parser parser;

    @BeforeEach
    public void parserCanOpenProgram1() {
        parser  = new Parser();
        assertDoesNotThrow(() -> parser.open(PROGRAM_1_PATH));
    }

    @AfterEach
    public void parserCanCloseProgram1() {
        assertDoesNotThrow(() -> parser.close());
    }

    @Test
    public void parserCanParse(){

    }
}
