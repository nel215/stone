package stone;

import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

public class LexerTest {
    @Test public void testLexer() {
        Reader r = new StringReader("123");
        Lexer l = new Lexer(r);
        Assert.assertEquals(l.read(), Token.EOF);
    }

}


