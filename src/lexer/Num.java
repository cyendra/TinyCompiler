package lexer;
/**
 * 整数值
 * 2014.5.3 新建
 * */
public class Num extends Token {
	public final int value;
	public Num(int v) {
		super(Tag.NUM);
		value = v;
	}
	public String toString() {
		return "" + value;
	}
}
