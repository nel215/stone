package stone;

import java.util.ArrayList;
import java.util.List;

import stone.ast.ASTLeaf;
import stone.ast.ASTree;

public class Parser {
    protected static abstract class Element {
        protected abstract void parse(Lexer lexer) throws ParseException;
    }

    protected static abstract class AToken extends Element {
        protected AToken(Class<? extends ASTLeaf> type) {
            if (type == null) {
                type = ASTLeaf.class;
            }
            // TODO: factory
        }

        protected void parse(Lexer lexer) throws ParseException {
            lexer.read();
            // TODO: test and construct tree
        }
    }

    protected static class NumToken extends AToken {

        protected NumToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

    }

    protected static class Leaf extends Element {
        protected String[] tokens;

        protected Leaf(String[] pat) {
            tokens = pat;
        }

        @Override
        protected void parse(Lexer lexer) {
        }

    }

    protected List<Element> elements;

    public Parser(Class<? extends ASTree> clazz) {
        reset(clazz);
    }

    public Parser reset(Class<? extends ASTree> clazz) {
        elements = new ArrayList<Element>();
        return this;
    }

    public static Parser rule() {
        return rule(null);
    }

    public static Parser rule(Class<? extends ASTree> clazz) {
        return new Parser(clazz);
    }

    public void parse(Lexer lexer) throws ParseException {
        for (Element e : elements) {
            e.parse(lexer);
        }
    }

    public Parser number() {
        return number(null);
    }

    public Parser number(Class<? extends ASTLeaf> clazz) {
        elements.add(new NumToken(clazz));
        return this;
    }

    public Parser token(String... pat) {
        elements.add(new Leaf(pat));
        return this;
    }

}
