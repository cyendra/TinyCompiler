package symbols;

import inter.Id;

import java.util.Hashtable;

import lexer.Token;

/**
 * 把字符串词法单元映射为类Id的对象
 * 2014.5.3 新建
 * */
public class Env {
	private Hashtable<Token, Id> table;
	protected Env prev;
	public Env(Env n) {
		table = new Hashtable<Token, Id>();
		prev = n;
	}
	public void put(Token w, Id i) {
		table.put(w, i);
	}
	public Id get(Token w) {
		for (Env e = this; e != null; e = e.prev) {
			Id found = (Id)(e.table.get(w));
			if (found != null) return found;
		}
		return null;
	}
}
