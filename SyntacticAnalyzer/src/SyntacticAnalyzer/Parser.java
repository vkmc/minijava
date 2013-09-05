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
        if(lookAhead.equals("}")) {
            // ListaMiembros -> lambda
            // No hay mas miembros
        } else {
            Miembro();
            ListaMiembros();
            // Para no duplicar codigo dejamos que el control lo haga Miembro()
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
            throw new LexicalException("Se esperaba la definicion de atributos, constructores o metodos.");
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
    }

    private void ArgsFormales() throws LexicalException, SyntacticException {
    }

    private void ListaArgsFormales() throws LexicalException, SyntacticException {
    }

    private void ListaArgsFormales_() throws LexicalException, SyntacticException {
    }

    private void ArgFormal() throws LexicalException, SyntacticException {
    }

    private void VarsLocales() throws LexicalException, SyntacticException {
    }

    private void ListaAtributos() throws LexicalException, SyntacticException {
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
        if(lookAhead.equals("id")) {
            match("id");
        } else {
            TipoPrimitivo();
        }
    }

    private void TipoPrimitivo() throws LexicalException, SyntacticException {
        if(lookAhead.equals("boolean")) {
            match("boolean");
        } else if (lookAhead.equals("char")) {
            match("char");
        } else if (lookAhead.equals("int")) {
            match("int");
        } else if (lookAhead.equals("String")) {
            match("String");
        } else {
            throw new LexicalException("Se esperaba un tipo primitivo.");
        }
    }

    private void ListaDecVars() throws LexicalException, SyntacticException {
        match("id");
        ListaDecVars_();
    }
    
    private void ListaDecVars_() throws LexicalException, SyntacticException {
        if (lookAhead.equals(",")) {
            match(",");
            ListaDecVars();
        } else {
            
        }
    }

    private void Bloque() throws LexicalException, SyntacticException {
    }

    private void ListaSentencias() throws LexicalException, SyntacticException {
    }

    private void Sentencia() throws LexicalException, SyntacticException {
    }

    private void Sentencia_() throws LexicalException, SyntacticException {
    }

    private void Asignacion() throws LexicalException, SyntacticException {
    }

    private void SentenciaSimple() throws LexicalException, SyntacticException {
    }

    private void Expresion() throws LexicalException, SyntacticException {
    }

    private void Expresion6() throws LexicalException, SyntacticException {
    }

    private void RExpresion6() throws LexicalException, SyntacticException {
    }

    private void Expresion5() throws LexicalException, SyntacticException {
    }

    private void RExpresion5() throws LexicalException, SyntacticException {
    }

    private void Expresion4() throws LexicalException, SyntacticException {
    }

    private void RExpresion4() throws LexicalException, SyntacticException {
    }

    private void Expresion3() throws LexicalException, SyntacticException {
    }

    private void RExpresion3() throws LexicalException, SyntacticException {
    }

    private void Expresion2() throws LexicalException, SyntacticException {
    }

    private void RExpresion2() throws LexicalException, SyntacticException {
    }

    private void Expresion1() throws LexicalException, SyntacticException {
    }

    private void RExpresion1() throws LexicalException, SyntacticException {
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

    private void ListaExps() throws LexicalException, SyntacticException {
    }

    private void ListaExps_() throws LexicalException, SyntacticException {
    }
}