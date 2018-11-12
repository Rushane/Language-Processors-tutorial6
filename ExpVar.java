public class ExpVar extends Exp {

    String var;

    public ExpVar(String id) {
	var = id;
    }

    public String getVar() {
	return var;
    }

    public Object visit(Visitor v, Object arg) 
    throws Exception
    {
	return v.visitExpVar(this, arg);
    }

    public String toString() {
	return var;
    }
}
