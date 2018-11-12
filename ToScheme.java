import java.util.*;

public class ToScheme implements Visitor {

    String result;

    public ToScheme() {
	result = "";
    }

    public String getResult() {
	return result;
    }

    // program
    public Object visitArithProgram(ArithProgram p,
				    Object arg)
	throws Exception {
	result = (String) p.getSeq().visit(this, arg);
	return result;
    }

    // statements
    public Object visitStatement(Statement stmt, Object arg)
	throws Exception {
	return stmt.getExp().visit(this, arg);
    }

    public Object visitStmtSequence(StmtSequence exp,
				    Object arg)
	throws Exception {
	ArrayList stmts = exp.getSeq();
	if (stmts.size() == 1)
	    return ((Statement) stmts.get(0)).visit(this,
						    arg);
	else {
	    Iterator iter = stmts.iterator();
	    String result = "(begin ";
	    Statement stmt;
	    while (iter.hasNext()) {
		stmt = (Statement) iter.next();
		result += (String) stmt.visit(this, arg) +
		    "\n";
	    }
	    result += ")";
	    return result;
	}
    }

    public Object visitStmtDefinition(StmtDefinition sd, Object arg)
	throws Exception {
	String valExp = (String) sd.getExp().visit(this, arg);
	return "(define " + sd.getVar() + " " +
	    valExp + ")";
    }

    public Object visitStmtLet(StmtLet let, Object arg) throws Exception {
	// to be completed.
	return "(let () 'unimplemented)";
    }

    // expressions
    public Object visitExpAdd(ExpAdd exp, Object arg)
	throws Exception {
	Object left = exp.getExpL().visit(this, arg);
	Object right = exp.getExpR().visit(this, arg);
	return "(+ " + left + " " + right + ")";
    }
    public Object visitExpSub(ExpSub exp, Object arg)
	throws Exception {
	Object left = exp.getExpL().visit(this, arg);
	Object right = exp.getExpR().visit(this, arg);
	return "(- " + left + " " + right + ")";
    }

    public Object visitExpMul(ExpMul exp, Object arg)
	throws Exception {
	Object left = exp.getExpL().visit(this, arg);
	Object right = exp.getExpR().visit(this, arg);
	return "(* " + left + " " + right + ")";
    }

    public Object visitExpDiv(ExpDiv exp, Object arg)
	throws Exception {
	Object left = exp.getExpL().visit(this, arg);
	Object right = exp.getExpR().visit(this, arg);
	return "(/ " + left + " " + right + ")";
    }

    public Object visitExpMod(ExpMod exp, Object arg)
	throws Exception{
	Object left = exp.getExpL().visit(this, arg);
	Object right = exp.getExpR().visit(this, arg);
	return "(mod " + left + " " + right + ")";
    }

    public Object visitExpLit(ExpLit exp, Object arg)
	throws Exception{
	return "" + exp.getVal();
    }

    public Object visitExpVar(ExpVar exp, Object arg)
	throws Exception {
	return exp.getVar();
    }

}
