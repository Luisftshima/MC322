public class GuardaChuva extends Produto{
    public GuardaChuva(){
        super("Guarda chuvas Wonka");
        this.qualidade = 0.5;
    }
    @Override public void processar(){
        setStatus("Guarda-chuvas produzidas");
    }

    @Override public double calcularTempoProducao(){
        return 3.0;
    }

    @Override public String getTipo(){
        return "Baixa Qualidade";
    }
}
