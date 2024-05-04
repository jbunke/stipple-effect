parser grammar ScriptParser;

options {
    tokenVocab=ScriptLexer;
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
| LCURLY key=type COLON val=type RCURLY     #MapType
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
| col=expr ADD LPAREN elem=expr
  (COMMA index=expr)? RPAREN SEMICOLON      #AddToCollection
| col=expr REMOVE LPAREN
  arg=expr RPAREN SEMICOLON                 #RemoveFromCollection
| map=expr DEFINE LPAREN key=expr
  COMMA val=expr RPAREN SEMICOLON           #DefineMapEntryStatement
| canvas=expr DRAW LPAREN img=expr COMMA
  x=expr COMMA y=expr RPAREN SEMICOLON      #DrawOntoImageStatement
| canvas=expr DOT LPAREN col=expr COMMA
  x=expr COMMA y=expr RPAREN SEMICOLON      #DotStatement
| canvas=expr LINE LPAREN col=expr COMMA
  breadth=expr COMMA x1=expr COMMA y1=expr
  COMMA x2=expr COMMA y2=expr RPAREN
  SEMICOLON                                 #DrawLineStatement
| canvas=expr FILL LPAREN col=expr COMMA
  x=expr COMMA y=expr COMMA w=expr COMMA
  h=expr RPAREN SEMICOLON                   #FillStatement
;

return_stat: RETURN expr? SEMICOLON;

loop_stat
: while_def body                            #WhileLoop
| iteration_def body                        #IteratorLoop
| for_def body                              #ForLoop
| DO body while_def SEMICOLON               #DoWhileLoop
;

iteration_def: FOR LPAREN
declaration IN expr RPAREN;

while_def: WHILE LPAREN expr RPAREN;

for_def: FOR LPAREN var_init SEMICOLON
expr SEMICOLON assignment RPAREN;

if_stat: if_def (ELSE if_def)*
(ELSE elseBody=body)?;

if_def: IF LPAREN cond=expr RPAREN body;

expr
: LPAREN expr RPAREN                        #NestedExpression
| col=expr HAS LPAREN elem=expr RPAREN      #ContainsExpression
| map=expr LOOKUP LPAREN elem=expr RPAREN   #MapLookupExpression
| map=expr KEYS LPAREN RPAREN               #MapKeysetExpression
| c=expr op=(RED | GREEN | BLUE | ALPHA)    #ColorChannelExpression
| FROM LPAREN expr RPAREN                   #ImageFromPathExpression
| BLANK LPAREN width=expr
  COMMA height=expr RPAREN                  #ImageOfBoundsExpression
| TEX_COL_REPL LPAREN texture=expr COMMA
  lookup=expr COMMA replace=expr RPAREN     #TextureColorReplaceExpression
| GEN_LOOKUP LPAREN source=expr COMMA
  vert=expr RPAREN                          #GenLookupExpression
| img=expr SECTION LPAREN x=expr COMMA
  y=expr COMMA w=expr COMMA h=expr RPAREN   #ImageSectionExpression
| img=expr PIXEL LPAREN x=expr
  COMMA y=expr RPAREN                       #ColorAtPixelExpression
| expr op=(WIDTH | HEIGHT)                  #ImageBoundExpression
| RGB LPAREN r=expr COMMA g=expr
  COMMA b=expr RPAREN                       #RGBColorExpression
| RGBA LPAREN r=expr COMMA g=expr
  COMMA b=expr COMMA a=expr RPAREN          #RGBAColorExpression
| string=expr AT LPAREN index=expr RPAREN   #CharAtExpression
| string=expr SUB LPAREN beg=expr
  COMMA end=expr RPAREN                     #SubstringExpression
| op=(MINUS | NOT | SIZE) expr              #UnaryExpression
| a=expr op=(PLUS | MINUS) b=expr           #ArithmeticBinExpression
| a=expr op=(TIMES | DIVIDE | MOD) b=expr   #MultBinExpression
| a=expr RAISE b=expr                       #PowerBinExpression
| a=expr op=(EQUAL | NOT_EQUAL |
  GT | LT | GEQ | LEQ) b=expr               #ComparisonBinExpression
| a=expr op=(OR | AND) b=expr               #LogicBinExpression
| cond=expr QUESTION if=expr
  COLON else=expr                           #TernaryExpression
| LBRACKET expr (COMMA expr)* RBRACKET      #ExplicitArrayExpression
| LT expr (COMMA expr)* GT                  #ExplicitListExpression
| LCURLY expr (COMMA expr)* RCURLY          #ExplicitSetExpression
| NEW type LBRACKET expr RBRACKET           #NewArrayExpression
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
| bool_lit                                  #BoolLiteral
;

int_lit: HEX_LIT                            #Hexadecimal
| DEC_LIT                                   #Decimal
;

bool_lit: TRUE                              #True
| FALSE                                     #False
;
