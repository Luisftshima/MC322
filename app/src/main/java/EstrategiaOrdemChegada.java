import java.util.List;

/**
 * Estrategia de producao baseada na ordem em que as demandas aparecem na lista de demandas da fabrica
 */
public class EstrategiaOrdemChegada implements EstrategiaProducao{

    /**
     * Percorre a lista de demandas em ordem e seleciona a primeira demanda encontrada de acordo com as condicoes implementadas pela estrategia
     *
     * <p>O orcamento disponivel nao eh utilizado diretamente na selecao</p>
     *
     * @param demandas lista de demandas disponiveis
     * @param orcamentoDisponivel orcamento atualmente disponivel para producao
     * @return demanda selecionada ou NULL caso nenhuma demanda seja retornada
     */
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        for (Demanda demanda: demandas){
            if (demanda.getStatus() == StatusDemanda.PENDENTE && demanda.getQuantidadeProdutos() > 0 ){
                return demanda;
            }
        }
		return null;
    }

    /**
     * Retorna o nome de apresentacao da estrategia
     *
     * @return nome da estrategia
     */
    public String getNomeEstrategia(){
        return "Chocofirst-In, Chocofirst-Out";
    }
}
