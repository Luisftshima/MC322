/**
 * Representa o produto Chocotone da fabrica
 *
 * <p>Eh um produto de alta qualidade que utiliza 70.0 g de materia-prima por unidade</p>
 */
public class Chocotone  extends Produto{
    /**
     * Cria um novo chocotone
     *
     * <p>O produto possui qualidade de 0.9, utiliza 70.0 g de materia-prima por unidade e eh classificado como de alta qualidade</p>
     */
    public Chocotone(){
        super("Chocotone", 70.0f, 0.9);
    }

    /**
     * Atualiza o status do produto para indicar que o chocotone saiu do forno.
     */
    @Override public void processar(){
        setStatus("Chocotone saindo direto do forno!");
    }

    /**
     * Retorna o tempo necessario para produzir uma unidade de chocotone
     *
     * @return tempo de producao
     */
    @Override public double calcularTempoProducao(){
        return 30.0;
    }
    /**
     * Retorna o nivel de qualidade do chocotone
     *
     * @return nivel de qualidade
     */
    @Override public NivelQualidade getTipo(){
        return NivelQualidade.ALTA;
    }
}
