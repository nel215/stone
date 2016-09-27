package stone;

public abstract class Token{
    public static final Token EOF = new Token(-1){}; // end of file
    private int lineNumber;

    protected Token(int line){
        lineNumber = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getText(){
        return "";
    }


}


