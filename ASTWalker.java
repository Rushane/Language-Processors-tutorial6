import java_cup.runtime.*;
import java.io.*;

/**
 * A generalised AST walker that can use any visitor to visit an AST built
 * from parsing an input program (sequence of arithmetic expressions and
 * statements).
 * 
 * This class is derived from the common features of the Repl and ScmRepl
 * classes.  It is instructive to compare the three files to see how the
 * Visitor design pattern has allowed us to represent the AST in a way that
 * is divorced from what we want to do with it.  By using the Visitor design
 * pattern, we specify the behaviour while walking that AST in the Visitor
 * implementation, which is kept separate from the representation of the
 * AST itself.
 * 
 * The following command line arguments are accepted:
 * -eval Use the Evaluator to reduce expressions as it walks the tree
 * -scm  Use the Scheme translator to convert the tree to Scheme code
 * Any other command line arguments are treated as file names
 */
public class ASTWalker {

    public static final String DEFAULT_PROMPT = ">";
    // should have a set of properties that get set by cmd line params

    public static void main(String args[]) {
	String prompt;
	Visitor visitor;
	Object state;
	if (args.length > 0 && args[0].equals("-scm")) {
	    prompt = "ToScheme>";
	    visitor = new ToScheme();
	    state = null;
	} else {
	    prompt = "Eval>";
	    visitor = new Evaluator();
	    state = new Environment();
	}
	for (int i = 1; i < args.length; i++) {
	    String name = args[i];
	    try {
		FileReader r = new FileReader(new File(name));
		parseEvalShow(prompt, r, visitor, state);
	    } catch (FileNotFoundException fnfe) {
		System.out.println("Warning! Ignoring " + name);
		System.out.println(fnfe.getMessage());
	    }
	}
	// NB: state will potentially accumulate changes from each file
	// finally drop into the REPL
	repl(prompt, visitor, state);
    }

    /**
     * Perpetually read from standard input and parse and evaluate (visit)
     * each input terminated by "EOF"
     */
    public static void repl(String prompt, Visitor v, Object state){
	InputStreamReader reader = new InputStreamReader(System.in);
	while (true) {
	    parseEvalShow(prompt, reader, v, state);
	}
    }

    public static void parseEvalShow(String prompt, Reader reader,
				     Visitor visitor, Object state) {
	ArithParser parser;
	ArithProgram program = null;
	System.out.print(prompt);
	try {
	    parser = new ArithParser(new Lexer(reader));
	    program = (ArithProgram) parser.parse().value;
	} catch (Exception e) {
	    System.out.println("Parse Error: " + e.getMessage());
	}

	if (program != null)
	    try {
		Object result;
		result = program.visit(visitor, state);
		System.out.println("\nResult: " + result);
	    } catch (Exception e) {
		System.out.println(e.getMessage());
	    }
    }
}
