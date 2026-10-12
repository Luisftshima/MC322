/**
 * Representa os possiveis estados operacionais de uma maquina
 */
public enum StatusMaquina {
    /**
     * Maquina apta a operar normalmente
     */
    OPERACIONAL("Fabricando chocolates normalmente!"),
    /**
     * Maquina que nao esta ligada no momento
     */
    DESLIGADA("Dando uma pausa..."),
    /**
     * Maquina cuja saude atingiu o nivel que recomenda manutencao, mas que ainda pode operar
     */
    MANUTENCAO("Conserte ou fique sem chocolate!"),
    /**
     * Maquina sem condicoes de operacao devido a saude zerada
     */
    QUEBRADA("Urgente! Temos que consertar logo.");

    /**
     * Descricao do status
     */
    private final String descricao;

    /**
     * Cria um status de maquina com sua descricao textual
     *
     * @param descricao descricao do status
     */
    StatusMaquina(String descricao){
        this.descricao = descricao;
    }

    /**
     * Retorna a descricao do status da maquina
     *
     * @return descricao textual do status
     */
    public String getDescricao(){
        return descricao;
    }
}
