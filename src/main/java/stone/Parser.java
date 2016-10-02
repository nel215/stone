package stone;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import stone.ast.ASTLeaf;
import stone.ast.ASTList;
import stone.ast.ASTree;

public class Parser {
    protected static abstract class Element {
        protected abstract void parse(Lexer lexer, List<ASTree> res) throws ParseException;
    }

    protected static abstract class AToken extends Element {
        protected Factory factory;

        protected AToken(Class<? extends ASTLeaf> type) {
            if (type == null) {
                type = ASTLeaf.class;
            }
            factory = Factory.get(type, Token.class);
        }

        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            Token t = lexer.read();
            if (!test(t)) {
                throw new ParseException(t);
            }
            ASTree leaf = factory.make(t);
            res.add(leaf);
        }

        protected abstract boolean test(Token t);
    }

    protected static class NumToken extends AToken {

        protected NumToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

        @Override
        protected boolean test(Token t) {
            return t.isNumber();
        }

    }

    protected static class Leaf extends Element {
        protected String[] tokens;

        protected Leaf(String[] pat) {
            tokens = pat;
        }

        @Override
        protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            Token t = lexer.read();
            if (t.isIdentifier()) {
                for (String token : tokens) {
                    if (!token.equals(t.getText())) {
                        continue;
                    }
                    res.add(new ASTLeaf(t));
                    return;
                }
            }
            if (tokens.length > 0) {
                throw new ParseException(tokens[0] + " expected.", t);
            }
            throw new ParseException(t);
        }
    }

    public static final String factoryName = "create";

    protected static abstract class Factory {
        protected abstract ASTree make0(Object arg) throws Exception;

        protected ASTree make(Object arg) {
            try {
                return make0(arg);
            } catch (IllegalArgumentException e1) {
                throw e1;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }

        protected static Factory getForASTList(Class<? extends ASTree> clazz) {
            Factory f = get(clazz, List.class);
            if (f != null) {
                return f;
            }
            return new Factory() {
                protected ASTree make0(Object arg) throws Exception {
                    List<ASTree> results = (List<ASTree>) arg;
                    if (results.size() == 1) {
                        return results.get(0);
                    }
                    return new ASTList(results);
                }
            };

        }

        protected static Factory get(Class<? extends ASTree> clazz, Class<?> argType) {
            if (clazz == null) {
                return null;
            }
            try {
                final Method m = clazz.getMethod(factoryName, new Class<?>[] { argType });
                return new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        return (ASTree) m.invoke(null, arg);
                    }
                };
            } catch (NoSuchMethodException e) {
            }
            try {
                final Constructor<? extends ASTree> c = clazz.getConstructor(argType);
                return new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        return c.newInstance(arg);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected List<Element> elements;
    protected Factory factory;

    public Parser(Class<? extends ASTree> clazz) {
        reset(clazz);
    }

    public Parser reset(Class<? extends ASTree> clazz) {
        elements = new ArrayList<Element>();
        factory = Factory.getForASTList(clazz);
        return this;
    }

    public static Parser rule() {
        return rule(null);
    }

    public static Parser rule(Class<? extends ASTree> clazz) {
        return new Parser(clazz);
    }

    public ASTree parse(Lexer lexer) throws ParseException {
        ArrayList<ASTree> results = new ArrayList<ASTree>();
        for (Element e : elements) {
            e.parse(lexer, results);
        }
        return factory.make(results);
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
