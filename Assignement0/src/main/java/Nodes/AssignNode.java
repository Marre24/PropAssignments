package Nodes;

import parser.ParserException;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class AssignNode implements INode {

    private final Lexeme id;
    private final Lexeme equalsSign;
    private final ExpressionNode expressionNode;
    private final Lexeme semiColon;

    public AssignNode(Tokenizer tokenizer) throws ParserException, TokenizerException, IOException {
        if (tokenizer.current().token() != Token.IDENT)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type IDENT");
        id = tokenizer.current();
        tokenizer.moveNext();

        if (tokenizer.current().token() != Token.ASSIGN_OP)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type ASSIGN_OP");
        equalsSign = tokenizer.current();
        tokenizer.moveNext();

        expressionNode = new ExpressionNode(tokenizer);

        if (tokenizer.current().token() != Token.SEMICOLON)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type SEMICOLON");
        semiColon = tokenizer.current();
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
