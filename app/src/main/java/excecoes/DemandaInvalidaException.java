package excecoes;

/**
 * Excecao lançada quando uma operacao relacionada a uma demanda possui parametros invalidos
 */
public class DemandaInvalidaException extends RuntimeException{
    /**
     * Cria uma excecao de demanda invalida com a mensagem informada
     *
     * @param mensagem mensagem que descreve o motivo da falha
     */
    public DemandaInvalidaException(String mensagem){
        super(mensagem);
    }
}
