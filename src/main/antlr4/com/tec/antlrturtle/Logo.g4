grammar Logo;

prog: EJECUTA OPENBRA logoExpression+ CLOSEBRA EOF;

colorExpression:
                blanco
                | azul
                | marron
                | cian
                | gris
                | amarillo
                | negro
                | verde
                | rojo
                ;

booleanExpression:
                mayorque            #MayorQueB
                |menorque           #MenorQueB
                |o                  #OB
                |y                  #YB
                |iguales            #IgualesG
                |TRUE               #TrueB
                |FALSE              #FalseB
                ;

intExpression:
                INT             #IntI
                |ID             #VariableI
                |inc            #IncI
                |rumbo          #RumboI
                |producto       #ProductoI
                |diferencia     #DiferenciaI
                |potencia       #PotenciaI
                |redondea       #RedondeaI
                |azar           #AzarI
                |menos          #MenosI
                |division       #DivisionI
                |resto          #RestoI
                |suma           #SumaI
                |cuenta         #CuentaI
                |ultimo         #UltimoI
                |elemento       #ElementoI
                |primero        #PrimeroI
                |elegir         #ElegirI
                ;

bloqExpression: logoExpression;

logoExpression: intExpression
                |colorExpression
                |booleanExpression
                |avanza
                |retrocede
                |giraderecha
                |giraizquierda
                |borrapantalla
                |ponxy
                |subelapiz
                |bajalapiz
                |resetAngle
                |ocultatortuga
                |aparecetortuga
                |haz
                |haz2
                |inic
                |ponx
                |pony
                |goma
                |centro
                |poncolorlapiz
                |espera
                |ponrumbo
                |repite
                |hasta
                |mientras
                |hazmientras
                |hazhasta
                |si
                |sisino
                ;


//Listas
cuenta: CUENTA lista;
elegir: ELEGIR lista;
primero:PRIMERO lista;
ultimo: ULTIMO lista;
elemento: ELEMENTO intExpression lista;

lista : OPENBRA intExpression* CLOSEBRA;

//ITERADORES
repite: REPITE intExpression OPENBRA logoExpression* CLOSEBRA;
hasta: HASTA OPENBRA (booleanExpression ) CLOSEBRA OPENBRA (logoExpression*) CLOSEBRA;
mientras: MIENTRAS OPENBRA (booleanExpression ) CLOSEBRA OPENBRA(logoExpression*)CLOSEBRA;
hazmientras:HAZMIENTRAS OPENBRA (logoExpression*) CLOSEBRA OPENBRA (booleanExpression ) CLOSEBRA;
hazhasta:HAZHASTA OPENBRA (logoExpression*) CLOSEBRA OPENBRA(booleanExpression )CLOSEBRA;

//Condicionales
si: SI booleanExpression OPENBRA logoExpression* CLOSEBRA;
sisino : SISINO booleanExpression   OPENBRA (logoExpression*) CLOSEBRA
                                  OPENBRA (bloqExpression*) CLOSEBRA;

//Booleans
mayorque: MAYORQUE intExpression intExpression;
menorque: MENORQUE intExpression intExpression;
iguales: IGUALES intExpression intExpression;
o: O booleanExpression booleanExpression;
y: Y booleanExpression booleanExpression;

//Colores
blanco: BLANCO;
azul: AZUL;
marron: MARRON;
cian: CIAN;
gris: GRIS;
amarillo: AMARILLO;
negro: NEGRO;
verde: VERDE;
rojo: ROJO;

//Operaciones matematicas
suma: SUMA intExpression+;
resto: RESTO intExpression intExpression;
division: DIVISION intExpression intExpression;
menos: MENOS intExpression;
azar: AZAR intExpression;
producto : PRODUCTO intExpression+ ;
diferencia: DIFERENCIA intExpression+;
potencia: POTENCIA intExpression intExpression;
redondea : REDONDEA intExpression;
inc: INC OPENBRA intExpression intExpression CLOSEBRA;

//Variables
inic: INIC ID EQUALS intExpression;
haz2: HAZ ID;
haz: HAZ ID intExpression;

//Tortuga
ocultatortuga: OCULTATORTUGA;
aparecetortuga: APARECETORTUGA;
avanza: AVANZA intExpression ;
retrocede: RETROCEDE intExpression ;
giraderecha: GIRADERECHA intExpression;
giraizquierda: GIRAIZQUIERDA intExpression;
borrapantalla: BORRAPANTALLA;
ponxy: PONXY intExpression intExpression;
subelapiz: SUBELAPIZ;
bajalapiz: BAJALAPIZ;
resetAngle: RESET_ANGLE;
espera: ESPERA intExpression;
centro : CENTRO;
poncolorlapiz: PONCOLORLAPIZ colorExpression;
goma: GOMA;
ponx : PONX intExpression;
pony : PONY intExpression;
rumbo: RUMBO;
ponrumbo: PONRUMBO intExpression;


