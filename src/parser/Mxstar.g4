grammar Mxstar;

@header {
	package parser;
}

program 
	: def* EOF
	;
  
def
	: varDefList
	| functDef
	| classDef
	;

classDef
	: 'class' Identifier '{' (varDefList | functDef | constructorDef)* '}' ';'
	;

functDef
	: type Identifier '(' paraList ')' '{' stmt* '}'
    ;

varDefList
	: type varDef (',' varDef)* ';'
	;

varDef
	: Identifier ('=' expr)?
	;

paraList
	: 
	| para (',' para)*
	;

para
	: type Identifier ('=' expr)?
	;

constructorDef
	: Identifier '(' ')' '{' stmt* '}'
	;

type
	: varType
	| arrayType
	;

arrayType
	: varType ('[' ']')+
	;

varType
	: primType
	| Identifier
	;

primType
    : 'bool' | 'int' | 'string' | 'void'
    ;

block
	: '{' stmt* '}'
	;

stmt
	: block                                    # blockStmt
	| varDefList                               # varDefStmt 
	| 'if' '(' expr? ')' stmt ('else' stmt)?   # ifStmt
	| 'for' '(' init = expr? ';'  
			  cond = expr? ';'
			  step = expr? 
		  ')'
	  stmt                                     # forStmt
	| 'while' '(' expr? ')' stmt               # whileStmt   
	| 'return' expr? ';'                       # returnStmt
	| 'break' ';'                              # breakStmt
	| 'continue' ';'                           # continueStmt
	| expr ';'                                 # exprStmt
	| ';'                                      # brankStmt
	;
	
expr
 	: '(' expr ')'                                           # bracketExpr
 	|'this'                                                  # thisExpr 
	| literal                                                # literalExpr       
	| Identifier                                             # varExpr
	| 'new' creator                                          # creatorExpr            
	| expr '.' Identifier   							     # memberExpr                  
	| expr '[' expr ']'               		   	             # arrayExpr                                
	| expr '(' exprList? ')'                                 # functExpr              
	| expr op = ('++' | '--')                                # suffixExpr                      
	| op = ('+' | '-') expr                                  # prefixExpr                    
	| op = ('++' | '--') expr                                # prefixExpr                      
	| op = ('~' | '!') expr                                  # prefixExpr                    
	| expr op = ('*' | '/' | '%') expr                       # binaryExpr                               
	| expr op = ('+' | '-') expr                             # binaryExpr                         
	| expr op = ('<<' | '>>') expr                           # binaryExpr                           
	| expr op = ('<' | '>' | '<=' | '>=') expr               # binaryExpr       
	| expr op = ('==' | '!=') expr                           # binaryExpr        
	| expr op = '&' expr                                     # binaryExpr     
	| expr op = '^' expr                                     # binaryExpr     
	| expr op = '|' expr                                     # binaryExpr     
	| expr op = '&&' expr                                    # binaryExpr      
	| expr op = '||' expr                                    # binaryExpr              
	| <assoc = right> expr op = '=' expr                     # binaryExpr
	;        

exprList
	: (expr (',' expr)*)
	;

creator
	: varType ( ('[' ']')+ | ((('[' ']')|('[' expr ']'))* '[' ']' '[' expr ']' (('[' ']')|('[' expr ']'))*) )        # invalidCreator
	| varType ('[' expr ']')+ ('[' ']')*                                                                             # arrayCreator
	| varType '(' ')'                                                                                                # classCreator
	| varType                                                                                                        # naiveCreator
	;

literal
	: BoolLiteral   
	| IntLiteral
	| StringLiteral
	| 'null'
	;

BoolLiteral 
	: 'true' | 'false'
	;

IntLiteral 
    : [1-9] [0-9]*
    | '0'
    ;

StringLiteral 
	: '"' (ESC|.)*? '"'
	;

ESC: '\\"' | '\\n' | '\\\\';

Identifier: [a-zA-Z] [a-zA-Z_0-9]* ;

WS : [ \t]+  -> skip;

NewLine: '\r' ? '\n' -> skip;

LineComment : '//' ~[\r\n]* -> skip;

BlockComment : '/*' .*? '*/' -> skip;

