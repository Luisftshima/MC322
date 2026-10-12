/**
 * Representa o produto Ovo Artesanal
 *
 * <p>Eh um produto de alta qualidade que utiliza 50.0 g de materia-prima por unidade</p>
 */
public class OvoArtesanal extends Produto{
    /**
     * Cria um novo ovo artesanal
     *
     * <p>O produto possui qualidade de 0.9 e utiliza 50.0 g de materia-prima por unidade</p>
     */
    public OvoArtesanal(){
        super("Ovo Artesanal", 50.0f, 0.9);
    }

    /**
     * Atualiza o status do produto para indicar que o ovo foi feito, recheado e embalado
     */
    @Override public void processar(){
        setStatus("Ovo feito, recheado e embalado!");
    }

    /**
     * Retorna o tempo necessario para produzir uma unidade de ovo artesanal
     *
     * @return tempo de producao
     */
    @Override public double calcularTempoProducao(){
        return 15.0;
    }

    /**
     * Retorna o nivel de qualidade associado ao produto
     *
     * @return nivel de qualidade
     */
    @Override public NivelQualidade getTipo(){
        return NivelQualidade.ALTA;
    }
    
}