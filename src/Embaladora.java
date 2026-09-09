public class Embaladora extends Maquina{
    public Embaladora(){
        super("Embaladora", 500f, 10.0, 15.0);
    }
    @Override public booblean processar(Produto produto){
        produto.AumentarProbabilidadeFalha(this.probabilidadeFalhaAcumulada);
        produto.setStatus("Embalado");
        return true;
    }

    @Override public String getTipo(){
        return "Embalagem";
    }
}
