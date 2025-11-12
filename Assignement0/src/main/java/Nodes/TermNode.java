package Nodes;

import parser.ParserException;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class TermNode implements INode {
    private final FactorNode factorNode;
    private Lexeme operator = null;
    private TermNode termNode;

    public TermNode(Tokenizer tokenizer) throws TokenizerException, IOException, ParserException {
        factorNode = new FactorNode(tokenizer);

        if (tokenizer.current().token() != Token.DIV_OP && tokenizer.current().token() != Token.MULT_OP)
            return;
        operator = tokenizer.current();
        tokenizer.moveNext();

        termNode = new TermNode(tokenizer);
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        Double factorValue = null;
        if (factorNode.evaluate(args) instanceof Double d)
            factorValue = d;

        if (operator == null || factorValue == null)
            return factorValue;

        Double termValue = null;
        if (termNode.evaluate(args) instanceof Double d)
            termValue = d;

        if (termValue == null)
            return null;

        if (operator.token() == Token.DIV_OP)
            return factorValue / termValue;

        return factorValue * termValue;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("TermNode").append("\n");
        factorNode.buildString(builder, tabs + 1);

        if (operator == null)
            return;
        builder.append("\t".repeat(tabs  + 1)).append(operator).append("\n");
        termNode.buildString(builder, tabs + 1);

    }
}
