import java.util.List;

/**
 * Define o contrato para estrategias de selecao da proxima demanda a ser produzida pela fabrica
 */
public interface EstrategiaProducao {
    /**
     * Seleciona uma demanda de acordo com a regra da estrategia.
     *
     * @param demandas lista de demandas disponiveis
     * @param orcamentoDisponivel orcamento atualmente disponivel para producao
     * @return demanda selecionada ou NULL caso nenhuma demanda possa ser selecionada
     */
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);
    /**
     * Retorna o nome utilizado para identificar a estrategia
     *
     * @return nome da estrategia
     */
    String getNomeEstrategia();
}
