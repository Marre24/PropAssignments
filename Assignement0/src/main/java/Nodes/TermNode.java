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
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {

    }
}
