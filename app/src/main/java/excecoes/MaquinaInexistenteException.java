package excecoes;

public class MaquinaInexistenteException extends RuntimeException{
    public MaquinaInexistenteException(String mensagem){
        super(mensagem);
    }
}
