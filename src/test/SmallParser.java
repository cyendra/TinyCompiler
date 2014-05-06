package test;

import java.io.IOException;

import lexer.Tag;

public class SmallParser {
	private SmallLexer lex;
	private SmallToken look;
	public SmallParser(SmallLexer l) {
		lex = l;
		move();
	}
	void move() {
		try {
			look = lex.scan();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void error(String s) {
		throw new Error(s);
	}
	void match(int t) {
		if (look.tag == t) move();
		else error("syntax error 1");
	}
	SmallExpr expr() {
		SmallExpr x = term();
		while (look.tag == '+' || look.tag == '-') {
			SmallToken tok = look;
			move();
			if (tok.tag == '+') x = new SmallExpr( x.value + term().value );
			else x = new SmallExpr( x.value - term().value ); 
		}
		return x;
	}
	SmallExpr term() {
		SmallExpr x = unary();
		while (look.tag == '*' || look.tag == '/') {
			SmallToken tok = look;
			move();
			if (tok.tag == '*') x = new SmallExpr( x.value * unary().value );
			else x = new SmallExpr( x.value / unary().value );
		}
		return x;
	}
	SmallExpr unary() {
		if (look.tag == '-') {
			move();
			return new SmallExpr(-unary().value);
		}
		else return factor();
	}
	SmallExpr factor() {
		SmallExpr x = null;
		switch (look.tag) {
		case '(':
			move();
			x = expr();
			match(')');
			return x;
		case Tag.REAL:
			x = new SmallExpr(((SmallReal)look).value);
			move();
			return x;
		default:
			error("syntax error 2");
			return x;
		}
	}
}
