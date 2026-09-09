// produto premium

public class OvoArtesanal extends Produto{
    public OvoArtesanal(){
        super("Ovo Artesanal", 50.0f);
        this.qualidade = 0.9;
    }
    @Override public void processar(){
        setStatus("Ovo feito e embalado");
    }

    @Override public double calcularTempoProducao(){
        return 15.0;
    }

    @Override public String getTipo(){
        return "Alta Qualidade";
    }
    
}