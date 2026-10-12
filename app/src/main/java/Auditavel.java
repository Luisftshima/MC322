/**
 * Define o contrato para objetos que podem ser submetidos a uma auditoria
 *
 * <p>Uma classe auditavel deve ser capaz de gerar um relatorio de diagnostico e informar se necessita de manutencao</p>
 */
public interface Auditavel {
    /**
     * Gera um relatorio textual com informacoes de diagnostico do objeto.
     *
     * @return texto contendo as informacoes relevantes para auditoria
     */
    String gerarRelatorioDiagnostico();
    /**
     * Verifica se o objeto se encontra em uma condicao que requer manutencao
     *
     * @return Se o objeto necessita de manutencao
     */
    boolean precisaManutencao();
}
