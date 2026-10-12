import java.util.List;

/**
 * Estrategia de producao que prioriza a demanda pendente de maior quantidade dentre aquelas cujo custo estimado cabe no orcamento disponivel
 */
public class EstrategiaMaximoProdutos implements EstrategiaProducao {
    /**
     * Seleciona a demanda pendente de maior quantidade cuja producao completa seja viavel dentro do orcamento disponivel
     *
     * @param demandas lista de demandas disponiveis
     * @param orcamentoDisponivel orcamento atualmente disponivel para producao
     * @return demanda elegivel com maior quantidade de produtos, ou NULL caso nenhuma demanda seja viavel
    */
    public Demanda selecionarDemanda(List<Demanda> demandas,double orcamentoDisponivel) {
        Demanda melhor = null;
        for (Demanda demanda : demandas) {
            if (demanda.getStatus() != StatusDemanda.PENDENTE || demanda.getQuantidadeProdutos() <= 0) {
                continue;
            }

            if (!demanda.isViavel(orcamentoDisponivel)){
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
    public String getNomeEstrategia() {
        return "Estrategia de Maior Producao de ChocoWonka";
    }
}