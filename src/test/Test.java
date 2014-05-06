package test;

import java.util.Hashtable;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class Test {

	public static void main(String[] args) {
		/*
		SmallLexer lex = new SmallLexer();
		SmallParser parser = new SmallParser(lex);
		SmallExpr x = parser.expr();
		System.out.println(x.value);
		System.out.println('\n');
		*/
		Hashtable hs = new Hashtable();
		hs.put("123", 333);
		hs.put(213, "213");
		if (hs.get("213") != null) System.out.println("12333");
		else System.out.println("3333333");
		System.out.println(hs.get("123"));
	}

}
