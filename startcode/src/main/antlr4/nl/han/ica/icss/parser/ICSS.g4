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
selector: class_selector | id_selector | tag_selector;
class_selector: CLASS_IDENT;
id_selector: ID_IDENT;
tag_selector: LOWER_IDENT;
declaration: (declarationName COLON value SEMICOLON) | statement;
declarationName: LOWER_IDENT;
statement: ifStatement elseClause?;
ifStatement: IF BOX_BRACKET_OPEN (boolLiteral | LOWER_IDENT | CAPITAL_IDENT) BOX_BRACKET_CLOSE OPEN_BRACE declaration+ CLOSE_BRACE;//TODO maak een body if statement anders dan declaration en zet declaration weer aan
elseClause: ELSE OPEN_BRACE declaration+ CLOSE_BRACE;
value: datatype ((PLUS | MUL | MIN) datatype)*;
arithmeticOperator: PLUS | MUL | MIN;
datatype: color | pixelSize | percentage | boolLiteral  | literal | integer;
integer: MIN? SCALAR;
boolLiteral: TRUE | FALSE;
color: COLOR;
pixelSize: PIXELSIZE;
percentage: PERCENTAGE;
literal: CAPITAL_IDENT;
variable: variableName ASSIGNMENT_OPERATOR value SEMICOLON;//declaration
variableName: CAPITAL_IDENT;




SELECTOR:  'p' | 'a' OPEN_BRACE DECLARATIONS CLOSE_BRACE;
DECLARATIONS: DECLARATION+;
DECLARATION: 'background-color' | 'width' | 'color' COLON COLOR | PIXELSIZE | PERCENTAGE SEMICOLON CLOSE_BRACE;

