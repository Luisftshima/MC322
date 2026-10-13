package excecoes;

/**
 * MaquinaInexistenteException
 * Excecao lancada se n'ao existe o id da maquina
 */
public class MaquinaInexistenteException extends RuntimeException{
    public MaquinaInexistenteException(String mensagem){
        super(mensagem);
    }
}
