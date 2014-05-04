package inter;

import symbols.Type;
import lexer.Token;

/**
 * 运算符
 * 2014.5.4
 * */
public class Op extends Expr {
	public Op(Token tok, Type p) {
		super(tok, p);
	}
	public Expr reduce() {
		Expr x = gen();
		Temp t = new Temp(type);
		emit(t.toString() + " = " + x.toString());
		return t;
	}
}
