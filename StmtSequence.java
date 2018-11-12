import java.util.*;

public class StmtSequence extends Statement {

    ArrayList seq;		// sequence of commands

    public StmtSequence() {
	seq = new ArrayList();
    }

    public StmtSequence(Statement s) {
	this();
	seq.add(s);
    }

    public ArrayList getSeq() {
	return seq;
    }

    public StmtSequence add(Statement s) {
	seq.add(s);
	return this;
    }

    public Object visit(Visitor v, Object arg) 
	throws Exception
    {
	return v.visitStmtSequence(this, arg);
    }

    public String toString() {
	Iterator iter = seq.iterator();

	String result = "";
	while (iter.hasNext()) {
	    result = result + iter.next().toString() + "\n";
	}

	return result;
    }

}

