grammar Mxstar;

@header {
	package parser;
}


program 
	: def* EOF
	;

def
	: classDef
	| varDefStmt
	| functDef
	;

classDef
	: CLASS Identifier '{' (varDefStmt | functDef)* '}'
	;

varDefStmt
	: type varDef (',' varDef)* ';'
	;

varDef
	: Identifier ('=' expr)?
	;

functDef
	: (type | VOID) Identifier '(' para* ')' block
    ;

para
	: type Identifier ('=' expr)?
	;

block
	: '{' stmt* '}'
	;

stmt
	: block                                  # blockStmt
	| expr ';'                               # exprStmt
	| varDefStmt                             # varStmt 
	| IF '(' expr ')' stmt (ELSE stmt)?      # ifStmt
	| FOR '(' init = expr? ';'  
			  cond = expr? ';'
			  step = expr? ';'
		  ')'
	  stmt                                   # forStmt
	| WHILE '(' expr ')' stmt                # whileStmt   
	| RETURN expr? ';'                       # returnStmt
	| BREAK ';'                              # breakStmt
	| CONTINUE ';'                           # continueStmt
	| ';'                                    # brankStmt
	;

expr
	: Identifier                                       # varExpr
	| THIS                                             # thisExpr 
	| literal                                          # literalExpr       
	| expr '.' Identifier                              # memberExpr                  
	| 'new' creator                                    # creatorExpr            
	| expr '[' expr ']'                                # arrayExpr               
	| expr '(' (expr (',' expr)*)? ')'                 # functExpr                                
	| '(' expr ')'                                     # bracketExpr           
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
	| <assoc = right> expr '=' expr                    # binaryExpr
	;        

creator
	: varType ('[' expr ']')+ ('[' ']')+ ('[' expr ']')+  # errorCreator
	| varType ('[' expr ']')+ ('[' ']')*                  # arrayCreator
	| Identifier                                           # nonarrayCreator
	;

type
	: varType
	| arrayType 
	;

arrayType
	: varType ('[' ']')*
	;

varType
	: primType
	| Identifier
	;

primType
    : BOOL | INT | STRING
    ;

literal
	: BoolLiteral   
	| IntLiteral
	| StringLiteral
	| NULL
	;

BoolLiteral 
	: TRUE | FALSE
	;

IntLiteral 
    : [1-9] [0-9]*
    | '0'
    ;


StringLiteral 
	: '"' (ESC|.)*? '"'
	;

ESC: '\\"' | '\\n' | '\\\\';

WS : [\t\n\r]+  -> skip;

Newline : ('\r' '\n'? | '\n') -> skip;

LineComment : '//' ~[\r\n]* -> skip;

BlockComment : '/*' .*? '*/' -> skip;

Identifier: [a-zA-Z_] [a-zA-Z_0-9]* ;

INT      : 'int'      ;          
BOOL     : 'bool'     ;     
STRING   : 'string'   ;       
NULL     : 'null'     ;     
VOID     : 'void'     ;     
TRUE     : 'true'     ;     
FALSE    : 'false'    ;      
IF       : 'if'       ;    
ELSE     : 'else'     ;      
FOR      : 'for'      ;    
WHILE    : 'while'    ;      
BREAK    : 'break'    ;      
CONTINUE : 'continue' ;         
RETURN   : 'return'   ;       
NEW      : 'new'      ;    
CLASS    : 'class'    ;      
THIS     : 'this'     ;      
