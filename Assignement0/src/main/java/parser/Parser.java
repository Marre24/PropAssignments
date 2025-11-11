package parser;

import Nodes.BlockNode;
import Nodes.INode;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

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
}
