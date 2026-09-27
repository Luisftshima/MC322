public class Demanda {
    private NivelQualidade tipoProduto;
    private int quantidadeProdutos;
    private StatusDemanda status = StatusDemanda.PENDENTE; 

    public Demanda(NivelQualidade tipoProduto, int quantidade){
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidade;
    }
    
    public boolean isElegivel(){
        return this.status == StatusDemanda.PENDENTE && this.quantidadeProdutos > 0;
    }

    public void atualizarQuantidade(int quantidade){
        //quantidade pode ser tanto um numero negativo como um positivo
        this.quantidadeProdutos += quantidade;
    }

    public float calcularMateriaPrimaNecessaria(Produto produto){
        return produto.getMateriaPrimaPorUnidade() * this.quantidadeProdutos;
    }

    public double calcularCustoEstimado() {
        return quantidadeProdutos * tipoProduto.getCustoUnitario();
    }

    public boolean isViavel(double orcamentoDisponivel){
        return calcularCustoEstimado() <= orcamentoDisponivel;
    }

    public void emProdução(){
        this.status = StatusDemanda.EM_PRODUCAO;
    }

    public void pendente(){
        this.status = StatusDemanda.PENDENTE;
    }

    public void atender(){
        if (this.status == StatusDemanda.CANCELADA){
            throw new IllegalStateException("Não é possível concluir uma demanda ChocoWonka cancelada");
        }
        this.status = StatusDemanda.CONCLUIDA;
    }

    public void cancelar(){
        this.status = StatusDemanda.CANCELADA;
    }

    public NivelQualidade getTipoProduto(){
        return tipoProduto;
    }

    public int getQuantidadeProdutos(){
        return quantidadeProdutos;
    }

    public StatusDemanda getStatus(){
        return status;
    }
}
