public class EstacaoInspecao extends Maquina{
    public EstacaoInspecao(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, saude, cenario);
    }

    @Override 
    public boolean processar(Produto produto){
        if (!this.podeOperar()){
            System.out.println("Maquina " + this.getNome() + "inválida");
            return false;
        }

        // é a única máquina que pode falhar por conta própria
        boolean falhouSensor = verificarFalha();
        this.deteriorarMaquina();

        if(falhouSensor){
            produto.setStatus("Inspeção com defeito no sensor");
            return false;
        }

        if (RANDOM.nextDouble() < produto.getProbabilidadeFalha()){
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
