public class Misturador extends Maquina{
    public Misturador(){
        super("Super Misturador de Ingredientes", 1000f, 0.15, 25.0);
    }
    @Override public boolean processar(Produto produto){
        produto.aumentarProbabilidadeFalha(getProbabilidadeFalha);
        produto.processar();
        return true;
    }

    @Override public String getTipo(){
        return "Misturador";
    }
}


