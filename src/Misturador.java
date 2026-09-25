public class Misturador extends Maquina{
    public Misturador(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, saude, cenario);
    }
    @Override public boolean processar(Produto produto){
        if (!this.estaLigada() || this.estaQuebrada()){
            System.out.println("Maquina " + this.getNome() + "inválida");
            return false;
        }

        if (verificarFalha()){
            produto.AumentarProbabilidadeFalha();
        }
        produto.processar();
        this.deteriorarMaquina();
        return true;
    }

    @Override public String getTipo(){
        return "Misturador";
    }
}


