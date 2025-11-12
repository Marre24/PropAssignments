package Nodes;

import parser.Parser;
import parser.ParserException;
import tokenizer.Lexeme;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.util.Map;

public class FactorNode implements INode {

    private Lexeme integer;
    private Lexeme identifier;

    private Lexeme leftParentheses;
    private ExpressionNode expressionNode;
    private Lexeme rightParentheses;

    public FactorNode(Tokenizer tokenizer) throws TokenizerException, IOException, ParserException {
        if (tokenizer.current().token() == Token.INT_LIT) {
            integer = tokenizer.current();
            tokenizer.moveNext();
            return;
        }

        if (tokenizer.current().token() == Token.IDENT) {
            identifier = tokenizer.current();
            tokenizer.moveNext();
            return;
        }

        if (tokenizer.current().token() != Token.LEFT_PAREN)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type LEFT_PAREN");

        leftParentheses = tokenizer.current();
        tokenizer.moveNext();

        expressionNode = new ExpressionNode(tokenizer);

        if (tokenizer.current().token() != Token.RIGHT_PAREN)
            throw new ParserException("Lexeme: " + tokenizer.current() + " was not of type RIGHT_PAREN");

        rightParentheses = tokenizer.current();
        tokenizer.moveNext();
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if (identifier != null){
            if (args[0] instanceof Map<?,?> map){
                var hashMap = (Map<String, Double>) map;

                return hashMap.getOrDefault(identifier.value().toString(), 0D);
            }
            return null;
        }
        if (integer != null)
            return Double.parseDouble(integer.value().toString());

        return expressionNode.evaluate(args);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        builder.append("\t".repeat(tabs)).append("FactorNode").append("\n");

        if (integer != null){
            builder.append("\t".repeat(tabs + 1)).append(integer).append(".0").append("\n");
            return;
        }
        if (identifier != null){
            builder.append("\t".repeat(tabs + 1)).append(identifier).append("\n");
            return;
        }
        builder.append("\t".repeat(tabs + 1)).append(leftParentheses).append("\n");
        expressionNode.buildString(builder, tabs + 1);
        builder.append("\t".repeat(tabs + 1)).append(rightParentheses).append("\n");
    }
}
