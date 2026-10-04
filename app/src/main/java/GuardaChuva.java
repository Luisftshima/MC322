public class GuardaChuva extends Produto{
    public GuardaChuva(){
        super("Guarda chuvas Wonka", 15.0f, 0.5);
    }
    
    @Override public void processar(){
        setStatus("Guarda-chuvas moldados");
    }

    @Override public double calcularTempoProducao(){
        return 3.0;
    }

    @Override public NivelQualidade getTipo(){
        return NivelQualidade.BAIXA;
    }
}
