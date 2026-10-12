package excecoes;

public class MaquinaIndisponivelException extends RuntimeException{
    public MaquinaIndisponivelException(String mensagem){
        super(mensagem);
    }
}
