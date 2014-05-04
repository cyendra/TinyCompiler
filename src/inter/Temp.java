package inter;

import symbols.Type;
import lexer.Word;

/**
 * 临时名字
 * 2014.5.4
 * */
public class Temp extends Expr {
	static int count = 0;
	int number = 0;
	public Temp(Type p) {
		super(Word.Temp, p);
		number = ++count;
	}
	public String toString() {
		return "t" + number;
	}
}
