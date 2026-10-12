/**
 * Representa os niveis de qualidade disponiveis para os produtos
 *
 * <p>Cada nivel possui uma descricao textual e um custo unitario utilizado nos calculos de custo estimado das demandas</p>
 */
public enum NivelQualidade {
    /**
     * Nivel de alta qualidade, com custo unitario de 4.75
     */
    ALTA("Alta qualidade", 4.75),
    /**
     * Nivel de media qualidade, com custo unitario de 3.75
     */
    MEDIA("Media qualidade", 3.75),
    /**
     * Nivel de baixa qualidade, com custo unitario de 3.00
     */
    BAIXA("Baixa qualidade", 3.00);

    /**
     * Descricao textual do nivel de qualidade
     */
    private final String descricao;
    /**
     * Custo associado a uma unidade do produto
     */
    private final double custoUnitario;

    /**
     * Cria um nivel de qualidade com uma descricao e um custo unitario
     *
     * @param descricao descricao textual do nivel de qualidade
     * @param custoUnitario custo associado a uma unidade do produto
     */
    NivelQualidade(String descricao, double custoUnitario){
        this.descricao = descricao;
        this.custoUnitario = custoUnitario;
    }

    /**
     * Retorna a descricao do nivel de qualidade
     *
     * @return descricao textual
     */
    public String getDescricao(){
        return descricao;
    }

    /**
     * Retorna o custo unitario associado ao nivel de qualidade
     *
     * @return custo por unidade
     */
    public double getCustoUnitario(){
        return custoUnitario;
    }
}
