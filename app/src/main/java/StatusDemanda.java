/**
 * Representa os possiveis estados de uma demanda de producao
 */
public enum StatusDemanda {
    /**
     * Demanda cadastrada que ainda possui producao a ser realizada
     */
    PENDENTE("Demanda cadastrada, mas ainda não iniciada"),
    /**
     * Demanda atualmente selecionada e em processo de fabricacao
     */
    EM_PRODUCAO("Demanda selecionada e em processo de fabricação. Vamos ter chocolate!"),
    /**
     * Demanda cuja quantidade foi totalmente atendida com sucesso
     */
    CONCLUIDA("Demanda com todos os chocolates produzidos com sucesso."),
    /**
     * Demanda interrompida por alguma condicao impeditiva, como falta de orcamento ou de materia-prima
     */
    CANCELADA("Demanda cancelada por falta de orçamento ou chocolate.");

    /**
     * Descricao do status
     */
    private final String descricao;

    /**
     * Cria um status de demanda com sua descricao textual
     *
     * @param descricao descricao do status
     */
    StatusDemanda(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descricao do status.
     *
     * @return descricao textual do status
     */
    public String getDescricao() {
        return descricao;
    }
}