public abstract class Demanda {
    private String tipoProduto;
    private int quantidadeProdutos;
    private boolean atendida; 

    public Demanda(String nome, int quantidade, boolean atendida){
        this.tipoProduto = nome;
        this.quantidadeProdutos = quantidade;
        this.atendida = false;
    }

    public void atualizarQuantidade(int quantidade){
        //quantidade pode ser tanto um numero negativo como um positivo
        this.quantidadeProdutos += quantidade;
    }

    public float calcularMateriaPrimaNecessaria(Produto produto, int quantidade){
        return produto.getDemandaMateriaPrima() * quantidade;
    }

    public void atender(){
        this.atendida = true;
    }

    public String getTipoProduto(){
        return tipoProduto;
    }

    public int getQuantidadeProdutos(){
        return quantidadeProdutos;
    }

    public boolean isAtendida(){
        return atendida;
    }
}
