public class EstacaoInspecao extends Maquina{
    public EstacaoInspecao(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, saude, cenario);
    }

    @Override 
    public boolean processar(Produto produto){

        // é a única máquina que pode falahr por conta própria
        if(verificarFalha()){
            produto.setStatus("Inspeção com defeito no sensor");
            return false;
        }

        java.util.Random rand = new java.util.Random();
        if (rand.nextDouble() < produto.getProbabilidadeFalha()){
            produto.setStatus("Rejeitado na Inspeção.");
            produto.ResetarProbabilidadeFalha();
            return false;
        }

        produto.setStatus("Aprovado!");
        return true;
    }
    @Override public String getTipo(){
        return "Inspeção";
    }
}
