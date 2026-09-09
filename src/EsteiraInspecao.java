public class EsteiraInspecao extends Maquina{
    public EsteiraInspecao(){
        super("Sensor de controle de qualidade", 200f, 5.0, 10.0);
    }
    @Override 
    public boolean processar(Produto produto){
        if(verificarFalha()){
            return false;
        }

        double chanceDeRejeicao = produto.getProbabilidadeFalhaAcumulada() * produto.getQualidade();

        java.util.Random rand = new java.util.Random();
        if (rand.nextDouble() < chanceDeRejeicao){
            produto.setStatus("Rejeitado na Inspeção.");
            return false;
        }

        produto.setStatus("Aprovado!");
        return true;
    }
    @Override public String getTipo(){
        return "Inspeção";
    }
}
