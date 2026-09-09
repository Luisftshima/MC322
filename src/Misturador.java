public class Misturador extends Maquina{
    public Misturador(){
        super("Misturador de ingredientes", 1000f, 15.0, 25.0);
    }
    @Override public boolean processar(Produto produto){
        produto.AumentarProbabilidadeFalha(this.probablidadeFalhaAcumulada);
        produto.processar();
        return true;
    }

    @Override public String getTipo(){
        return "Misturador";
    }
}


