import excecoes.RecursoInsuficienteException;

public class Demanda {
    private TipoProduto tipoProduto;
    private int quantidadeProdutos;
    private StatusDemanda status = StatusDemanda.CONCLUIDA; 

    public Demanda(TipoProduto tipoProduto, int quantidade){
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidade;
    }

    public void atualizarQuantidade(int quantidade){
        //quantidade pode ser tanto um numero negativo como um positivo
        if (this.quantidadeProdutos + quantidade < 0){
            throw new RecursoInsuficienteException("Materia-prima insuficiente.");
        }
        this.quantidadeProdutos += quantidade;
    }

    public float calcularMateriaPrimaNecessaria(Produto produto){
        return produto.getMateriaPrimaPorUnidade() * this.quantidadeProdutos;
    }

    public double calcularCustoEstimado() {
        return quantidadeProdutos * tipoProduto.getNivelQualidade().getCustoUnitario();
    }

    public boolean isViavel(double orcamentoDisponivel){
        return calcularCustoEstimado() <= orcamentoDisponivel;
    }

    public void emProducao(){
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

    public TipoProduto getTipoProduto(){
        return tipoProduto;
    }

    public int getQuantidadeProdutos(){
        return quantidadeProdutos;
    }

    public StatusDemanda getStatus(){
        return status;
    }
}
