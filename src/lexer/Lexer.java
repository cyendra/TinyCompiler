package lexer;

import java.io.IOException;
import java.util.Hashtable;

import symbols.Type;

/**
 * 识别数字、标识符和保留字。
 * 2014.5.3 更新
 * */
public class Lexer {
	public static int line = 1;
	char peek = ' ';
	Hashtable<String, Word> words = new Hashtable<String, Word>();
	void reserve(Word w) {
		words.put(w.lexeme, w);
	}
	public Lexer() {
		reserve(new Word("if",    Tag.IF));
		reserve(new Word("else",  Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do",    Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(Word.True);
		reserve(Word.False);
		reserve(Type.Int);
		reserve(Type.Char);
		reserve(Type.Bool);
		reserve(Type.Float);
	}
	/**把下一个输入字符读到变量peek中*/
	void readch() throws IOException {
		peek = (char)System.in.read();
	}
	/**识别复合的词法单元*/
	boolean readch(char c) throws IOException {
		readch();
		if (peek != c) return false;
		peek = ' ';
		return true;
	}
	public Token scan() throws IOException {
		// 去掉空字符
		for ( ; ; readch()) {
			//if (peek == ' ' || peek == '\t') continue;
			if (peek < 33 || peek > 126) continue;
			else if (peek == '\n') line = line + 1;
			else break;
		}
		//System.out.println((int)peek);
		//System.out.println(peek + " is special sign?");
		// 特殊符号的情况
		switch (peek) {
		case '&':
			if (readch('&')) return Word.And;
			else return new Token('&');
		case '|':
			if (readch('|')) return Word.Or;
			else return new Token('|');
		case '=':
			if (readch('=')) return Word.Eq;
			else return new Token('=');
		case '!':
			if (readch('=')) return Word.Ne;
			else return new Token('!');
		case '<':
			if (readch('=')) return Word.Le;
			else return new Token('<');
		case '>':
			if (readch('=')) return Word.Ge;
			else return new Token('>');
		}
		//System.out.println(peek + " is a digit?");
		// 数字
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10*v + Character.digit(peek, 10);
				readch();
			} while(Character.isDigit(peek));
			if (peek != '.') return new Num(v);// 整数
			float x = v;
			float d = 10;
			for (;;) {
				readch();
				if (!Character.isDigit(peek)) break;
				x = x + Character.digit(peek, 10) / d;
				d = d * 10;
			}
			return new Real(x);// 浮点数
		}
		//System.out.println(peek + " is a word?");
		// 单词
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word)words.get(s);
			//System.out.println("find a word "+s);
			if (w != null) return w;
			//System.out.println(s + " is a new word");
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		//System.out.println(peek + " is WTF?");
		// 其它神奇的字符
		Token tok = new Token(peek);
		peek = ' ';
		return tok;
	}
}
