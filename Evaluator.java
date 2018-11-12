import java.util.*;

public class Evaluator implements Visitor {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. */

    // allocate state here
    protected Integer result;	// result of evaluation

    public Evaluator() {
	// perform initialisations here
	result = new Integer(0);
    }

    public Object visitArithProgram(ArithProgram p,
				    Object arg)
	throws Exception 
    {
	result = (Integer) p.getSeq().visit(this, arg);
	return result;
    }

    public Object visitStatement(Statement s, Object arg)
	throws Exception
    {
	return s.getExp().visit(this, arg);
    }

    public Object visitStmtSequence(StmtSequence sseq,
				    Object arg)
	throws Exception
    {
	// remember that arg is the environment
	Statement s;
	ArrayList seq = sseq.getSeq();
	Iterator iter = seq.iterator();
	Object result = new Integer(0); // default result
	while(iter.hasNext()) {
	    s = (Statement) iter.next();
	    result = s.visit(this, arg);
	}
	// return last value evaluated
	return result;
    }

    public Object visitStmtDefinition(StmtDefinition sd,
				      Object arg)
	throws Exception
    {
	Environment env = (Environment) arg;
	Integer result;
	result = (Integer) sd.getExp().visit(this, env);
	env.put(sd.getVar(), result.intValue());
	return result;
    }

    public Object visitStmtLet(StmtLet let, Object arg)
	throws Exception {
	Environment env = (Environment) arg;
	ArrayList<Binding> bindings = let.getBindings();
	Statement body = let.getBody();

	int size = bindings.size();
	String[] vars = new String[size];
	int[] vals = new int[size];
	Binding b;
	for (int i = 0; i < size; i++) {
	    b = bindings.get(i);
	    /* Populate vars[] and vals[] appropriately so that the
	     * body will be evaluated w.r.t. the correct
	     * environment */
            vars[i] = b.getVar();
	    Exp valExp = b.getValExp();
	    vals[i] = (Integer) valExp.visit(this, env);
	}
	// create new env as child of current
	Environment newEnv = new Environment(vars,vals, env);
	return body.visit(this, newEnv);
    }

    public Object visitExpAdd(ExpAdd exp, Object arg)
	throws Exception
    {
	Integer val1, val2;
	val1 = (Integer) exp.getExpL().visit(this, arg);
	val2 = (Integer) exp.getExpR().visit(this, arg);
	return new Integer(val1.intValue() +
			   val2.intValue());
    }

    public Object visitExpSub(ExpSub exp, Object arg)
	throws Exception
    {
	Integer val1, val2;
	val1 = (Integer) exp.getExpL().visit(this, arg);
	val2 = (Integer) exp.getExpR().visit(this, arg);
	return new Integer(val1.intValue() -
			   val2.intValue());
    }

    public Object visitExpMul(ExpMul exp, Object arg)
	throws Exception
    {
	Integer val1, val2;
	val1 = (Integer) exp.getExpL().visit(this, arg);
	val2 = (Integer) exp.getExpR().visit(this, arg);
	return new Integer(val1.intValue() *
			   val2.intValue());
    }

    public Object visitExpDiv(ExpDiv exp, Object arg)
	throws Exception
    {
	Integer val1, val2;
	val1 = (Integer) exp.getExpL().visit(this, arg);
	val2 = (Integer) exp.getExpR().visit(this, arg);
	return new Integer(val1.intValue() /
			   val2.intValue());
    }

    public Object visitExpMod(ExpMod exp, Object arg)
	throws Exception
    {
	Integer val1, val2;
	val1 = (Integer) exp.getExpL().visit(this, arg);
	val2 = (Integer) exp.getExpR().visit(this, arg);
	return new Integer(val1.intValue() %
			   val2.intValue());
    }

    public Object visitExpLit(ExpLit exp, Object arg)
	throws Exception
    {
	return new Integer(exp.getVal());
    }

    public Object visitExpVar(ExpVar exp, Object arg)
	throws Exception
    {
	// remember that arg is really the environment
	Environment env = (Environment) arg;
	int val = env.get(exp.getVar());
	return new Integer(val);
    }
}
