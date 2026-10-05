
public class Chocotone  extends Produto{
    public Chocotone(){
        super("Chocotone", 70.0f, 0.9);
    }

    @Override public void processar(){
        setStatus("Chocotone saindo direto do forno!");
    }

    @Override public double calcularTempoProducao(){
        return 30.0;
    }

    @Override public NivelQualidade getTipo(){
        return NivelQualidade.ALTA;
    }
}
