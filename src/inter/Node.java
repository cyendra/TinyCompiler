package inter;

import lexer.Lexer;

/**
 * 抽象语法树中的结点
 * 2014.5.4 新建
 * */
public class Node {
	int lexline = 0;/** 本结点对应的构造在源程序中的行号 */
	Node() {
		lexline = Lexer.line;
	}
	void error(String s) {
		throw new Error("near line "+lexline+": "+s);
	}
	static int labels = 0;
	public int newlabel() {
		return ++labels;
	}
	public void emitlabel(int i) {
		System.out.print("L" + i + ":");
	}
	public void emit(String s) {
		System.out.println("\t" + s);
	}
}
