public enum TipoProduto {
    OVO_ARTESANAL("OvoArtesanal", NivelQualidade.ALTA),
    CHOCOTONE("Chocotone", NivelQualidade.ALTA),
    BOMBONS_SORTIDOS("Bombons Sortidos", NivelQualidade.MEDIA),
    GUARDA_CHUVA("Guarda-chuva de Chocolate", NivelQualidade.BAIXA);

    private final String descricao;
    private final NivelQualidade nivelQualidade;

    TipoProduto(String descricao, NivelQualidade nivelQualidade){
        this.descricao = descricao;
        this.nivelQualidade = nivelQualidade;
    }

    public String getDescricao(){
        return descricao;
    }

    public NivelQualidade getNivelQualidade(){
        return nivelQualidade;
    }
}
