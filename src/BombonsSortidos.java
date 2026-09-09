//produto de qualidade média

public class BombonsSortidos extends Produto{
    public BombonsSortidos(){
    super("Caixa de Bombons Wonka Sortidos", 30.0f);
    this.qualidade = 0.7;
    }
    @Override public void processar(){
        setStatus("Bombons fabricados e na caixa");
    }

    @Override public double calcularTempoProducao(){
        return 8.0;
    }

    @Override public String getTipo(){
        return "Qualidade média";
    }
}
