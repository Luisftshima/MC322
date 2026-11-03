package excecoes;

/**
 * PersistenciaException
 * 
 * Tratamento de erro relacionado as persistencias
 */
public abstract class PersistenciaException extends RuntimeException{
    public PersistenciaException(String mensagem){
        super(mensagem);
    }
    public PersistenciaException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
