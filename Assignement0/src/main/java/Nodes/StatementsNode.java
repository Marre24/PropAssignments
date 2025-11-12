package Nodes;

import parser.ParserException;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class StatementsNode implements INode{

    private AssignmentNode assignmentNode;
    private StatementsNode statementsNode;

    public StatementsNode(Tokenizer tokenizer) throws ParserException, TokenizerException, IOException {
        if (tokenizer.current().token() != Token.IDENT)
            return;

        assignmentNode = new AssignmentNode(tokenizer);
        statementsNode = new StatementsNode(tokenizer);
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if (assignmentNode == null)
            return null;
        statementsNode.evaluate(args);
        assignmentNode.evaluate(args);
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("StatementsNode").append("\n");
        if (assignmentNode == null)
            return;
        assignmentNode.buildString(builder, tabs + 1);
        statementsNode.buildString(builder, tabs + 1);
    }
}
