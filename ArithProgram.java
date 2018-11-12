public class ArithProgram extends Exp {
    StmtSequence seq;

    public ArithProgram(StmtSequence s) {
	seq = s;
    }

    public StmtSequence getSeq() {
	return seq;
    }

    public Object visit(Visitor v, Object arg)
	throws Exception
    {
	return v.visitArithProgram(this, arg);
    }

    public String toString() {
	return seq.toString();
    }
}
