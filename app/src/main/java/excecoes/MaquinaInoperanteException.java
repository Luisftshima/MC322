package excecoes;

public class MaquinaInoperanteException extends RuntimeException{
    public MaquinaInoperanteException(String mensagem){
        super(mensagem);
    }
}
