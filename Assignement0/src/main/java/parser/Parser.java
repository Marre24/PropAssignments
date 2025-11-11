package parser;

import Nodes.BlockNode;
import Nodes.INode;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Parser implements IParser {

    private final Tokenizer tokenizer = new Tokenizer();

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        tokenizer.open(fileName);
    }

    @Override
    public INode parse() throws IOException, TokenizerException, ParserException {
        return new BlockNode(tokenizer);
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }

    public String writeParseTreeToFile(String output2Path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            INode root = parse();
            root.buildString(stringBuilder, 0);
        } catch (IOException | TokenizerException | ParserException e) {
            return "Could not create parse tree";
        }
        writeToFile(output2Path, stringBuilder);
        return stringBuilder.toString();
    }

    private void writeToFile(String path, StringBuilder stringBuilder) throws IOException {
        File file = new File(path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(stringBuilder);
        }
    }
}
