package test;

public class Test {

	public static void main(String[] args) {
		SmallLexer lex = new SmallLexer();
		SmallParser parser = new SmallParser(lex);
		SmallExpr x = parser.expr();
		System.out.println(x.value);
		System.out.println('\n');
	
	}

}
