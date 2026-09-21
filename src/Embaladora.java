public class Embaladora extends Maquina{
    public Embaladora(){
        super("Embaladora Wonka", 500, 0.1, 1.5, 100);
    }

    @Override public boolean processar(Produto produto){
        if (verificarFalha()){
            produto.AumentarProbabilidadeFalha();
        }
        produto.setStatus("Embalado");
        return true;
    }

    @Override public String getTipo(){
        return "Embalagem";
    }
}
