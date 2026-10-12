package excecoes;

/**
 * Excecao lançada quando uma opcao informada pelo usuario nao corresponde a uma opcao valida do menu atual
 */
public class OpcaoInvalidaException extends RuntimeException{
    /**
     * Cria uma excecao de demanda invalida com a mensagem informada
     *
     * @param mensagem mensagem que descreve o motivo da falha
     */
    public OpcaoInvalidaException(String mensagem){
        super(mensagem);
    }
}
