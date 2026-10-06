package excecoes;

public class DemandaInvalidaException extends RuntimeException{
    public DemandaInvalidaException(String mensagem){
        super(mensagem);
    }
}
