grammar Input;

data : ( dataInput newLine )+ ;

newLine : '\r'? '\n' ;

dataInput : ( botRule | valueRule ) ;

botRule : 'bot ' ID ' gives low to ' output ' and high to ' output ;

valueRule : 'value ' ID ' goes to ' output ;

output : outputOutput | botOutput ;

outputOutput : 'output ' ID;
botOutput : 'bot ' ID;

ID : ('0' .. '9')+ ;