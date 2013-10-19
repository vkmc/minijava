package SemanticAnalyzer;

import java.util.LinkedList;

/**
 * Analizador sintáctico. Transforma su entrada en un arbol de derivación.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Parser {

    private Tokenizer tokenizer;
    private Token lookAhead, currentToken;
    public SymbolTable symbolTable;

    // analizador sintactico
    /**
     * Constructor de la clase Parser
     *
     * Inicializa las estructuras de datos utilizadas.
     *
     * @param filename Path del archivo con el código fuente
     */
    public Parser(String filename) {
        tokenizer = new Tokenizer(filename);
        symbolTable = new SymbolTable();
    }

    /**
     * Inicia el proceso de análisis obteniendo el primer token y procesando la
     * regla inicial de la gramática.
     *
     * @throws SyntacticException
     * @throws LexicalException
     */
    public void analize() throws SyntacticException, LexicalException, SemanticException {
        lookAhead = tokenizer.getToken();
        Inicial();
    }

    /**
     * Compara el token encontrado con lo que se esperaba de acuerdo a la
     * gramatica. En caso de encontrar un token no esperado o el fin de linea
     * durante el proceso de analisis, se procedera a detenerlo y a notificar el
     * error.
     *
     * @param token
     * @throws LexicalException
     * @throws SyntacticException
     */
    public void match(String token) throws LexicalException, SyntacticException {
        if (lookAhead.getToken().equals(token)) {
            if (!token.equals("EOF")) {
                currentToken = lookAhead;
                lookAhead = tokenizer.getToken();
            } else {
                System.err.println("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se alcanzo el fin de archivo durante el analisis sintactico.");
            }
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba: '" + token + "'. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    // Todos los metodos que siguen corresponden a las reglas de la gramatica de MiniJava
    // Existe un metodo por no-terminal de la gramatica, y el proceso se lleva adelante siguiendo el flujo de ejecucion normal
    private void Inicial() throws LexicalException, SyntacticException, SemanticException {
        symbolTable.setup();
        Clase();
        ListaClases();
    }

    private void ListaClases() throws LexicalException, SyntacticException, SemanticException {
        if (lookAhead.equals("class")) {
            Clase();
            ListaClases();
        } else if (lookAhead.equals("EOF")) {
            System.out.println("El analizador sintactico termino exitosamente.");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba la palabra reservada 'class'. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void Clase() throws LexicalException, SyntacticException, SemanticException {
        match("class");
        if (!lookAhead.equals("id")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el nombre de la clase. Se encontro: '" + lookAhead.getToken() + "'.");
        }
        match("id");
        String className = currentToken.getLexeme();
        if (symbolTable.getClassEntry(className) == null) {
            symbolTable.addClass(className);
            symbolTable.setCurrentClass(className);
        } else {
            throw new SemanticException("Linea: " + currentToken.getLineNumber() + " - Error semantico: Ya existe una clase declarada con el nombre " + className);
        }
        Herencia();
        // el control de { lo hace Herencia()
        match("{");
        ListaMiembros("class");
        symbolTable.getClassEntry(className).controlDefaultConstructor();
        // el control de } lo hace ListaMiembros()
        match("}");
    }

    private void Herencia() throws LexicalException, SyntacticException, SemanticException {
        String className = symbolTable.getCurrentClass();
        ClassEntry classEntry = symbolTable.getClassEntry(className); 
        if (lookAhead.equals("extends")) {
            match("extends");
            match("id");
            String parentClass = currentToken.getLexeme();
            classEntry.addParent(parentClass);
            classEntry.setParent(parentClass);
            if (symbolTable.controlInheritance(className)) {
                throw new SemanticException("Linea: " + currentToken.getLineNumber() + " - Error semantico: Herencia circular.");
            }
        } else if (lookAhead.equals("{")) {
            // Herencia -> lambda
            // No hay herencia
            classEntry.addParent("Object");
            classEntry.setParent("Object");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el comienzo de la clase '{' o la especificacion de herencia. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void ListaMiembros(String from) throws LexicalException, SyntacticException, SemanticException {
        if (lookAhead.equals("}")) {
            // ListaMiembros -> lambda
            // No hay mas miembros
        } else {
            Miembro(from);
            ListaMiembros(from);
            // Para no duplicar codigo dejamos que el control
            // de lo haga Miembro()
        }
    }

    private void Miembro(String from) throws LexicalException, SyntacticException, SemanticException {
        if (lookAhead.equals("var")) {
            Atributo(from);
        } else if (lookAhead.equals("id")) {
            Ctor();
        } else if (lookAhead.equals("static") || lookAhead.equals("dynamic")) {
            Metodo();
        } else if (isType(lookAhead)) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: No se encontro el modificador del metodo.");
        } else if (lookAhead.equals("EOF")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el cierre de la clase '}'. Se encontro: '" + lookAhead.getToken() + "'.");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba la definicion de atributos, constructores o metodos. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void Atributo(String from) throws LexicalException, SyntacticException {
        match("var");
        String type = Tipo();
        ListaDecVars(from, type);
        if (!lookAhead.equals(";")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el terminador de la lista de variables ';'. Se encontro: '" + lookAhead.getToken() + "'.");
        }
        match(";");
    }

    private void Metodo() throws LexicalException, SyntacticException, SemanticException {
        String modificator = ModMetodo();
        String type = TipoMetodo();
        match("id");
        String methodName = currentToken.getLexeme();
        symbolTable.setCurrentMethod(methodName);
        String currentClass = symbolTable.getCurrentClass();
        ClassEntry classEntry = symbolTable.getClassEntry(currentClass);
        if (methodName.equals(currentClass)) {
            throw new SemanticException("Linea: " + lookAhead.getLineNumber() + " - Error semantico: El metodo no puede tener el mismo nombre que la clase.");
        } else if (classEntry.getMethodEntry(methodName) != null) {
            throw new SemanticException("Linea: " + lookAhead.getLineNumber() + " - Error semantico: Ya existe un metodo " + methodName + " declarado en la clase " + currentClass);
        } else {
            classEntry.addMethodEntry(methodName, type, modificator);
        }
        ArgsFormales();
        VarsLocales("method");
        BlockNode body = Bloque();
        classEntry.getMethodEntry(methodName).setBody(body);
    }

    private void Ctor() throws LexicalException, SyntacticException, SemanticException {
        match("id");
        String constructorName = currentToken.getLexeme();
        symbolTable.setCurrentMethod(constructorName);
        String currentClass = symbolTable.getCurrentClass();
        ClassEntry classEntry = symbolTable.getClassEntry(currentClass);
        if (!currentClass.equals(constructorName)) {
            throw new SemanticException("Linea: " + lookAhead.getLineNumber() + " - Error semantico: El nombre del constructor no corresponde al nombre de la clase.");
        } else if (classEntry.getConstructorEntry() != null ) {
            throw new SemanticException("Linea: " + lookAhead.getLineNumber() + " - Error semantico: Ya existe un constructor en la clase " + currentClass + ".");
        } else {
           classEntry.setConstructorEntry(constructorName);
        }
        ArgsFormales();
        VarsLocales("method");
        BlockNode body = Bloque();
        classEntry.getConstructorEntry().setBody(body);
    }

    private void ArgsFormales() throws LexicalException, SyntacticException, SemanticException {
        if (lookAhead.equals("(")) {
            match("(");
            ArgsFormales_();
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba la apertura de una lista de argumentos formales '('. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void ArgsFormales_() throws LexicalException, SyntacticException, SemanticException {
        if (lookAhead.equals(")")) {
            // ArgsFormales_ -> )
            // No hay mas argumentos formales
            match(")");
        } else {
            ListaArgsFormales();
            if (lookAhead.equals(")")) {
                match(")");
            } else {
                throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el cierre de la lista de argumentos formales ')'. Se encontro: '" + lookAhead.getToken() + "'.");
            }
        }
    }

    private void ListaArgsFormales() throws LexicalException, SyntacticException, SemanticException {
        ArgFormal();
        ListaArgsFormales_();
    }

    private void ListaArgsFormales_() throws LexicalException, SyntacticException, SemanticException {
        if (lookAhead.equals(")")) {
            // ListaArgsFormales_ -> lambda
            // No hay mas argumentos formales
        } else if (lookAhead.equals(",")) {
            match(",");
            ListaArgsFormales();
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba un nuevo argumento formal o el cierre de la lista de argumentos formales ')'. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void ArgFormal() throws LexicalException, SyntacticException, SemanticException {
        String type = Tipo();
        match("id");
        String parameterName = currentToken.getLexeme();
        String currentClass = symbolTable.getCurrentClass();
        String currentMethod = symbolTable.getCurrentMethod();
        ServiceEntry serviceEntry = symbolTable.getClassEntry(currentClass).getMethodEntry(currentMethod);
        if (serviceEntry.getParameterEntry(parameterName) != null) {
            throw new SemanticException("Linea: " + lookAhead.getLineNumber() + " - Error semantico: Ya existe un argumento formal con el nombre " + parameterName + " en la clase " + currentClass);
        } else {
            serviceEntry.addParameterEntry(parameterName, type);
        }
    }

    private void VarsLocales(String from) throws LexicalException, SyntacticException {
        ListaAtributos(from);
    }

    private void ListaAtributos(String from) throws LexicalException, SyntacticException {
        if (lookAhead.equals("{")) {
            // ListaAtributos -> lambda
            // Bloque
            // No hay mas atributos
        } else if (lookAhead.equals("var")) {
            Atributo(from);
            ListaAtributos(from);
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el comienzo de bloque del metodo '{' o la definicion de variables locales. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private String ModMetodo() throws LexicalException, SyntacticException {
        if (lookAhead.equals("static")) {
            match("static");
            return "static";
        } else {
            match("dynamic");
            return "dynamic";
        }
    }

    private String TipoMetodo() throws LexicalException, SyntacticException {
        String type;
        if (lookAhead.equals("void")) {
            match("void");
            type = "void";
        } else {
            type =  Tipo();
        }
        return type;
    }

    private String Tipo() throws LexicalException, SyntacticException {
        String type;
        if (lookAhead.equals("id")) {
            match("id");
            type = currentToken.getLexeme();
        } else {
            type = TipoPrimitivo();
        }
        return type;
    }

    private String TipoPrimitivo() throws LexicalException, SyntacticException {
        String type;
        if (lookAhead.equals("boolean")) {
            match("boolean");
            type = "boolean";
        } else if (lookAhead.equals("char")) {
            match("char");
            type = "char";
        } else if (lookAhead.equals("int")) {
            match("int");
            type = "int";
        } else if (lookAhead.equals("String")) {
            match("String");
            type = "String";
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el tipo de la variable. Se encontro: '" + lookAhead.getToken() + "'.");
        }
        return type;
    }

    private void ListaDecVars(String from, String type) throws LexicalException, SyntacticException {
        if (lookAhead.equals("id")) {
            match("id");
            if (from.equals("clase")) {
            //////////////////////////////
            ////////////////////////////
            ///////////////////////////////
            ////FALTA IMPLEMENTAR
            }
            ListaDecVars_(from, type);
        } else if (lookAhead.equals(",")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Falta especificar el tipo de las variables declaradas."); // caso: var v1, v2;                                                                                                                                                 // v1 se toma como tipo  
        }
    }

    private void ListaDecVars_(String from, String type) throws LexicalException, SyntacticException {
        if (lookAhead.equals(";")) {
            // ListaDecVars_ -> lambda
            // No hay mas variables declaradas
        } else if (lookAhead.equals(",")) {
            match(",");
            ListaDecVars(from, type);
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba una variable. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private BlockNode Bloque() throws LexicalException, SyntacticException {
        if (lookAhead.equals("{")) {
            match("{");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba la apertura de un bloque '}'. Se encontro: '" + lookAhead.getToken() + "'.");
        }
        LinkedList<SentenceNode> sentenceList = ListaSentencias();
        if (lookAhead.equals("}")) {
            match("}");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el cierre de un bloque '{'. Se encontro: '" + lookAhead.getToken() + "'.");
        }
        BlockNode block = new BlockNode(sentenceList);
        return block;
    }

    private LinkedList<SentenceNode> ListaSentencias() throws LexicalException, SyntacticException {
        if (lookAhead.equals("}")) {
            // ListaSentencias -> lambda
            // No hay mas sentencias
        } else {
            Sentencia();
            // Delego el control de terminales o no-terminales a Sentencia()
            ListaSentencias();
        }
        return null;
    }

    private void Sentencia() throws LexicalException, SyntacticException {
        if (lookAhead.equals(";")) {
            match(";");
        } else if (lookAhead.equals("id")) {
            Asignacion();
            match(";");
        } else if (lookAhead.equals("(")) {
            SentenciaSimple();
            match(";");
        } else if (lookAhead.equals("if")) {
            match("if");
            match("(");
            Expresion();
            match(")");
            Sentencia();
            Sentencia_();
        } else if (lookAhead.equals("while")) {
            match("while");
            match("(");
            Expresion();
            match(")");
            Sentencia();
        } else if (lookAhead.equals("for")) {
            match("for");
            match("(");
            Asignacion();
            match(";");
            Expresion();
            match(";");
            Expresion();
            match(")");
            Sentencia();
        } else if (lookAhead.equals("{")) {
            Bloque();
        } else if (lookAhead.equals("return")) {
            match("return");
            Sentencia__();
            match(";");
        } else if (lookAhead.equals("var")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: No pueden declararse variables dentro de un bloque.");
        } else if (lookAhead.equals("=")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Falta el lado izquierdo de la asignacion.");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba una sentencia o el cierre de bloque.");
        }
    }

    private void Sentencia_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("else")) {
            match("else");
            Sentencia();
        } else {
            // Sentencia_ -> lambda
            // if-then sin else
        }
    }

    private void Sentencia__() throws LexicalException, SyntacticException {
        if (lookAhead.equals(";")) {
            // Sentencia__ -> lambda
        } else {
            Expresion();
        }
    }

    private void Asignacion() throws LexicalException, SyntacticException {
        match("id");
        match("=");
        Expresion();
    }

    private void SentenciaSimple() throws LexicalException, SyntacticException {
        if (!lookAhead.equals("(")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba la apertura de una expresion parentizada '('. Se encontro: '" + lookAhead.getToken() + "'.");
        }
        match("(");
        Expresion();
        if (!lookAhead.equals(")")) {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el cierre de una expresion parentizada ')'. Se encontro: '" + lookAhead.getToken() + "'.");
        }
        match(")");
    }

    private void Expresion() throws LexicalException, SyntacticException {
        Expresion6();
    }

    private void Expresion6() throws LexicalException, SyntacticException {
        Expresion5();
        Expresion6_();
    }

    private void Expresion6_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("||")) {
            match("||");
            Expresion5();
            Expresion6_();
        } else {
            // Expresion6_ -> lambda
        }
    }

    private void Expresion5() throws LexicalException, SyntacticException {
        Expresion4();
        Expresion5_();
    }

    private void Expresion5_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("&&")) {
            match("&&");
            Expresion4();
            Expresion5_();
        } else {
            // Expresion5_ -> lambda
        }
    }

    private void Expresion4() throws LexicalException, SyntacticException {
        Expresion3();
        Expresion4_();
    }

    private void Expresion4_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("==") || lookAhead.equals("!=")) {
            Operador4();
            Expresion3();
            Expresion4_();
        } else {
            // Expresion4_ -> lambda
        }
    }

    private void Expresion3() throws LexicalException, SyntacticException {
        Expresion2();
        Expresion3_();
    }

    private void Expresion3_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("<") || lookAhead.equals(">") || lookAhead.equals(">=") || lookAhead.equals("<=")) {
            Operador3();
            Expresion2();
            Expresion3_();
        } else {
            // Expresion3_ -> lambda
        }
    }

    private void Expresion2() throws LexicalException, SyntacticException {
        Expresion1();
        Expresion2_();
    }

    private void Expresion2_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("+") || lookAhead.equals("-")) {
            Operador2();
            Expresion1();
            Expresion2_();
        } else {
            // Expresion2_ -> lambda
        }
    }

    private void Expresion1() throws LexicalException, SyntacticException {
        Expresion0();
        Expresion1_();
    }

    private void Expresion1_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("*") || lookAhead.equals("/") || lookAhead.equals("%")) {
            Operador1();
            Expresion0();
            Expresion1_();
        } else {
            // Expresion1_ -> lambda
        }
    }

    private void Expresion0() throws LexicalException, SyntacticException {
        if (lookAhead.equals("!") || lookAhead.equals("+") || lookAhead.equals("-")) {
            OperadorUnario();
            Expresion0();
        } else {
            Primario();
        }
    }

    private void Operador4() throws LexicalException, SyntacticException {
        if (lookAhead.equals("==")) {
            match("==");
        } else if (lookAhead.equals("!=")) {
            match("!=");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba == o != . Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void Operador3() throws LexicalException, SyntacticException {
        if (lookAhead.equals("<")) {
            match("<");
        } else if (lookAhead.equals(">")) {
            match(">");
        } else if (lookAhead.equals(">=")) {
            match(">=");
        } else if (lookAhead.equals("<=")) {
            match("<=");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba <, >, <=, >= . Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void Operador2() throws LexicalException, SyntacticException {
        if (lookAhead.equals("+")) {
            match("+");
        } else if (lookAhead.equals("-")) {
            match("-");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba + o - . Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void Operador1() throws LexicalException, SyntacticException {
        if (lookAhead.equals("*")) {
            match("*");
        } else if (lookAhead.equals("/")) {
            match("/");
        } else if (lookAhead.equals("%")) {
            match("%");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba *, / o % . Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void OperadorUnario() throws LexicalException, SyntacticException {
        if (lookAhead.equals("!")) {
            match("!");
        } else if (lookAhead.equals("+")) {
            match("+");
        } else if (lookAhead.equals("-")) {
            match("-");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba !, + o - . Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void Primario() throws LexicalException, SyntacticException {
        if (lookAhead.equals("this")) {
            match("this");
        } else if (lookAhead.equals("(")) {
            match("(");
            Expresion();
            match(")");
            ListaLlamadas();
        } else if (lookAhead.equals("id")) {
            match("id");
            ListaLlamadas_();
        } else if (lookAhead.equals("new")) {
            match("new");
            match("id");
            ArgsActuales();
            ListaLlamadas();
        } else if (lookAhead.equals("id")) {
            match("id");
            ArgsActuales();
            ListaLlamadas();
        } else {
            Literal();
        }
    }

    private void ListaLlamadas() throws LexicalException, SyntacticException {
        if (lookAhead.equals(".")) {
            Llamada();
            ListaLlamadas();
        } else {
            // ListaLlamadas -> lambda
        }
    }

    private void ListaLlamadas_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("(")) {
            ArgsActuales();
            ListaLlamadas();
        } else {
            ListaLlamadas();
        }
    }

    private void Llamada() throws LexicalException, SyntacticException {
        match(".");
        match("id");
        ArgsActuales();
    }

    private void Literal() throws LexicalException, SyntacticException {
        if (lookAhead.equals("null")) {
            match("null");
        } else if (lookAhead.equals("true")) {
            match("true");
        } else if (lookAhead.equals("false")) {
            match("false");
        } else if (lookAhead.equals("intLiteral")) {
            match("intLiteral");
        } else if (lookAhead.equals("charLiteral")) {
            match("charLiteral");
        } else if (lookAhead.equals("stringLiteral")) {
            match("stringLiteral");
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba un literal. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void ArgsActuales() throws LexicalException, SyntacticException {
        if (lookAhead.equals("(")) {
            match("(");
            ArgsActuales_();
        } else {
            throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba la apertura de una lista de argumentos actuales '('. Se encontro: '" + lookAhead.getToken() + "'.");
        }
    }

    private void ArgsActuales_() throws LexicalException, SyntacticException {
        if (lookAhead.equals(")")) {
            // ArgsActuales_ -> )
            // No hay mas argumentos actuales
            match(")");
        } else {
            ListaExps();
            if (lookAhead.equals(")")) {
                match(")");
            } else {
                throw new SyntacticException("Linea: " + lookAhead.getLineNumber() + " - Error sintactico: Se esperaba el cierre de la lista de argumentos actuales ')'. Se encontro: '" + lookAhead.getToken() + "'.");
            }
        }
    }

    private void ListaExps() throws LexicalException, SyntacticException {
        Expresion();
        ListaExps_();
    }

    private void ListaExps_() throws LexicalException, SyntacticException {
        if (lookAhead.equals(",")) {
            match(",");
            ListaExps();
        } else {
            // ListaExps_ -> lambda
        }
    }

    /**
     * Control de declaracion de metodos
     *
     * @param lookAhead
     * @return true si se trata de un tipo, false en caso contrario
     */
    private boolean isType(Token lookAhead) {
        return lookAhead.equals("void") || lookAhead.equals("boolean") || lookAhead.equals("char") || lookAhead.equals("int") || lookAhead.equals("String") || lookAhead.equals("id");
    }
}