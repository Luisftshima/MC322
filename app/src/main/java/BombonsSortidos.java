/**
 * Representa o produto Caixa de Bombons Wonka Sortidos
 *
 * <p>Eh um produto de qualidade media que utiliza 30.0 g de materia-prima por unidade</p>
 */
public class BombonsSortidos extends Produto{
    /**
     * Cria uma nova caixa de bombons sortidos
     *
     * <p>O produto possui qualidade de 0,7, utiliza 30.0 g de materia-prima por unidade e eh identificado pelo nome "Caixa de Bombons Wonka Sortidos"</p>
     */
    public BombonsSortidos(){
    super("Caixa de Bombons Wonka Sortidos", 30.0f, 0.7);
    }

    /**
     * Atualiza o status do produto para indicar que os bombons foram fabricados e colocados na caixa
     */
    @Override public void processar(){
        setStatus("Bombons fabricados e na caixa!");
    }
    /**
     * Retorna o tempo necessario para produzir uma unidade do produto
     *
     * @return tempo de producao em unidades de tempo do sistema
     */
    @Override public double calcularTempoProducao(){
        return 8.0;
    }
    /**
     * Retorna o nivel de qualidade associado ao produto
     *
     * @return o nivel de qualidade
     */
    @Override public NivelQualidade getTipo(){
        return NivelQualidade.MEDIA;
    }
}
