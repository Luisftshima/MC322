public class Misturador extends Maquina{
    public Misturador(){
        super("Super Misturador de Ingredientes", 1000, 0.15, 0.25, 100);
    }
    @Override public boolean processar(Produto produto){
        if (verificarFalha()){
            produto.AumentarProbabilidadeFalha();
        }
        produto.processar();
        return true;
    }

    @Override public String getTipo(){
        return "Misturador";
    }
}


