parser grammar ScrippleParser;

options {
    tokenVocab=ScrippleLexer;
}

head_rule: FUNC signature LCURLY stat* RCURLY;

declaration: FINAL? type ident;

signature
: LPAREN param_list? RPAREN                 #VOID_RETURN
| LPAREN param_list? ARROW type RPAREN      #TYPE_RETURN
;

param_list: declaration (COMMA declaration)*;

type
: BOOL                                      #BOOL_TYPE
| INT                                       #INT_TYPE
| FLOAT                                     #FLOAT_TYPE
| CHAR                                      #CHAR_TYPE
| STRING                                    #STRING_TYPE
| IMAGE                                     #IMAGE_TYPE
| COLOR                                     #COLOR_TYPE
| type LBRACKET RBRACKET                    #ARRAY_TYPE
| type LT GT                                #SET_TYPE
| type LCURLY RCURLY                        #LIST_TYPE
| LCURLY type COLON type RCURLY             #MAP_TYPE
;

body
: stat                                      #SINGLE_STAT_BODY
| LCURLY stat* RCURLY                       #COMPLEX_BODY
;

stat
: loop_stat                                 #LOOP_STAT
| if_stat                                   #IF_STAT
// match_stat
| var_def SEMICOLON                         #VAR_DEF_STAT
| assignment SEMICOLON                      #ASSIGNMENT_STAT
| return_stat                               #RETURN_STAT
| expr ADD LPAREN expr (COMMA expr)?
  RPAREN SEMICOLON                          #ADD_TO_COLLECTION
| expr REMOVE LPAREN expr RPAREN SEMICOLON  #REMOVE_FROM_COLLECTION
| expr DEFINE LPAREN expr COMMA expr
  RPAREN SEMICOLON                          #DEFINE_MAP_ENTRY
| expr DRAW LPAREN expr COMMA expr
  COMMA expr RPAREN SEMICOLON               #DRAW_ONTO_IMAGE
;

return_stat: RETURN expr? SEMICOLON;

loop_stat
: (while_def | iteration_def | for_def)
  body                                      #COND_FIRST_LOOP
| DO body while_def SEMICOLON               #COND_LAST_LOOP
;

iteration_def: FOREACH LPAREN
declaration IN expr RPAREN;

while_def: WHILE LPAREN expr RPAREN;

for_def: FOR LPAREN var_init SEMICOLON
expr SEMICOLON assignment RPAREN;

if_stat: IF LPAREN expr RPAREN body
(ELSE if_stat)* (ELSE body)?;

expr
: LPAREN expr RPAREN                        #NESTED_EXPR
| expr QUESTION expr COLON expr             #TERNARY_EXPR
| expr op=(PLUS | MINUS) expr               #ARITHMETIC_BIN_EXPR
| expr op=(TIMES | DIVIDE | MOD) expr       #MULT_BIN_EXPR
| expr RAISE expr                           #POWER_BIN_EXPR
| expr op=(EQUAL | NOT_EQUAL |
  GT | LT | GEQ | LEQ) expr                 #COMPARISON_BIN_EXPR
| expr op=(OR | AND) expr                   #LOGIC_BIN_EXPR
| op=(MINUS | NOT | SIZE) expr              #UNARY_EXPR
| expr HAS LPAREN expr RPAREN               #CONTAINS_EXPR
| expr LOOKUP LPAREN expr RPAREN            #MAP_GET_EXPR
| expr KEYS LPAREN RPAREN                   #MAP_KEYSET_EXPR
| expr op=(RED | GREEN | BLUE | ALPHA)      #GET_COLOR_CHANNEL
| FROM LPAREN expr RPAREN                   #IMAGE_FROM_PATH_EXPR
| BLANK LPAREN expr COMMA expr RPAREN       #IMAGE_OF_BOUNDS_EXPR
| expr PIXEL LPAREN expr COMMA expr RPAREN  #COLOR_AT_PIXEL_EXPR
| expr op=(WIDTH | HEIGHT)                  #IMAGE_BOUND_EXPR
| RGB LPAREN expr COMMA expr
  COMMA expr RPAREN                         #RGB_COLOR_EXPR
| RGBA LPAREN expr COMMA expr
  COMMA expr COMMA expr RPAREN              #RGBA_COLOR_EXPR
| OF LPAREN expr (COMMA expr)* RPAREN       #EXPLICIT_COLLECTION
| NEW LBRACKET expr RBRACKET                #NEW_ARRAY_EXPR
| NEW LT GT                                 #NEW_LIST_EXPR
| NEW LCURLY RCURLY                         #NEW_SET_EXPR
| NEW LCURLY COLON RCURLY                   #NEW_MAP_EXPR
| assignable                                #ASSIGNABLE_EXPR
| literal                                   #LIT_EXPR
;

assignment
: assignable ASSIGN expr                    #STANDARD_ASSIGNMENT
| assignable INCREMENT                      #INC_ASSIGNMENT
| assignable DECREMENT                      #DEC_ASSIGNMENT
| assignable ADD_ASSIGN expr                #ADD_ASSIGNMENT
| assignable SUB_ASSIGN expr                #SUB_ASSIGNMENT
| assignable MUL_ASSIGN expr                #MUL_ASSIGNMENT
| assignable DIV_ASSIGN expr                #DIV_ASSIGNMENT
| assignable MOD_ASSIGN expr                #MOD_ASSIGNMENT
| assignable AND_ASSIGN expr                #AND_ASSIGNMENT
| assignable OR_ASSIGN expr                 #OR_ASSIGNMENT
;

var_init: declaration ASSIGN expr;

var_def
: declaration                               #IMPLICIT_DEF
| var_init                                  #EXPLICIT_DEF
;

assignable
: ident                                     #ASSIGNABLE_VAR
| ident LT expr GT                          #ASSIGNABLE_LIST_ELEM
| ident LBRACKET expr RBRACKET              #ASSIGNABLE_ARR_ELEM
;

ident: IDENTIFIER;

literal
: STRING_LIT                                #STRING_LITERAL
| CHAR_LIT                                  #CHAR_LITERAL
| int_lit                                   #INT_LITERAL
| FLOAT_LIT                                 #FLOAT_LITERAL
| BOOL_LIT                                  #BOOL_LITERAL
;

int_lit: HEX_LIT                            #HEXADECIMAL
| DEC_LIT                                   #DECIMAL
;
