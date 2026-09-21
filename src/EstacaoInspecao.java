public class EstacaoInspecao extends Maquina{
    public EstacaoInspecao(){
        super("Sensor de controle de qualidade", 200, 0.05, 0.5, 100);
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
