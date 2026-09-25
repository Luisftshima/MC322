public class Demanda {
    private String tipoProduto;
    private int quantidadeProdutos;
    private StatusDemanda status = StatusDemanda.CONCLUIDA; 

    public Demanda(String tipoProduto, int quantidade){
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidade;
    }

    public void atualizarQuantidade(int quantidade){
        //quantidade pode ser tanto um numero negativo como um positivo
        this.quantidadeProdutos += quantidade;
    }

    public float calcularMateriaPrimaNecessaria(Produto produto){
        return produto.getMateriaPrimaPorUnidade() * this.quantidadeProdutos;
    }
    public double calcularCustoEstimado() {
        switch (tipoProduto.toLowerCase()) {
            case "alta":
                return quantidadeProdutos * 4.75;
            case "media":
                return quantidadeProdutos * 3.75;
            case "baixa":
                return quantidadeProdutos * 3.00;
            default:
                return 0;
        }
    }

    public void emProdução(){
        this.status = StatusDemanda.EM_PRODUCAO;
    }

    public void pendente(){
        this.status = StatusDemanda.PENDENTE;
    }

    public void atender(){
        this.status = StatusDemanda.CONCLUIDA;
    }

    public void cancelar(){
        this.status = StatusDemanda.CANCELADA;
    }

    public String getTipoProduto(){
        return tipoProduto;
    }

    public int getQuantidadeProdutos(){
        return quantidadeProdutos;
    }

    public StatusDemanda getStatus(){
        return status;
    }
}
