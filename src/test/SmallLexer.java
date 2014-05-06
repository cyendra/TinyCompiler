package test;

import java.io.IOException;


public class SmallLexer {
	char peek = ' ';
	public static int line = 1;
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
	public SmallToken scan() throws IOException {
		// 去掉空字符
		for ( ; ; readch()) {
			if (peek == ' ' || peek == '\t') continue;
			else if (peek == '\n') line = line + 1;
			else break;
		}
		// 特殊符号的情况
		/*
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
		*/
		// 数字
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10*v + Character.digit(peek, 10);
				readch();
			} while(Character.isDigit(peek));
			if (peek != '.') return new SmallReal(v);// 整数
			float x = v;
			float d = 10;
			for (;;) {
				readch();
				if (!Character.isDigit(peek)) break;
				x = x + Character.digit(peek, 10) / d;
				d = d * 10;
			}
			return new SmallReal(x);// 浮点数
		}
		// 单词
		/*
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word)words.get(s);
			if (w != null) return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		*/
		// 其它神奇的字符
		SmallToken tok = new SmallToken(peek);
		peek = ' ';
		return tok;
	}
}
