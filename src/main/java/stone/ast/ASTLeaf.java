package stone.ast;

import java.util.ArrayList;
import java.util.Iterator;

import stone.Token;

public class ASTLeaf extends ASTree {
    private static ArrayList<ASTree> empty = new ArrayList<ASTree>();
    protected Token token;

    public ASTLeaf(Token t) {
        token = t;
    }

    @Override
    public Iterator<ASTree> iterator() {
        return null;
    }

    @Override
    public String toString() {
        return token.getText();
    }

}
