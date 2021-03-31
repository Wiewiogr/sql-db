grammar Sql;
statement:       selectStatement;
selectStatement: SELECT resultColumn (',' resultColumn)* FROM from;

resultColumn
  : anyName
  | ASTERISK
  ;
from: anyName;

anyName
 : IDENTIFIER
 | '(' anyName ')'
 ;

ASTERISK: '*';

SELECT: S E L E C T;
FROM: F R O M;

IDENTIFIER
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

WHITESPACES : [ \t\r\n]+ -> skip ;

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