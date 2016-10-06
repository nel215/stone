package stone;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import stone.ast.ASTree;
import stone.ast.NegativeExpr;

public class ParserTest {
    @Test
    public void testAdder() throws ParseException {

        Parser adder = Parser.rule().number().token("+").number();
        Lexer l = new Lexer(new StringReader("1 + 2"));
        ASTree ast = adder.parse(l);
        Assert.assertNotNull(ast);
        Assert.assertEquals("(1 + 2)", ast.toString());
    }

    @Test
    public void testFactor() throws ParseException {
        Parser factor = Parser.rule().or(Parser.rule(NegativeExpr.class).sep("-"), Parser.rule());
        Lexer l = new Lexer(new StringReader("-1"));
        ASTree ast = factor.parse(l);
        Assert.assertNotNull(ast);
        Assert.assertEquals("()", ast.toString());
    }
}
