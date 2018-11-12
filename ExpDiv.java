public class ExpDiv extends Exp {

  Exp exp1, exp2;

  public ExpDiv(Exp e1, Exp e2) {
    exp1 = e1;
    exp2 = e2;
  }
    public Exp getExpL() {
	return exp1;
    }

    public Exp getExpR() {
	return exp2;
    }

    public Object visit(Visitor v, Object arg)
	throws Exception
    {
	return v.visitExpDiv(this, arg);
    }

  public String toString() {
    return exp1.toString() + " / " + exp2.toString();
  }
}

