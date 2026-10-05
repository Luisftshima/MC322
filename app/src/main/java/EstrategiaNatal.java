
import java.util.List;

public class EstrategiaNatal implements EstrategiaProducao{
    
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel){
        Demanda melhorChocotone = maiorDemandaDoTipo(demandas, TipoProduto.CHOCOTONE);

        if(melhorChocotone != null){
            return melhorChocotone;
        }

        return maiorDemandaGeral(demandas);
    }

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

    public String getNomeEstrategia(){
        return "Estratégia da Ceia de Natal";
    }
}
