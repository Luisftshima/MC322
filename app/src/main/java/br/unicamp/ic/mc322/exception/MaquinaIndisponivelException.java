package br.unicamp.ic.mc322.exception;

/**
 * Excecao lancada se uma maquina nao esta disponivel para fabricacao
 */
public class MaquinaIndisponivelException extends RuntimeException{
    public MaquinaIndisponivelException(String mensagem){
        super(mensagem);
    }
}
