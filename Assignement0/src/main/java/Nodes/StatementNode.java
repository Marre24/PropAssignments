package Nodes;

import parser.ParserException;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class StatementNode implements INode{

    private AssignNode assignNode;
    private StatementNode statementNode;

    public StatementNode(Tokenizer tokenizer) throws ParserException, TokenizerException, IOException {
        if (tokenizer.current().token() != Token.IDENT)
            return;

        assignNode = new AssignNode(tokenizer);
        statementNode = new StatementNode(tokenizer);
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {

    }
}
