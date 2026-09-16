public class Demanda {
    private String tipoProduto;
    private int quantidadeProdutos;
    private boolean atendida = true; 

    public Demanda(String tipoProduto, int quantidade){
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidade;
    }

    public void atualizarQuantidade(int quantidade){
        //quantidade pode ser tanto um numero negativo como um positivo
        this.quantidadeProdutos += quantidade;

        if(this.quantidadeProdutos > 0){
            this.atendida = false;
        }
    }

    public float calcularMateriaPrimaNecessaria(Produto produto){
        return produto.getMateriaPrimaPorUnidade() * this.quantidadeProdutos;
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
