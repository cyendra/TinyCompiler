package inter;

import symbols.Type;
import lexer.Token;

/**
 * 表达式构造
 * 2014.5.4
 * */
public class Expr extends Node {
	public Token op;
	public Type type;
	Expr(Token tok, Type p) {
		op = tok;
		type = p;
	}
	/** 返回一个项，该项可成为一个三地址指令的右部 */
	public Expr gen() {
		return this;
	}
	/** 把一个表达式计算成为一个单一的地址 */
	public Expr reduce() {
		return this;
	}
	public void jumping(int t, int f) {
		emitjumps(toString(), t, f);
	}
	public void emitjumps(String test, int t, int f) {
		if (t != 0 && f != 0) {
			emit("if " + test + " goto L" + t);
			emit("goto L" + f);
		}
		else if (t != 0) emit("if " + test + " goto L" + t);
		else if (f != 0) emit("iffalse " + test +" goto L" + f);
		else ;
	}
	public String toString() {
		return op.toString();
	}
}
