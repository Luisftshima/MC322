package excecoes;

/**
 * ArquivoInexistenteException
 * 
 * Tratamento de erro para arquivos xml inexistentes
 */
public class ArquivoInexistenteException extends PersistenciaException{
    public ArquivoInexistenteException(String mensagem){
        super(mensagem);
    }
}
