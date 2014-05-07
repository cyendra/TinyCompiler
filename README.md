Tiny Compiler
============

Tiny Compiler

package main
------------
程序的执行从类Main的方法main开始  
  
**class Main**  
创建一个词法分析器和一个语法分析器，然后调用语法分析器中的方法program  
  
package lexer  
------------
词法分析器  
  
**class Tag**  
定义了各个词法单元对应的常量   
  
**class Token**  
词素  
  
**class Num < Token**  
管理整数的词素  
  
**class Word < Token**  
管理保留字、标识符和像&&这样的复合词法单元的词素  
  
**class Real < Token**  
管理浮点数的词素  
  
**class Lexer**  
词法分析器
用以识别数字、标识符和保留字  
  
package symbols  
---------------
实现了符号表和类型  
  
**Env**  
把字符串词法单元映射为类Id的对象
  
**Type**  
用词法分析器将基本类型从词素映射为适当的对象  
  
**Array**  
数组  
  
package inter
-------------
包inter包含了Node的类层次结构。  
Node有两个子类：对应于表达式结点的Expr和对应于语句结点的Stmt。  
  
package parser
--------------
语法分析器  
  
**Parser**  
语法分析器读入一个由词法单元组成的流，并调用适当的包inter中类的构造函数，构建出一棵抽象语法树。  
  
  
