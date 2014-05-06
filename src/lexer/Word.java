package lexer;
/**
 * 管理保留字、标识符和像&&这样的复合词法单元的词素。
 * 2014.5.3 新建
 * */
public class Word extends Token {
	public String lexeme = "";
	public Word(String s, int tag) {
		super(tag);
		lexeme = s;
	}
	public String toString() {
		return lexeme;
	}
	public static final Word
		And = new Word("&&", Tag.AND), Or = new Word("||", Tag.OR),
		Eq  = new Word("==", Tag.EQ),  Ne = new Word("!=", Tag.NE),
		Le  = new Word("<=", Tag.LE),  Ge = new Word(">=", Tag.GE),
		Minus = new Word("minus", Tag.MINUS),
		True  = new Word("true", Tag.TRUE),
		False = new Word("false", Tag.FALSE),
		Temp  = new Word("t", Tag.TEMP);
}
