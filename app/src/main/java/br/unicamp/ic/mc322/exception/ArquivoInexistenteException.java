package br.unicamp.ic.mc322.exception;

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
