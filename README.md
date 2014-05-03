Tiny Compiler
============

Tiny Compiler

package main
------------
程序的执行从类Main的方法main开始。  
  
**class Main**  
创建一个词法分析器和一个语法分析器，然后调用语法分析器中的方法program。  
  
package lexer  
------------
词法分析器。  
  
**class Tag**  
定义了各个词法单元对应的常量。  
  
**class Token**  
  
**class Num < Token**  
  
**class Word < Token**  
管理保留字、标识符和像&&这样的复合词法单元的词素。  
  
**class Real < Token**  
处理浮点数。  
  
**class Lexer**  
识别数字、标识符和保留字。  
  
package symbols  
---------------
实现了符号表和类型  
  
**Env**  
把字符串词法单元映射为类Id的对象
  
**Type**  
用词法分析器将基本类型从词素映射为适当的对象。  
  
**Array**  
数组  
  
