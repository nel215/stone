package stone;

import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

public class LexerTest {
    @Test
    public void testNumber() throws ParseException {
        Reader r = new StringReader("123");
        Lexer l = new Lexer(r);
        Token t = l.read();
        Assert.assertNotEquals(t, Token.EOF);
        Assert.assertEquals(t.getText(), "123");
        Assert.assertEquals(t.isNumber(), true);
        t = l.read();
        Assert.assertEquals(t.getText(), Token.EOL);
        Assert.assertEquals(t.isNumber(), false);
        t = l.read();
        Assert.assertEquals(t, Token.EOF);
    }

    @Test
    public void testString() throws ParseException {
        Reader r = new StringReader("\"test\"");
        Lexer l = new Lexer(r);
        Token t = l.read();
        Assert.assertNotEquals(t, Token.EOF);
        Assert.assertEquals(t.getText(), "test");
        Assert.assertEquals(t.isNumber(), false);
        t = l.read();
        Assert.assertEquals(t.getText(), Token.EOL);
        Assert.assertEquals(t.isNumber(), false);
        t = l.read();
        Assert.assertEquals(t, Token.EOF);
    }

    @Test
    public void testId() throws ParseException {
        Reader r = new StringReader("+");
        Lexer l = new Lexer(r);
        Token t = l.read();
        Assert.assertNotEquals(t, Token.EOF);
        Assert.assertEquals(t.getText(), "+");
        t = l.read();
        Assert.assertEquals(t.getText(), Token.EOL);
        t = l.read();
        Assert.assertEquals(t, Token.EOF);
    }

}
