package SyntacticAnalyzer;

/**
 * Analizador sintáctico. Transforma su entrada en un arbol de derivación.
 *
 * @author Ramiro Agis
 * @author Victoria Martínez de la Cruz
 */
public class Parser {

    private Tokenizer tokenizer;
    private Token lookAhead, currentToken;

    public Parser(String filename) {
        tokenizer = new Tokenizer(filename);
    }

    // analizador sintactico
    public void analize() throws SyntacticException, LexicalException {
        lookAhead = tokenizer.getToken();
        currentToken = null;
        Inicial();
    }

    public void match(String token) throws LexicalException, SyntacticException {
        if (lookAhead.getToken().equals(token)) {
            if (!token.equals("EOF")) {
                currentToken = lookAhead;
                lookAhead = tokenizer.getToken();
            } else {
                System.err.println("FIN DE ARCHIVO :O ");
            }
        } else {
            throw new SyntacticException("Se esperaba: '" + token + "'\nSe encontro: '" + lookAhead.getToken() + "'\nNumero de linea: " + lookAhead.getLineNumber());
        }
    }

    // no-terminales
    private void Inicial() throws LexicalException, SyntacticException {
        Clase();
        ListaClases();
    }

    private void ListaClases() throws LexicalException, SyntacticException {
        if (lookAhead.equals("class")) {
            Clase();
            ListaClases();
        } else if (lookAhead.equals("EOF")) {
            System.err.println("El analizador sintáctico termino exitosamente");
        } else {
            throw new SyntacticException("Se alcanzo EOF durante el análisis sintáctico.");
        }
    }

    private void Clase() throws LexicalException, SyntacticException {
        match("class");
        match("id");
        Herencia();
        match("{");
        ListaMiembros();
        match("}");
    }

    private void Herencia() throws LexicalException, SyntacticException {
        if (lookAhead.equals("extends")) {
            match("extends");
            match("id");
        } else if (lookAhead.equals("{")) {
            // Herencia -> lambda
            // No hay herencia 
        } else {
            throw new SyntacticException("Se esperaba la lista de miembros de la clase.");
        }
    }

    private void ListaMiembros() throws LexicalException, SyntacticException {
        if (lookAhead.equals("}")) {
            // ListaMiembros -> lambda
            // No hay mas miembros
        } else {
            Miembro();
            ListaMiembros();
            // Para no duplicar codigo dejamos que el control
            // de lo haga Miembro()
        }
    }

    private void Miembro() throws LexicalException, SyntacticException {
        if (lookAhead.equals("var")) {
            Atributo();
        } else if (lookAhead.equals("id")) {
            Ctor();
        } else if (lookAhead.equals("static") || lookAhead.equals("dynamic")) {
            Metodo();
        } else {
            throw new SyntacticException("Se esperaba la definicion de atributos, constructores o metodos.");
        }
    }

    private void Atributo() throws LexicalException, SyntacticException {
        match("var");
        Tipo();
        ListaDecVars();
        match(";");
    }

    private void Metodo() throws LexicalException, SyntacticException {
        ModMetodo();
        TipoMetodo();
        match("id");
        ArgsFormales();
        VarsLocales();
        Bloque();
    }

    private void Ctor() throws LexicalException, SyntacticException {
        match("id");
        ArgsFormales();
        VarsLocales();
        Bloque();
    }

    private void ArgsFormales() throws LexicalException, SyntacticException {
        match("(");
        ArgsFormales_();
        match(")");
    }

    private void ArgsFormales_() throws LexicalException, SyntacticException {
        if (lookAhead.equals(")")) {
            // ArgsFormales_ -> lambda
            // No hay mas argumentos formales
        } else {
            ListaArgsFormales();
        }
    }

    private void ListaArgsFormales() throws LexicalException, SyntacticException {
        ArgFormal();
        ListaArgsFormales_();
    }

    private void ListaArgsFormales_() throws LexicalException, SyntacticException {
        if (lookAhead.equals(")")) {
            // ListaArgsFormales_ -> lambda
            // No hay mas argumentos formales
        } else if (lookAhead.equals(",")) {
            match(",");
            ListaArgsFormales();
        } else {
            throw new SyntacticException("Se esperaban argumentos formales.");
        }
    }

    private void ArgFormal() throws LexicalException, SyntacticException {
        Tipo();
        match("id");
    }

    private void VarsLocales() throws LexicalException, SyntacticException {
        ListaAtributos();
    }

    private void ListaAtributos() throws LexicalException, SyntacticException {
        if (lookAhead.equals("{")) {
            // ListaAtributos -> lambda
            // Bloque
            // No hay mas atributos
        } else if (lookAhead.equals("var")) {
            Atributo();
            ListaAtributos();
        } else {
            throw new SyntacticException("Se esperaban atributos.");
        }
    }

