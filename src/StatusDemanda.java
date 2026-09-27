public enum StatusDemanda {
    PENDENTE("Demanda cadastrada, mas ainda não iniciada"),
    EM_PRODUCAO("Demanda selecionada e em processo de fabricação. Vamos ter chocolate!"),
    CONCLUIDA("Demanda com todos os chocolates produzidos com sucesso."),
    CANCELADA("Demanda cancelada por falta de orçamento ou chocolate.");

    private final String descricao;

    StatusDemanda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}