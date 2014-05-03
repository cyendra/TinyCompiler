package lexer;
/**
 * 处理浮点数
 * 2014.5.3 新建
 * */
public class Real extends Token {
	public final float value;
	public Real(float v) {
		super(Tag.REAL);
		value = v;
	}
	public String toString() {
		return "" + value;
	}
}
