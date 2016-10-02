package stone;

import java.io.StringReader;

import org.junit.Test;

public class ParserTest {
    @Test
    public void testAdder() throws ParseException {

        Parser adder = Parser.rule().number().token("+").number();
        Lexer l = new Lexer(new StringReader("1 + 2"));
        adder.parse(l);
    }
}
