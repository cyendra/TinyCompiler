package lexer;
/**
 * 词法单元
 * 2014.5.3 新建
 * */
public class Token {
	public final int tag;
	public Token(int t) { 
		tag = t;
	} 
	public String toString() {
		return "" + (char)tag;
	}
}
