import java.util.List;

public class EstrategiaMaiorDemanda implements EstrategiaProducao{

    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda melhor = null;

        for (Demanda demanda : demandas) {
            if (demanda.getQuantidadeProdutos() > melhor.getQuantidadeProdutos()) {
                melhor = demanda;
            }
        }

        return melhor;
    }

    public String getNomeEstrategia(){
        return "Estratégia de Chocolate mais Amado";
    }
}
