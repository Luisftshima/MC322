package br.unicamp.ic.mc322.exception;

/**
 * Excecao lancada quando uma operacao de compra de materia-prima nao pode ser realizada
 */
public class CompraInvalidaException extends RuntimeException{
    /**
     * Cria uma excecao de compra invalida com a mensagem informada
     *
     * @param mensagem mensagem que descreve o motivo da falha
     */
    public CompraInvalidaException(String mensagem){
        super(mensagem);
    }
}
