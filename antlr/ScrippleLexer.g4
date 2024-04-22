lexer grammar ScrippleLexer;

// Ignore whitespace and line comment
WS: [ \t\n]+ -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;

// Separators
LPAREN: '(';
RPAREN: ')';
LBRACKET: '[';
RBRACKET: ']';
LCURLY: '{';
RCURLY: '}';
SEMICOLON: ';';
COLON: ':';
COMMA: ',';
PERIOD: '.';
PIPE: '|';
QUESTION: '?';

// Assignment
ASSIGN: '=';
INCREMENT: '++';
DECREMENT: '--';
ADD_ASSIGN: '+=';
SUB_ASSIGN: '-=';
MUL_ASSIGN: '*=';
DIV_ASSIGN: '/=';
MOD_ASSIGN: '%=';
AND_ASSIGN: '&=';
OR_ASSIGN: '|=';
ARROW: '->';

// Binary Operators
EQUAL: '==';
NOT_EQUAL: '!=';
GT: '>';
LT: '<';
GEQ: '>=';
LEQ: '<=';
RAISE: '^';
PLUS: '+';
MINUS: '-';
TIMES: '*';
DIVIDE: '/';
MOD: '%';
AND: '&&';
OR: '||';

// Unary Operators
NOT: '!';
SIZE: '#';
// negative handled as minus

// Keywords
IN: 'in';
FUNC: 'func';
FINAL: 'final';
BOOL: 'bool';
FLOAT: 'float';
INT: 'int';
CHAR: 'char';
COLOR: 'color';
IMAGE: 'image';
STRING: 'string';
RETURN: 'return';
DO: 'do';
WHILE: 'while';
FOREACH: 'foreach';
FOR: 'for';
IF: 'if';
ELSE: 'else';
TRUE: 'true';
FALSE: 'false';

// Native call keywords
NEW: 'new';
OF: 'of';
FROM: 'from';
RGBA: 'rgba';
RGB: 'rgb';
BLANK: 'blank';

// Native field keywords
RED: '.' ('red' | 'r');
GREEN: '.' ('green' | 'g');
BLUE: '.' ('blue' | 'b');
ALPHA: '.' ('alpha' | 'a');
WIDTH: '.' ('width' | 'w');
HEIGHT: '.' ('height' | 'h');

// Native function keywords
HAS: '.has';
LOOKUP: '.lookup';
KEYS: '.keys';
PIXEL: '.pixel';
ADD: '.add';
REMOVE: '.remove';
DEFINE: '.define';
DRAW: '.draw';

BOOL_LIT: TRUE | FALSE;

// numbers
fragment DIGIT: '0'..'9';
fragment HEX_DIGIT: DIGIT | 'a'..'f';
FLOAT_LIT: (DIGIT+ '.' DIGIT+) | (DIGIT+ 'f');
DEC_LIT: DIGIT+;
HEX_LIT: '0x' HEX_DIGIT+;

CHAR_QUOTE: '\'';
STR_QUOTE: '"';

fragment RESTRICTED_ASCII: ~('\\' | '\'' | '"');

STRING_LIT: '"' (RESTRICTED_ASCII | ESC_CHAR | CHAR_QUOTE)* '"';
fragment CHARACTER: RESTRICTED_ASCII | ESC_CHAR;
CHAR_LIT: '\'' CHARACTER '\'';

ESC_CHAR: '\\' ('0' | 'b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\');

// Identifier
IDENTIFIER: [_A-Za-z] [_A-Za-z0-9]*;
