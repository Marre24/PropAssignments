package Nodes;

import parser.ParserException;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.util.HashMap;

public class BlockNode implements INode{

    private final Lexeme leftBraces;
    private final StatementsNode statementsNode;
    private final Lexeme rightBraces;

    public BlockNode(Tokenizer tokenizer) throws ParserException, TokenizerException, IOException {
        if (tokenizer.current().token() != Token.LEFT_CURLY)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type LEFT_CURLY");
        leftBraces = tokenizer.current();
        tokenizer.moveNext();

        statementsNode = new StatementsNode(tokenizer);

        if (tokenizer.current().token() != Token.RIGHT_CURLY)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type RIGHT_CURLY");
        rightBraces = tokenizer.current();
        tokenizer.moveNext();
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if (args == null)
            args = new Object[]{new HashMap<String, Double>()};
        return statementsNode.evaluate(args);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("BlockNode").append("\n");
        builder.append("\t".repeat(tabs)).append(leftBraces).append("\n");

        statementsNode.buildString(builder, tabs + 1);

        builder.append("\t".repeat(tabs)).append(rightBraces).append("\n");
    }
}
