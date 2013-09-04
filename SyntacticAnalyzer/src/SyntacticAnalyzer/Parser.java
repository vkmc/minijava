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
        
    }

    private void ListaClases() throws LexicalException, SyntacticException {
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
       
        } else {
            throw new SyntacticException("Se esperaba la palabra reservada extends o {. Se encontro: '"+lookAhead.getToken()+"'\nNumero de linea: " + lookAhead.getLineNumber());
        }
    }

    private void ListaMiembros() throws LexicalException, SyntacticException {
        
    }

    private void Miembro() throws LexicalException, SyntacticException {
        if (lookAhead.equals("var")) {
            Atributo();
        } else if(lookAhead.equals("id")) {
            Ctor();            
        } else if(lookAhead.equals("static") || lookAhead.equals("dynamic")) {
            Metodo();
        } else {
            // error
        }
    }

    private void Atributo() throws LexicalException, SyntacticException {
        
    }

    private void Metodo() throws LexicalException, SyntacticException {
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
    }

    private void TipoMetodo() throws LexicalException, SyntacticException {
    }

    private void Tipo() throws LexicalException, SyntacticException {
    }

    private void TipoPrimitivo() throws LexicalException, SyntacticException {
    }

    private void ListaDecVars() throws LexicalException, SyntacticException {
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
