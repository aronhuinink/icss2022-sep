grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-'; //BUGGED
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';


//--- PARSER: ---
stylesheet: (cssRule | variable)+ EOF;


//p{background-color: #ffffff; } WERKT (lvl 0)
cssRule : selector OPEN_BRACE declaration+ CLOSE_BRACE;
selector: CLASS_IDENT | ID_IDENT | LOWER_IDENT;
declaration: (property COLON value SEMICOLON) | ifStatement;
ifStatement: IF BOX_BRACKET_OPEN (boolLiteral | LOWER_IDENT | CAPITAL_IDENT) BOX_BRACKET_CLOSE OPEN_BRACE declaration+ CLOSE_BRACE (ELSE OPEN_BRACE declaration+ CLOSE_BRACE)?;
property: LOWER_IDENT;
value: datatype | ((datatype arithmeticOperator)+ datatype);
arithmeticOperator: PLUS | MUL;
datatype: color | pixelSize | percentage | boolLiteral  | CAPITAL_IDENT | integer;
integer: MIN? SCALAR;
boolLiteral: TRUE | FALSE;
color: COLOR;
pixelSize: PIXELSIZE;
percentage: PERCENTAGE;
variable: CAPITAL_IDENT ASSIGNMENT_OPERATOR value SEMICOLON;




SELECTOR:  'p' | 'a' OPEN_BRACE DECLARATIONS CLOSE_BRACE;
DECLARATIONS: DECLARATION+;
DECLARATION: 'background-color' | 'width' | 'color' COLON COLOR | PIXELSIZE | PERCENTAGE SEMICOLON CLOSE_BRACE;

