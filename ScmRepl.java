import java_cup.runtime.*;
import java.io.*;

public class ScmRepl {

    public static final String PROMPT = "ToScheme>";

    public static void main(String args[]) {
	repl(new Environment());
    }

    public static void repl(Environment env){
	InputStreamReader reader = new InputStreamReader(System.in);
	while (true) {
	    parseEvalShow(reader, env);
	}
    }

    public static void parseEvalShow(Reader reader,
				     Environment env) {
	ArithParser parser;
	ArithProgram program = null;
	//Evaluator interp = new Evaluator();
	ToScheme translator = new ToScheme();
	System.out.print(PROMPT);
	try {
	    parser = new ArithParser(new Lexer(reader));
	    program = (ArithProgram) parser.parse().value;
	} catch (Exception e) {
	    System.out.println("Parse Error: " + e.getMessage());
	}

	if (program != null)
	    try {
		Object result;
		result = program.visit(translator, env);
		System.out.println("\nResult: " + result);
	    } catch (Exception e) {
		System.out.println(e.getMessage());
	    }
    }

}
