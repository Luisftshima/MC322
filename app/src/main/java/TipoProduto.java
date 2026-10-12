/**
 * Representa os tipos de produtos fabricados pela fabrica
 *
 * <p>Cada tipo possui uma descricao e esta associado a um nivel de qualidade</p>
 */
public enum TipoProduto {
    /**
     * Tipo correspondente ao Ovo Artesanal, de alta qualidade
     */
    OVO_ARTESANAL("OvoArtesanal", NivelQualidade.ALTA),
    /**
     * Tipo correspondente ao Chocotone, de alta qualidade
     */
    CHOCOTONE("Chocotone", NivelQualidade.ALTA),/**
    * Tipo correspondente a Caixa de Bombons Wonka Sortidos, de qualidade media
    */
    BOMBONS_SORTIDOS("Bombons Sortidos", NivelQualidade.MEDIA),
    /**
     * Tipo correspondente ao Guarda-chuva de Chocolate, de baixa qualidade
     */
    GUARDA_CHUVA("Guarda-chuva de Chocolate", NivelQualidade.BAIXA);

    /**
     * Descricao textual do tipo de produto
     */
    private final String descricao;
    /**
     * Nivel de qualidade associado ao produto
     */
    private final NivelQualidade nivelQualidade;

    /**
     * Cria um tipo de produto com sua descricao e nivel de qualidade
     *
     * @param descricao descricao textual do tipo de produto
     * @param nivelQualidade nivel de qualidade associado ao produto
     */
    TipoProduto(String descricao, NivelQualidade nivelQualidade){
        this.descricao = descricao;
        this.nivelQualidade = nivelQualidade;
    }

    /**
     * Retorna a descricao do tipo de produto
     *
     * @return descricao textual
     */
    public String getDescricao(){
        return descricao;
    }

    /**
     * Retorna o nivel de qualidade associado ao tipo de produto
     *
     * @return nivel de qualidade
     */
    public NivelQualidade getNivelQualidade(){
        return nivelQualidade;
    }
}
