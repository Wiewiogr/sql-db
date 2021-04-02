grammar Sql;

statement
  : selectStatement
  | insertStatement
  ;

selectStatement: SELECT resultColumn (',' resultColumn)* FROM from;
insertStatement: INSERT INTO table VALUES '(' value (',' value )* ')';

resultColumn
  : anyName
  | ASTERISK
  ;
from: IDENTIFIER;
table: IDENTIFIER;

value
  : NUMERIC_LITERAL
  | STRING_LITERAL
  | BOOLEAN_LITERAL
  ;

anyName
  : IDENTIFIER
  | '(' anyName ')'
  ;

ASTERISK: '*';

BOOLEAN_LITERAL
  : TRUE
  | FALSE
  ;

TRUE: T R U E;
FALSE: F A L S E;
SELECT: S E L E C T;
FROM: F R O M;
INSERT: I N S E R T;
INTO: I N T O;
VALUES: V A L U E S;

IDENTIFIER
  : [a-zA-Z_] [a-zA-Z_0-9]*
  ;

STRING_LITERAL
  : '\'' ( ~'\'' | '\'\'')* '\''
  ;

NUMERIC_LITERAL
  : INTEGER_LITERAL
  | DECIMAL_LITERAL
  ;

INTEGER_LITERAL
  : DIGIT+
  ;

DECIMAL_LITERAL
  : DIGIT+ '.' DIGIT*
  ;

WHITESPACES : [ \t\r\n]+ -> skip ;

fragment DIGIT: [0-9];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];