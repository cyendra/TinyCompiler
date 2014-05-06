package main;

import java.io.FileInputStream;
import java.io.IOException;

import parser.Parser;
import lexer.Lexer;

public class Main {

	public static void main(String[] args) throws IOException {
		//String str = "G:\\Documents\\GitHub\\TinyCompiler\\src\\test.txt";
       // FileInputStream fis=new FileInputStream(str);
        //System.setIn(fis);
		Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		parse.program();
		System.out.write('\n');
	}
	
}
