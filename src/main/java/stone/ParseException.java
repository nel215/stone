package stone;

import java.io.IOException;

public class ParseException extends Exception {
    static final long serialVersionUID = 0L;

    public ParseException(Token t) {
        this("", t);
    }

    public ParseException(String msg, Token t) {
        super("syntax error around " + location(t) + ". " + msg);
    }

    private static String location(Token t) {
        if (t == Token.EOF) {
            return "the last line";
        }
        return "\"" + t.getText() + "\" at line " + t.getLineNumber();
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(IOException e) {
        super(e);
    }

}
