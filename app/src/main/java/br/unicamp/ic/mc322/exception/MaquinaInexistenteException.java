package br.unicamp.ic.mc322.exception;

/**
 * MaquinaInexistenteException
 * Excecao lancada se n'ao existe o id da maquina
 */
public class MaquinaInexistenteException extends RuntimeException{
    public MaquinaInexistenteException(String mensagem){
        super(mensagem);
    }
}
