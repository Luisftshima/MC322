import java.util.List;

public class EstrategiaOrdemChegada implements EstrategiaProducao{

    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        for (Demanda demanda: demandas){
            if (demanda.getStatus().equals(StatusDemanda.PENDENTE)){
                return demanda;
            }
        }
		return null;
    }

    public String getNomeEstrategia(){
        return "Estratégia de Quem Chegou Primeiro";
    }
}
