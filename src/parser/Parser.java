package parser;

import inter.Access;
import inter.And;
import inter.Arith;
import inter.Break;
import inter.Constant;
import inter.Do;
import inter.Else;
import inter.Expr;
import inter.Id;
import inter.If;
import inter.Not;
import inter.Or;
import inter.Rel;
import inter.Seq;
import inter.Set;
import inter.SetElem;
import inter.Stmt;
import inter.Unary;
import inter.While;

import java.io.IOException;

import symbols.Array;
import symbols.Env;
import symbols.Type;
import lexer.Lexer;
import lexer.Num;
import lexer.Tag;
import lexer.Token;
import lexer.Word;

/**
 * 语法分析器
 * 2014.5.5
 * */
public class Parser {
	private Lexer lex;// 词法分析器
	private Token look;// 向前看词法单元
	Env top = null;// 当前或顶层符号表
	int used = 0;// 变量声明的存储位置
	public Parser(Lexer l) throws IOException {
		lex = l;
		move();
	}
	void move() throws IOException {
		look = lex.scan();
		//System.out.println(look.tag);
	}
	void error(String s) {
		throw new Error("near line " + Lexer.line + ": " + s);
	}
	void match(int t) throws IOException {
		if (look.tag == t) move();
		else error("syntax error");
	}
	public void program() throws IOException {
		Stmt s = block();
		int begin = s.newlabel();
		int after = s.newlabel();
		s.emitlabel(begin);
		s.gen(begin, after);
		s.emitlabel(after);
	}
	Stmt block() throws IOException {
		match('{');
		Env saveEnv = top;
		top = new Env(top);
		decls();
		Stmt s = stmts();
		match('}');
		top = saveEnv;
		return s;
	}
	void decls() throws IOException {
		//System.out.println("Tag = "+look.tag);
		while (look.tag == Tag.BASIC) {
			//System.out.println("Tag = "+look.tag);
			Type p = type();
			Token tok = look;
			match(Tag.ID);
			match(';');
			//System.out.println("id = "+((Word)tok).lexeme);
			Id id = new Id((Word)tok, p, used);
			top.put(tok, id);
			used = used + p.width;
		}
	}
	Type type() throws IOException {
		Type p = (Type)look;
		match(Tag.BASIC);
		if (look.tag != '[') return p;
		else return dims(p);
	}
	Type dims(Type p) throws IOException {
		match('[');
		Token tok = look;
		match(Tag.NUM);
		match(']');
		if (look.tag == '[') p = dims(p);
		return new Array(((Num)tok).value, p);
	}
	Stmt stmts() throws IOException {
		if (look.tag == '}') return Stmt.Null;
		return new Seq(stmt(), stmts());
	}
	Stmt stmt() throws IOException {
		Expr x;
		Stmt s1, s2;
		Stmt savedStmt;
		switch (look.tag) {
		case ';':
			move();
			return Stmt.Null;
		case Tag.IF:
			//System.out.println("找到if语句");
			match(Tag.IF);
			//System.out.println("准备匹配左括号");
			match('(');
			x = bool();
			//System.out.println("准备匹配右括号");
			match(')');
			s1 = stmt();
			//System.out.println("寻找else语句");
			if (look.tag != Tag.ELSE) return new If(x, s1);
			match(Tag.ELSE);
			s2 = stmt();
			return new Else(x, s1, s2);
		case Tag.WHILE:
			While whilenode = new While();
			savedStmt = Stmt.Enclosing;
			Stmt.Enclosing = whilenode;
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			s1 = stmt();
			whilenode.init(x, s1);
			Stmt.Enclosing = savedStmt;
			return whilenode;
		case Tag.DO:
			Do donode = new Do();
			savedStmt = Stmt.Enclosing;
			Stmt.Enclosing = donode;
			match(Tag.DO);
			s1 = stmt();
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			match(';');
			donode.init(s1, x);
			Stmt.Enclosing = savedStmt;
			return donode;
		case Tag.BREAK:
			match(Tag.BREAK);
			match(';');
			return new Break();
		case '{':
			return block();
		default:
			return assign();
		}
	}
	Stmt assign() throws IOException {
		Stmt stmt;
		Token t = look;
		match(Tag.ID);
		Id id = top.get(t);
		//System.out.println("---------\n a value = "+t.toString());
		if (id == null) error(t.toString() + " undeclared");
		if (look.tag == '=') {
			move();
			stmt = new Set(id, bool());
		}
		else {
			Access x = offset(id);
			match('=');
			stmt = new SetElem(x, bool());
		}
		match(';');
		return stmt;
	}
	Expr bool() throws IOException {
		Expr x = join();
		while (look.tag == Tag.OR) {
			Token tok = look;
			move();
			x = new Or(tok, x, join());
		}
		return x;
	}
	Expr join() throws IOException {
		Expr x = equality();
		while (look.tag == Tag.AND) {
			Token tok = look;
			move();
			x = new And(tok, x, equality());
		}
		return x;
	}
	Expr equality() throws IOException {
		Expr x = rel();
		while (look.tag == Tag.EQ || look.tag == Tag.NE) {
			Token tok = look;
			move();
			x = new Rel(tok, x, rel());
		}
		return x;
	}
	Expr rel() throws IOException {
		Expr x = expr();
		switch(look.tag) {
		case '<':
		case Tag.LE:
		case Tag.GE:
		case '>':
			Token tok = look;
			move();
			return new Rel(tok, x, expr());
		default:
			return x;
		}
	}
	Expr expr() throws IOException {
		Expr x = term();
		while (look.tag == '+' || look.tag == '-') {
			Token tok = look;
			move();
			x = new Arith(tok, x, term());
		}
		return x;
	}
	Expr term() throws IOException {
		Expr x = unary();
		while (look.tag == '*' || look.tag == '/') {
			Token tok = look;
			move();
			x = new Arith(tok, x, unary());
		}
		return x;
	}
	Expr unary() throws IOException {
		if (look.tag == '-') {
			move();
			return new Unary(Word.Minus, unary());
		}
		else if (look.tag == '!') {
			Token tok = look;
			move();
			return new Not(tok, unary());
		}
		else return factor();
	}
	Expr factor() throws IOException {
		Expr x = null;
		switch(look.tag) {
		case '(':
			move();
			x = bool();
			match(')');
			return x;
		case Tag.NUM:
			x = new Constant(look, Type.Int);
			move();
			return x;
		case Tag.REAL:
			x = new Constant(look, Type.Float);
			move();
			return x;
		case Tag.TRUE:
			x = Constant.True;
			move();
			return x;
		case Tag.FALSE:
			x = Constant.False;
			move();
			return x;
		default:
			error("syntax error");
			return x;
		case Tag.ID:
			String s = look.toString();
			Id id = top.get(look);
			if (id == null) error(s + " undeclared");
			move();
			if (look.tag != '[') return id;
			else return offset(id);
		}
	}
	Access offset(Id a) throws IOException {
		Expr i;
		Expr w;
		Expr t1, t2;
		Expr loc;
		Type type = a.type;
		match('[');
		i = bool();
		match(']');
		type = ((Array)type).of;
		w = new Constant(type.width);
		t1 = new Arith(new Token('*'), i, w);
		loc = t1;
		while (look.tag == '[') {
			match('[');
			i = bool();
			match(']');
			type = ((Array)type).of;
			w = new Constant(type.width);
			t1 = new Arith(new Token('*'), i, w);
			t2 = new Arith(new Token('+'), loc, t1);
			loc = t2;
		}
		return new Access(a, loc, type);
	}
}
