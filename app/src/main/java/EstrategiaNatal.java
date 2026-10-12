import java.util.List;

/**
 * Estrategia de producao que prioriza demandas de chocotone
 *
 * <p>Quando existe pelo menos uma demanda pendente de chocotone, a maior demanda desse tipo eh selecionada. Na ausencia de chocotones elegiveis, a estrategia seleciona a maior demanda pendente entre os demais produtos</p>
 */
public class EstrategiaNatal implements EstrategiaProducao{
    
    /**
     * Seleciona a demanda a ser produzida dando prioridade aos chocotones
     *
     * <p>O orcamento disponivel nao eh utilizado diretamente na selecao</p>
     *
     * @param demandas lista de demandas disponiveis
     * @param orcamentoDisponivel orcamento atualmente disponivel para producao
     * @return maior demanda pendente de chocotone quando existente, caso contrario a maior demanda pendente geral
     */
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel){
        Demanda melhorChocotone = maiorDemandaDoTipo(demandas, TipoProduto.CHOCOTONE);

        if(melhorChocotone != null){
            return melhorChocotone;
        }

        return maiorDemandaGeral(demandas);
    }

    /**
     * Encontra a maior demanda pendente de um tipo especifico de produto
     *
     * @param demandas lista de demandas a ser analisada
     * @param tipo tipo de produto que deve ser priorizado
     * @return maior demanda pendente do tipo informado ou NULL caso nao exista nenhuma elegivel
     */
    private Demanda maiorDemandaDoTipo(List<Demanda> demandas, TipoProduto tipo){
        Demanda melhor = null;
        for (Demanda demanda : demandas){
            if (demanda.getStatus() != StatusDemanda.PENDENTE || demanda.getQuantidadeProdutos() <= 0 || demanda.getTipoProduto() != tipo){
                continue;
            }
            if (melhor == null || demanda.getQuantidadeProdutos() > melhor.getQuantidadeProdutos()){
                melhor = demanda;
            }
        }
        return melhor;
    }

    /**
     * Encontra a maior demanda pendente entre todos os tipos de produto
     *
     * @param demandas lista de demandas a ser analisada
     * @return maior demanda pendente encontrada ou NULL caso nao exista nenhuma elegivel
     */
    private Demanda maiorDemandaGeral(List<Demanda> demandas){
        Demanda melhor = null;
        for(Demanda demanda : demandas){
            if(demanda.getStatus() != StatusDemanda.PENDENTE || demanda.getQuantidadeProdutos() <= 0 ){
                continue;
            }
            if (melhor == null || demanda.getQuantidadeProdutos() > melhor.getQuantidadeProdutos()){
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
        return "Estrategia da Ceia de Natal";
    }
}
