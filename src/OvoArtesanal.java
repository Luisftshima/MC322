// produto premium
// o produto vai alem das vendas de Pascoa

public class OvoArtesanal extends Produto{
    public OvoArtesanal(){
        super("Ovo Artesanal", 50.0f, 0.9);
    }
    @Override public void processar(){
        setStatus("Ovo feito, recheado e embalado!");
    }

    @Override public double calcularTempoProducao(){
        return 15.0;
    }

    @Override public String getTipo(){
        return "Alta Qualidade";
    }
    
}