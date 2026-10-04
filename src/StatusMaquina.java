public enum StatusMaquina {
    OPERACIONAL("Fabricando chocolates normalmente!"),
    DESLIGADA("Dando uma pausa..."),
    MANUTENCAO("Conserte ou fique sem chocolate!"),
    QUEBRADA("Urgente! Temos que consertar logo.");

    private final String descricao;

    StatusMaquina(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
