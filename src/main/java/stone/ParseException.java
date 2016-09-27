package stone;

import java.io.IOException;

public class ParseException extends Exception{
    static final long serialVersionUID = 0L;

    public ParseException(String msg){
        super(msg);
    }

    public ParseException(IOException e){
        super(e);
    }



}
