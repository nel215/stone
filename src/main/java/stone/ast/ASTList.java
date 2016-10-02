package stone.ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {
    protected List<ASTree> children;

    public ASTList(List<ASTree> list) {
        children = list;
    }

    @Override
    public Iterator<ASTree> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        String sep = "";
        for (ASTree t : children) {
            builder.append(sep);
            sep = " ";
            builder.append(t.toString());
        }
        return builder.append(')').toString();
    }

}
