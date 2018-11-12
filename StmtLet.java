import java.util.ArrayList;

public class StmtLet extends Statement {
    ArrayList<Binding> bindings;
    Statement body;

    public StmtLet(ArrayList<Binding> bs, Statement bod) {
	bindings = bs;
	body = bod;
    }

    public ArrayList<Binding> getBindings() {
	return bindings;
    }

    public Statement getBody() {
	return body;
    }

    public Object visit(Visitor v, Object arg) throws Exception {
	return v.visitStmtLet(this,arg);
    }

    public String toString() {
	return "let " + bindings + " in " + body;
    }

}
