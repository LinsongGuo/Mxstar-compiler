grammar Mxstar;

@header {
	package parser;
}

program 
	: def* EOF
	;
  
def
	: functDef
	| classDef
	| varDefList 
	;

functDef
	: type Identifier '(' paraList ')' '{' stmt* '}'
    ;

classDef
	: 'class' Identifier '{' (varDefList | functDef | constructorDef)* '}' ';'
	;

varDefList
	: type varDef (',' varDef)* ';'
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

paraList
	: 
	| para (',' para)*
	;

para
	: type Identifier ('=' expr)?
	;

varDef
	: Identifier ('=' expr)?
	;

block
	: '{' stmt* '}'
	;

stmt
	: block                                      # blockStmt
	| varDefList                               # varDefStmt 
	| 'if' '(' expr? ')' stmt ('else' stmt)?      # ifStmt
	| 'for' '(' init = expr? ';'  
			  cond = expr? ';'
			  step = expr? 
		  ')'
	  stmt                                     # forStmt
	| 'while' '(' expr? ')' stmt                # whileStmt   
	| 'return' expr? ';'                       # returnStmt
	| 'break' ';'                              # breakStmt
	| 'continue' ';'                           # continueStmt
	| ';'                                      # brankStmt
	| expr ';'                                 # exprStmt
	;
	
expr
 	: 'new' creator                                          # creatorExpr            
	| expr '.' (identifierMember | functCall | arrayCall)    # memberExpr                  
	| functCall               		   				         # functExpr                                
	| arrayCall                                              # arrayExpr               
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
	| '(' expr ')'                                           # bracketExpr
	| 'this'                                                 # thisExpr 
	| literal                                                # literalExpr       
	| Identifier                                             # varExpr
	;        

identifierMember
	: Identifier
	;

arrayCall
	: Identifier ('[' expr ']')+
	;
	
functCall
	: Identifier '(' (expr (',' expr)*)? ')'
	;

creator
	: varType ('[' expr ']')+ ('[' ']')+ ('[' expr ']')+  # invalidCreator
	| varType ('[' expr ']')+ ('[' ']')*                  # arrayCreator
	| varType '(' ')'                                     # classCreator
	| varType                                             # naiveCreator
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

