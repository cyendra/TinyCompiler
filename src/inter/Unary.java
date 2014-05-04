package inter;

import lexer.Token;
import symbols.Type;

/**
 * 单目运算符
 * 2014.5.4
 * */
public class Unary extends Op {
	public Expr expr;
	public Unary(Token tok, Expr x) {
		super(tok, null);
		expr = x;
		type = Type.max(Type.Int, expr.type);
		if (type == null) error("type error");
	}
	public Expr gen() {
		return new Unary(op, expr.reduce());
	}
	public String toString() {
		return op.toString() + " " + expr.toString();
	}
}
