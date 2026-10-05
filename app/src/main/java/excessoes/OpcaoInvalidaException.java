package excessoes;
public class OpcaoInvalidaException extends RuntimeException{
    public OpcaoInvalidaException(String mensagem){
        super(mensagem);
    }
}
