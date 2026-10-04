public enum NivelQualidade {
    ALTA("Alta qualidade", 4.75),
    MEDIA("Média qualidade", 3.75),
    BAIXA("Baixa qualidade", 3.00);

    private final String descricao;
    private final double custoUnitario;

    NivelQualidade(String descricao, double custoUnitario){
        this.descricao = descricao;
        this.custoUnitario = custoUnitario;
    }

    public String getDescricao(){
        return descricao;
    }

    public double getCustoUnitario(){
        return custoUnitario;
    }
}
