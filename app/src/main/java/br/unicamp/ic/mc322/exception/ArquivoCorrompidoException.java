package br.unicamp.ic.mc322.exception;

/**
 * 
 * ArquivoCorrompidoException
 * 
 * Tratamento de erro para arquivo vazio, XML quebrado ou fora da estrutura esperada
 */
public class ArquivoCorrompidoException extends PersistenciaException{
    public ArquivoCorrompidoException(String mensagem){
        super(mensagem);
    }
    public ArquivoCorrompidoException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
