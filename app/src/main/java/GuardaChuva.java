/**
 * Representa o produto Guarda-chuva de Chocolate
 *
 * <p>Eh um produto de baixa qualidade que utiliza 15.0 g de materia-prima por unidade</p>
 */
public class GuardaChuva extends Produto{
    /**
     * Cria um novo guarda-chuva de chocolate
     *
     * <p>O produto possui qualidade de 0.5 e utiliza 15.0 g de materia-prima por unidade</p>
     */
    public GuardaChuva(){
        super("Guarda chuvas Wonka", 15.0f, 0.5);
    }
    
    /**
     * Atualiza o status do produto para indicar que o guarda-chuva foi moldado
     */
    @Override public void processar(){
        setStatus("Guarda-chuvas moldados");
    }

    /**
     * Retorna o tempo necessario para produzir uma unidade de guarda-chuva de chocolate
     *
     * @return tempo de producao
     */
    @Override public double calcularTempoProducao(){
        return 3.0;
    }

    /**
     * Retorna o nivel de qualidade associado ao produto
     *
     * @return nivel de qualidade
     */
    @Override public NivelQualidade getTipo(){
        return NivelQualidade.BAIXA;
    }
}
