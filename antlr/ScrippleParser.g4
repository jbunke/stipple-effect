parser grammar ScrippleParser;

options {
    tokenVocab=ScrippleLexer;
}

head_rule: FUNC signature LCURLY stat* RCURLY;

declaration: FINAL? type ident;

signature
: LPAREN param_list? RPAREN                 #VoidReturnSignature
| LPAREN param_list? ARROW type RPAREN      #TypeReturnSignature
;

param_list: declaration (COMMA declaration)*;

type
: BOOL                                      #BoolType
| INT                                       #IntType
| FLOAT                                     #FloatType
| CHAR                                      #CharType
| STRING                                    #StringType
| IMAGE                                     #ImageType
| COLOR                                     #ColorType
| type LBRACKET RBRACKET                    #ArrayType
| type LT GT                                #SetType
| type LCURLY RCURLY                        #ListType
| LCURLY type COLON type RCURLY             #MapType
;

body
: stat                                      #SingleStatBody
| LCURLY stat* RCURLY                       #ComplexBody
;

stat
: loop_stat                                 #LoopStatement
| if_stat                                   #IfStatement
// match_stat
| var_def SEMICOLON                         #VarDefStatement
| assignment SEMICOLON                      #AssignmentStatement
| return_stat                               #ReturnStatement
| expr ADD LPAREN expr (COMMA expr)?
  RPAREN SEMICOLON                          #AddToCollection
| expr REMOVE LPAREN expr RPAREN SEMICOLON  #RemoveFromCollection
| expr DEFINE LPAREN expr COMMA expr
  RPAREN SEMICOLON                          #DefineMapEntryStatement
| expr DRAW LPAREN expr COMMA expr
  COMMA expr RPAREN SEMICOLON               #DrawOntoImageStatement
;

return_stat: RETURN expr? SEMICOLON;

loop_stat
: while_def body                            #WhileLoop
| iteration_def body                        #IteratorLoop
| for_def body                              #ForLoop
| DO body while_def SEMICOLON               #DoWhileLoop
;

iteration_def: FOREACH LPAREN
declaration IN expr RPAREN;

while_def: WHILE LPAREN expr RPAREN;

for_def: FOR LPAREN var_init SEMICOLON
expr SEMICOLON assignment RPAREN;

if_stat: IF LPAREN expr RPAREN body
(ELSE if_stat)* (ELSE body)?;

expr
: LPAREN expr RPAREN                        #NestedExpression
| op=(MINUS | NOT | SIZE) expr              #UnaryExpression
| expr op=(PLUS | MINUS) expr               #ArithmeticBinExpression
| expr op=(TIMES | DIVIDE | MOD) expr       #MultBinExpression
| expr RAISE expr                           #PowerBinExpression
| expr op=(EQUAL | NOT_EQUAL |
  GT | LT | GEQ | LEQ) expr                 #ComparisonBinExpression
| expr op=(OR | AND) expr                   #LogicBinExpression
| expr QUESTION expr COLON expr             #TernaryExpression
| expr HAS LPAREN expr RPAREN               #ContainsExpression
| expr LOOKUP LPAREN expr RPAREN            #MapLookupExpression
| expr KEYS LPAREN RPAREN                   #MapKeysetExpression
| expr op=(RED | GREEN | BLUE | ALPHA)      #ColorChannelExpression
| FROM LPAREN expr RPAREN                   #ImageFromPathExpression
| BLANK LPAREN expr COMMA expr RPAREN       #ImageOfBoundsExpression
| expr PIXEL LPAREN expr COMMA expr RPAREN  #ColorAtPixelExpression
| expr op=(WIDTH | HEIGHT)                  #ImageBoundExpression
| RGB LPAREN expr COMMA expr
  COMMA expr RPAREN                         #RGBColorExpression
| RGBA LPAREN expr COMMA expr
  COMMA expr COMMA expr RPAREN              #RGBAColorExpression
| OF LPAREN expr (COMMA expr)* RPAREN       #ExplicitCollectionExpression
| NEW LBRACKET expr RBRACKET                #NewArrayExpression
| NEW LT GT                                 #NewListExpression
| NEW LCURLY RCURLY                         #NewSetExpression
| NEW LCURLY COLON RCURLY                   #NewMapExpression
| assignable                                #AssignableExpression
| literal                                   #LiteralExpression
;

assignment
: assignable ASSIGN expr                    #StandardAssignment
| assignable INCREMENT                      #IncrementAssignment
| assignable DECREMENT                      #DecrementAssignment
| assignable ADD_ASSIGN expr                #AddAssignment
| assignable SUB_ASSIGN expr                #SubAssignment
| assignable MUL_ASSIGN expr                #MultAssignment
| assignable DIV_ASSIGN expr                #DivAssignmnet
| assignable MOD_ASSIGN expr                #ModAssignment
| assignable AND_ASSIGN expr                #AndAssignment
| assignable OR_ASSIGN expr                 #OrAssignment
;

var_init: declaration ASSIGN expr;

var_def
: declaration                               #ImplicitVarDef
| var_init                                  #ExplicitVarDef
;

assignable
: ident                                     #SimpleAssignable
| ident LT expr GT                          #ListAssignable
| ident LBRACKET expr RBRACKET              #ArrayAssignable
;

ident: IDENTIFIER;

literal
: STRING_LIT                                #StringLiteral
| CHAR_LIT                                  #CharLiteral
| int_lit                                   #IntLiteral
| FLOAT_LIT                                 #FloatLiteral
| BOOL_LIT                                  #BoolLiteral
;

int_lit: HEX_LIT                            #Hexadecimal
| DEC_LIT                                   #Decimal
;
