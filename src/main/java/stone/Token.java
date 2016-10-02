package stone;

public abstract class Token {
    public static final Token EOF = new Token(-1) {
    }; // end of file
    public static final String EOL = "\\n"; // end of line
    private int lineNumber;

    protected Token(int line) {
        lineNumber = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isIdentifier() {
        return false;
    }

    public String getText() {
        return "";
    }

}
