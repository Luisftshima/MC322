public enum StatusPedido {
    PENDENTE("Pedido não feito"),
    EM_PRODUCAO("Pedido em produção"),
    CONCLUIDA("Pedido concluído");

    private String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}