package stone;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer{
    private static String commentPat = "(//.*)";
    private static String numPat = "([0-9]+)";
    private static String strPat = "(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")";
    private static String idPat = "[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct}";
    public static String regexPat = "\\s*("+commentPat+"|"+numPat+"|"+strPat+"|"+idPat+")?";
    private Pattern pattern = Pattern.compile(regexPat);
    private boolean hasMore;
    private ArrayList<Token> queue;
    private LineNumberReader reader;

    Lexer(Reader r){
        reader = new LineNumberReader(r);
        hasMore = true;
        queue = new ArrayList<Token>();
    }

    public Token read() throws ParseException{
        if(fillQueue(0)){
            return queue.remove(0);
        }else{
            return Token.EOF;
        }
    }

    private boolean fillQueue(int i) throws ParseException{
        while(i >= queue.size()){
            if(!hasMore){
                return false;
            }
            readLine();
        }
        return true;
    }

    protected void readLine() throws ParseException{
        String line;
        try{
            line = reader.readLine();
        }catch(IOException e){
            throw new ParseException(e);
        }
        if(line==null){
            hasMore = false;
            return;
        }
        int lineNo = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while(pos < endPos){
            matcher.region(pos, endPos);
            if(matcher.lookingAt()){
                addToken(lineNo, matcher);
                pos = matcher.end();
            }else{
                throw new ParseException("bad token at line " + lineNo);
            }
        }
        queue.add(new IdToken(lineNo, Token.EOL));

    }

    protected void addToken(int lineNo, Matcher matcher){
        String m = matcher.group(1);
        if(m==null)return; // if space
        if(matcher.group(2)!=null)return; // if comment
        if(matcher.group(3)!=null){
            System.out.println(m);
            queue.add(new NumToken(lineNo, Integer.parseInt(m)));
        }else if(matcher.group(4)!=null){
            queue.add(Token.EOF);
        }else{
            queue.add(Token.EOF);
        }
    }

    protected static class NumToken extends Token{
        private int value;
        protected NumToken(int line, int v){
            super(line);
            value = v;
        }
        @Override
        public String getText() {
            return Integer.toString(value);
        }

    }

    protected static class IdToken extends Token{
        private String text;
        protected IdToken(int line, String id){
            super(line);
            text = id;
        }
        @Override
        public String getText() {
            return text;
        }

    }

}
