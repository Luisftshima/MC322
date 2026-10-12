import java.util.List;

/**
 * Estrategia de producao que seleciona a demanda pendente com a maior quantidade de produtos solicitados
 */
public class EstrategiaMaiorDemanda implements EstrategiaProducao{
    /**
     * Seleciona a demanda pendente com maior quantidade de produtos.
     *
     * <p>Demandas nao pendentes ou sem quantidade positiva sao ignoradas. O orcamento disponivel eh recebido pela interface, mas nao eh utilizado por esta estrategia</p>
     *
     * @param demandas lista de demandas disponiveis
     * @param orcamentoDisponivel orcamento atualmente disponivel para producao
     * @return demanda pendente com a maior quantidade de produtos, ou NULL caso nenhuma demanda elegivel seja encontrada
     */
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        
        Demanda melhor = null;

        for (Demanda demanda : demandas) {
            if (demanda.getStatus() != StatusDemanda.PENDENTE || demanda.getQuantidadeProdutos() <= 0 ) {
                continue;
            }

            if (melhor == null || demanda.getQuantidadeProdutos() > melhor.getQuantidadeProdutos()) {

                melhor = demanda;
            }
        }
        return melhor;
    }

    /**
     * Retorna o nome de apresentacao da estrategia
     *
     * @return nome da estrategia
     */
    public String getNomeEstrategia(){
        return "Estrategia do ChocoWonka mais Pedido";
    }
}
