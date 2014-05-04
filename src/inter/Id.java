package inter;

import symbols.Type;
import lexer.Word;

/**
 * 一个标识符
 * 2014.5.4
 * */
public class Id extends Expr {
	/** 相对地址 */
	public int offset;
	public Id(Word id, Type p, int b) {
		super(id, p);
		offset = b;
	}
}
