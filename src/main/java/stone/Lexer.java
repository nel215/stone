package stone;

import java.io.Reader;
import java.util.regex.Pattern;

public class Lexer{
    private static String commentPat = "(//.*)";
    private static String numPat = "([0-9]+)";
    private static String strPat = "(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")";
    private static String idPat = "[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct}";
    public static String regexPat = "\\s*("+commentPat+"|"+numPat+"|"+strPat+"|"+idPat+")?";
    private Pattern pattern = Pattern.compile(regexPat);

    Lexer(Reader r){
    }

    public Token read(){
        return Token.EOF;
    }




}
