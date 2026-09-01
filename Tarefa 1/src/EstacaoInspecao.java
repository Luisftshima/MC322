public class EstacaoInspecao {
    private boolean ativa = false;
    private int produtosInspecionados = 0;

    public void ativar(EstacaoInspecao estacaoInspecao){
        estacaoInspecao.ativa = true;
    }

    public void desativar(EstacaoInspecao estacaoInspecao){
        estacaoInspecao.ativa = false;
    }

    public void inspecionar(){
        if (ativa){
            produtosInspecionados++;
        }
    }

    public int getTotalInspecionados(){
        return produtosInspecionados;
    }

}
