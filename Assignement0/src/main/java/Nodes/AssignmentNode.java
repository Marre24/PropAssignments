package Nodes;

import parser.ParserException;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssignmentNode implements INode {

    private final Lexeme id;
    private final Lexeme assignmentOp;
    private final ExpressionNode expressionNode;
    private final Lexeme semiColon;

    public AssignmentNode(Tokenizer tokenizer) throws ParserException, TokenizerException, IOException {
        if (tokenizer.current().token() != Token.IDENT)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type IDENT");
        id = tokenizer.current();
        tokenizer.moveNext();

        if (tokenizer.current().token() != Token.ASSIGN_OP)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type ASSIGN_OP");
        assignmentOp = tokenizer.current();
        tokenizer.moveNext();

        expressionNode = new ExpressionNode(tokenizer);

        if (tokenizer.current().token() != Token.SEMICOLON)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type SEMICOLON");
        semiColon = tokenizer.current();
        tokenizer.moveNext();
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if (args[0] instanceof Map<?,?> map){
            var hashMap = (Map<String, Double>) map;
            double value = (double) expressionNode.evaluate(args);
            hashMap.put(id.value().toString(), value);
        }

        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("AssignmentNode").append("\n");
        builder.append("\t".repeat(tabs + 1)).append(id).append("\n");
        builder.append("\t".repeat(tabs + 1)).append(assignmentOp).append("\n");

        expressionNode.buildString(builder, tabs + 1);

        builder.append("\t".repeat(tabs  + 1)).append(semiColon).append("\n");
    }
}