// Reglas Lexicas
fragment MAQ: ('mayorque?' | 'maq');
fragment MEQ: ('menorque?' | 'meq');
fragment IG: ('iguales?' | 'ig');
fragment OB: ('O' | 'o');
fragment YB: ('Y' | 'y');
fragment SM: ('suma' | 'sm');
fragment RS: ('resto' | 'rs');
fragment DV: ('division' | 'dv');
fragment MN: ('menos' | 'mn');
fragment RM: ('azar' | 'rm');
fragment RD: ('redondea' | 'rd');
fragment POT: ('potencia' | 'pot');
fragment EP: ('espera' | 'ep');
fragment CT: ('centro' | 'centro');

fragment CB: ('blanco' | 'cb');
fragment CA: ('azul' | 'ca');
fragment CM: ('marron' | 'cm');
fragment CC: ('cian' | 'cc');
fragment CG: ('gris' | 'cg');
fragment CAM:('amarillo' | 'cam');
fragment CN: ('negro' | 'cn');
fragment CV: ('verde' | 'cv');
fragment CR: ('rojo' | 'cr');

fragment PM: ('producto'| 'pm');
fragment DR: ('diferencia' | 'dr');

fragment PCL:('poncolorlapiz' | 'poncl');
fragment GM: ('goma' | 'go');
fragment PX : ('ponx' | 'px');
fragment PY : ('pony' | 'py');
fragment R: ('rumbo' | 'r');
fragment PR: ('ponrumbo' | 'pr');
fragment AT: ('aparecetortuga' | 'at');
fragment OT: ('ocultatortuga' | 'ot');
fragment IC: ('inc'| 'ic');
fragment IN: ('inic' | 'in');
fragment HZ: ('haz' | 'hz');
fragment AV: ('avanza' | 'av');
fragment RE: ('retrocede' | 're');
fragment GI: ('giraizquierda' | 'gi');
fragment GD: ('giraderecha' | 'gd');
fragment BP: ('borrapantalla');
fragment XY: ('ponxy');
fragment SB: ('subelapiz' | 'sb');
fragment BL: ('bajalapiz' | 'bl');

fragment EG: ('elegir'| 'elg');
fragment CUE: ('cuenta' | 'cu');
fragment EL: ('elemento' | 'el');
fragment UL: ('ultimo' | 'ul');
fragment PRI: ('primero' | 'pri');
fragment S: ('si' | 's');
fragment SSN: ('sisino' | 'ssn');
fragment REP: ('repite' | 'rep');
fragment HST: ('hasta' | 'hst');
fragment MNTR: ('mientras' | 'mntr');
fragment HHST: ('haz.hasta' | 'hhst');
fragment HMNTR: ('haz.mientras' | 'hmntr');

MAYORQUE: MAQ;
MENORQUE: MEQ;
IGUALES: IG;
Y : YB;
O : OB;
ESPERA: EP;
CENTRO : CT;
BLANCO : CB;
AZUL : CA;
MARRON : CM;
CIAN : CC;
GRIS : CG;
AMARILLO : CAM;
NEGRO : CN;
ROJO : CR;
VERDE : CV;

RESTO: RS;
SUMA : SM;
DIVISION : DV;
AZAR: RM;
MENOS: MN;
REDONDEA: RD;
PRODUCTO: PM;
DIFERENCIA: DR;
POTENCIA: POT;

CUENTA : CUE;
ELEGIR : EG;
ELEMENTO :EL;
ULTIMO:UL;
PRIMERO:PRI;

SI:S;
REPITE: REP;
SISINO:SSN;

HASTA:HST;
MIENTRAS:MNTR;
HAZHASTA:HHST;
HAZMIENTRAS:HMNTR;

PONCOLORLAPIZ: PCL;
GOMA: GM;
PONX: PX;
PONY : PY;
RUMBO: R;
PONRUMBO: PR;
APARECETORTUGA : AT;
OCULTATORTUGA : OT;
INC : IC;
INIC : IN;
HAZ : HZ;
AVANZA: AV;
RETROCEDE: RE;
GIRAIZQUIERDA: GI;
GIRADERECHA: GD;
BORRAPANTALLA: BP;
PONXY: XY;
SUBELAPIZ: SB;
BAJALAPIZ: BL;

EJECUTA : 'ejecuta';

TRUE: 'true';
FALSE: 'false';
RESET_ANGLE: 'resetAngle';

OPENBRA : '[';
CLOSEBRA : ']';
EQUALS : '=';

INT: [0-9]+ ;
ID:[a-zA-Z_][a-zA-Z0-9_]*;
STRING: '"' ~('"')* '"';

NEWLINE: '\r'? '\n' -> skip;
WS: ' '+ -> skip;
COMMENT: '//' ~[\n]* -> skip;