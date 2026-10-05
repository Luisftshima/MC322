
import java.util.List;

public class EstrategiaOrdemChegada implements EstrategiaProducao{

    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        for (Demanda demanda: demandas){
            if (demanda.getStatus() != StatusDemanda.PENDENTE || demanda.getQuantidadeProdutos() <= 0 ){
                return demanda;
            }
        }
		return null;
    }

    public String getNomeEstrategia(){
        return "Chocofirst-In, Chocofirst-Out";
    }
}
