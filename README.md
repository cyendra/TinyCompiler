TinyCompiler
============

Tiny Compiler

package main
------------
程序的执行从类Main的方法main开始。  
  
**class Main**  
创建一个词法分析器和一个语法分析器，然后调用语法分析器中的方法program。  
  
pakage lexer  
------------
词法分析器。  
  
**class Tag**  
定义了各个词法单元对应的常量。  
  
**class Token**  
  
**class Num < Token**  
  
**class Word < Token**  
管理保留字、标识符和像&&这样的复合词法单元的词素。  
  
