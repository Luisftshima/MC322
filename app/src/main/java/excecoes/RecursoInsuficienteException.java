package excecoes;

/**
 * Excecao lançada quando uma operacao tenta utilizar ou representar uma quantidade de recurso superior a disponivel
 */
public class RecursoInsuficienteException extends RuntimeException{
    /**
     * Cria uma excecao de demanda invalida com a mensagem informada
     *
     * @param mensagem mensagem que descreve o motivo da falha
     */
    public RecursoInsuficienteException(String mensagem){
        super(mensagem);
    }
}
