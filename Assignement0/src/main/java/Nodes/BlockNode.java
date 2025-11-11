package Nodes;

import parser.ParserException;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class BlockNode implements INode{

    private final Lexeme leftBraces;
    private final StatementNode statementNode;
    private final Lexeme rightBraces;

    public BlockNode(Tokenizer tokenizer) throws ParserException, TokenizerException, IOException {
        if (tokenizer.current().token() != Token.LEFT_CURLY)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type LEFT_CURLY");
        leftBraces = tokenizer.current();
        tokenizer.moveNext();

        statementNode = new StatementNode(tokenizer);

        if (tokenizer.current().token() != Token.RIGHT_CURLY)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type RIGHT_CURLY");
        rightBraces = tokenizer.current();
        tokenizer.moveNext();
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {

    }
}
