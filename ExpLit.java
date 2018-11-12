public class ExpLit extends Exp {

  int val;

  public ExpLit(Integer v) {
    val = v.intValue();
  }

    public int getVal() {
	return val;
    }

    public Object visit(Visitor v, Object arg) 
    throws Exception
    {
	return v.visitExpLit(this, arg);
    }

  public String toString() {
    return Integer.toString(val);
  }
}

