grammar Mxstar;

program 
	: definition* EOF
	;

definition
	: varDef
	| functDef
	| classDef
	;

varDef
	: varType ('[' ']')* (Identifier ('=' expr)?) (Identifier ('=' expr)?)* ';'
	;

classDef
	: CLASS Identifier '{' (varDef | functDef)* '}'
	;

functDef
	: (varType | VOID) Identifier '(' ( (varType Identifier ('=' expr)?) (',' varType Identifier ('=' expr)?)* )? ')' block
    ;

block
	: '{' stmt* '}'
	;

stmt
	: block                                  # blockStmt
	| expr ';'                               # exprStmt
	| varDef                                 # varDefStmt
	| IF '(' expr ')' stmt (ELSE stmt)?      # ifStmt
	| FOR '(' init = expr ';'  
			  cond = expr ';'
			  step = expr ';'
		  ')'
	  stmt                                   # forStmt
	| WHILE '(' expr ')' stmt                # whileStmt   
	| RETURN expr? ';'                       # returnStmt
	| BREAK ';'                              # breakStmt
	| CONTINUE ';'                           # contintueStmt
	| ';'                                    # brankstmt
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
	| expr op = '&&' expr                              # logicalExpr      
	| expr op = '||' expr                              # logicalExpr              
	| <assoc = right> expr '=' expr                    # assignExpr
	;        

creator
	: varType ('[' expr ']')+ ('[' ']')+ ('[' expr ']')+  # errorCreator
	| varType ('[' expr ']')+ ('[' ']')*                  # arrayCreator
	| Identifier                                           # nonarrayCreator
	;

varType
	: primitiveType
	| Identifier
	;

primitiveType
    : type = (BOOL | INT | STRING)
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
