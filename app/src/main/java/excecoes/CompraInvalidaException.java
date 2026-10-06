package excecoes;

public class CompraInvalidaException extends RuntimeException{
    public CompraInvalidaException(String mensagem){
        super(mensagem);
    }
}
