grammar Mxstar;

@header {
	package parser;
}

program 
	: def* EOF
	;
  
def
	: classDef
	| varDefList
	| functDef
	;

classDef
	: 'class' Identifier '{' (varDefList | functDef | constructorDef)* '}' ';'
	;

constructorDef
	: Identifier '(' ')' block
	;
	
varDefList
	: type varDef (',' varDef)* ';'
	;

varDef
	: Identifier ('=' expr)?
	;

functDef
	: type Identifier '(' paraList ')' block
    ;

paraList
	: 
	| para (',' para)*
	;

para
	: type Identifier ('=' expr)?
	;

block
	: '{' stmt* '}'
	;

stmt
	: block                                  # blockStmt
	| 'if' '(' expr ')' stmt ('else' stmt)?      # ifStmt
	| 'for' '(' init = expr? ';'  
			  cond = expr? ';'
			  step = expr? 
		  ')'
	  stmt                                   # forStmt
	| 'while' '(' expr ')' stmt                # whileStmt   
	| 'return' expr? ';'                     	 # returnStmt
	| 'break' ';'                              # breakStmt
	| 'continue' ';'                           # continueStmt
	| varDefList                             # varDefStmt 
	| expr ';'                               # exprStmt

	| ';'                                    # brankStmt
	;
	
exprList
	: (expr (',' expr)*)?
	;

expr
 	: '(' expr ')'                                     # bracketExpr           
	| 'this'                                           # thisExpr 
	| 'new' creator                                    # creatorExpr            
	| literal                                          # literalExpr       
	| Identifier                                       # identifierExpr
	| expr '.' Identifier                              # memberExpr                  
	| expr '[' expr ']'                                # arrayExpr               
	| expr '(' exprList ')'                 		   # functExpr                                
	| expr op = ('++' | '--')                          # suffixExpr                      
	| op = ('+' | '-') expr                            # prefixExpr                    
	| op = ('++' | '--') expr                          # prefixExpr                      
	| op = ('~' | '!') expr                            # prefixExpr                    
	| expr op = ('*' | '/' | '%') expr                 # binaryExpr                               
	| expr op = ('+' | '-') expr                       # binaryExpr                         
	| expr op = ('<<' | '>>') expr                     # binaryExpr                           
	| expr op = ('<' | '>' | '<=' | '>=') expr         # binaryExpr       
	| expr op = ('==' | '!=') expr                     # binaryExpr        
	| expr op = '&' expr                               # binaryExpr     
	| expr op = '^' expr                               # binaryExpr     
	| expr op = '|' expr                               # binaryExpr     
	| expr op = '&&' expr                              # binaryExpr      
	| expr op = '||' expr                              # binaryExpr              
	| <assoc = right> expr op = '=' expr               # binaryExpr
	;        
	
creator
	: varType ('[' expr ']')+ ('[' ']')+ ('[' expr ']')+  # invalidCreator
	| varType ('[' expr ']')+ ('[' ']')*                  # arrayCreator
	| varType '(' ')'                                     # classCreator
	| varType                                             # naiveCreator
	;

type
	: varType
	| arrayType 
	;

varType
	: primType
	| Identifier
	;

arrayType
	: varType ('[' ']')*
	;

primType
    : 'bool' | 'int' | 'string' | 'void'
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

Identifier: [a-zA-Z_] [a-zA-Z_0-9]* ;

WS : [ \t]+  -> skip;

NewLine: '\r' ? '\n' -> skip;

LineComment : '//' ~[\r\n]* -> skip;

BlockComment : '/*' .*? '*/' -> skip;

