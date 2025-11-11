package Nodes;

import parser.ParserException;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class ExpressionNode implements INode{

    private final TermNode termNode;
    private Lexeme operator = null;
    private ExpressionNode expressionNode = null;

    public ExpressionNode(Tokenizer tokenizer) throws TokenizerException, IOException, ParserException {
        termNode = new TermNode(tokenizer);

        if (tokenizer.current().token() != Token.SUB_OP && tokenizer.current().token() != Token.ADD_OP)
            return;
        operator = tokenizer.current();
        tokenizer.moveNext();

        expressionNode = new ExpressionNode(tokenizer);
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("ExpressionNode").append("\n");
        termNode.buildString(builder, tabs + 1);

        if (operator == null)
            return;
        builder.append("\t".repeat(tabs + 1)).append(operator).append("\n");
        expressionNode.buildString(builder, tabs + 1);
    }
}
