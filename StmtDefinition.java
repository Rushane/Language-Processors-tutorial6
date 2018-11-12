
public class StmtDefinition extends Statement {

    String var;
    Exp exp;

    public StmtDefinition(String id, Exp e) {
	var = id;
	exp = e;
    }

    public String getVar(){
	return var;
    }

    public Exp getExp() {
	return exp;
    }

    public Object visit(Visitor v, Object arg) 
	throws Exception
    {
	return v.visitStmtDefinition(this, arg);
    }

    /*
    public int eval(Environment env) {
	int result = exp.eval(env);
	env.put(var, result);
	return result;
    }
    */
}