    private void ModMetodo() throws LexicalException, SyntacticException {
        if (lookAhead.equals("static")) {
            match("static");
        } else if (lookAhead.equals("dynamic")) {
            match("dynamic");
        } else {
            throw new SyntacticException("Se esperaba el modo de ejecucion del metodo (static o dynamic).");
        }
    }

    private void TipoMetodo() throws LexicalException, SyntacticException {
        if (lookAhead.equals("void")) {
            match("void");
        } else {
            Tipo();
        }
    }

    private void Tipo() throws LexicalException, SyntacticException {
        if (lookAhead.equals("id")) {
            match("id");
        } else {
            TipoPrimitivo();
        }
    }

    private void TipoPrimitivo() throws LexicalException, SyntacticException {
        if (lookAhead.equals("boolean")) {
            match("boolean");
        } else if (lookAhead.equals("char")) {
            match("char");
        } else if (lookAhead.equals("int")) {
            match("int");
        } else if (lookAhead.equals("String")) {
            match("String");
        } else {
            throw new SyntacticException("Se esperaba un tipo de dato.");
        }
    }

    private void ListaDecVars() throws LexicalException, SyntacticException {
        match("id");
        ListaDecVars_();
    }

    private void ListaDecVars_() throws LexicalException, SyntacticException {
        if (lookAhead.equals(";")) {
            // ListaDecVars_ -> lambda
            // No hay mas variables declaradas
        } else if (lookAhead.equals(",")) {
            match(",");
            ListaDecVars();
        } else {
            throw new SyntacticException("Se esperaba una variable.");
        }
    }

    private void Bloque() throws LexicalException, SyntacticException {
        match("{");
        ListaSentencias();
        match("}");
    }

    private void ListaSentencias() throws LexicalException, SyntacticException {
        if (lookAhead.equals("}")) {
            // ListaSentencias -> lambda
            // No hay mas sentencias
        } else {
            Sentencia();
            // Delego el control de terminales o no-terminales a Sentencia()
            ListaSentencias();
        }
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
        } else {
            throw new SyntacticException("Se esperaba una sentencia.");
        }
    }

    private void Sentencia_() throws LexicalException, SyntacticException {
        if (lookAhead.equals("else")) {
            Sentencia();
        } else {
            // Sentencia_ -> lambda
            // if-then sin else
        }
    }

    private void Sentencia__() throws LexicalException, SyntacticException {
    }

    private void Asignacion() throws LexicalException, SyntacticException {
    }

    private void SentenciaSimple() throws LexicalException, SyntacticException {
    }

    private void Expresion() throws LexicalException, SyntacticException {
    }

    private void Expresion6() throws LexicalException, SyntacticException {
    }

    private void Expresion6_() throws LexicalException, SyntacticException {
    }

    private void Expresion5() throws LexicalException, SyntacticException {
    }

    private void Expresion5_() throws LexicalException, SyntacticException {
    }

    private void Expresion4() throws LexicalException, SyntacticException {
    }

    private void Expresion4_() throws LexicalException, SyntacticException {
    }

    private void Expresion3() throws LexicalException, SyntacticException {
    }

    private void Expresion3_() throws LexicalException, SyntacticException {
    }

    private void Expresion2() throws LexicalException, SyntacticException {
    }

    private void Expresion2_() throws LexicalException, SyntacticException {
    }

    private void Expresion1() throws LexicalException, SyntacticException {
    }

    private void Expresion1_() throws LexicalException, SyntacticException {
    }

    private void Expresion0() throws LexicalException, SyntacticException {
    }

    private void Operador4() throws LexicalException, SyntacticException {
    }

    private void Operador3() throws LexicalException, SyntacticException {
    }

    private void Operador2() throws LexicalException, SyntacticException {
    }

    private void Operador1() throws LexicalException, SyntacticException {
    }

    private void OperadorUnario() throws LexicalException, SyntacticException {
    }

    private void Primario() throws LexicalException, SyntacticException {
    }

    private void ListaLlamadas_() throws LexicalException, SyntacticException {
    }

    private void ListaLlamadas() throws LexicalException, SyntacticException {
    }

    private void Llamada() throws LexicalException, SyntacticException {
    }

    private void Literal() throws LexicalException, SyntacticException {
    }

    private void ArgsActuales() throws LexicalException, SyntacticException {
    }

    private void ArgsActuales_() throws LexicalException, SyntacticException {
    }

    private void ListaExps() throws LexicalException, SyntacticException {
    }

    private void ListaExps_() throws LexicalException, SyntacticException {
    }
}