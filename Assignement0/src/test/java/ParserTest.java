import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";
    private final static String PARSE_TREE_2_PATH = "src/main/resources/parsetree2.txt";
    private final static String OUTPUT_2_PATH = "src/main/resources/output2.txt";
    private Parser parser;

    private static String expectedParseTree1;

    @BeforeAll
    public static void initTree() throws IOException {
        expectedParseTree1 = Files.readString(Path.of(PARSE_TREE_2_PATH), StandardCharsets.UTF_8);
    }

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

    @Test
    public void parserOutputsTheCorrectTree() {
        String parseTree = null;
        try {
            parseTree = parser.writeParseTreeToFile(OUTPUT_2_PATH);
        } catch (IOException e) {
            System.err.println("Could not write to file: " + e.getMessage());
        }

        assertEquals(expectedParseTree1, parseTree);
    }
}
