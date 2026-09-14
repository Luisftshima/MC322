import java.util.ArrayList;

public abstract class GerenciadorProducao {
    private ArrayList<Demanda> demandas = new ArrayList<>();
    private ArrayList<Produto> produtosFabricados = new ArrayList<>();
    private ArrayList<Maquina> maquinas = new ArrayList<>();
    private MateriaPrima materiaPrima;
    private double budget;

    public GerenciadorProducao(MateriaPrima materiaPrima, double budgetInicial){
        this.materiaPrima = materiaPrima;
        this.budget = budgetInicial;

        maquinas.add(new Misturador());
        maquinas.add(new Embaladora());
        maquinas.add(new EsteiraInspecao());
    }

    public void registrarDemanda(String tipoProduto, int qualidade){
        demandas.add(new Demanda(tipoProduto, quantidade));
    }

    public void comprarMateriaPrima(float quantidade){
        double custototal = quantidade * materiaPrima.getCustoPorUnidade();
        if(budget >= custototal){
            budget -= custototal;
            materiaPrima.adicionarEstoque(quantidade);
            System.out.println("[OBA!] Ingredientes comprados!. Novo saldo: R$" + budget);
        } else {
            System.out.println("[ERRO] Dinheiro insuficiente!");
        }
    }

    public void fabricarDemanda(int indexDemanda){
        if(indexDemanda < 0 || indexDemanda >= demandas.size()) return;
        Demanda d = demandas.get(indexDemanda);

        for(int i = 0; i < d.getQuantidadeProdutos(); i++){
            Produto p = criarProdutoPorTipo(d.getTipoProduto());
            if (p == null) break;

            if(!materiaPrima.verificarDisponibilidade(p.getMateriaPrimaPorUnidade())){
                System.out.println("[ERRO] Ingredientes insuficientes para produzir o chocolate!");
                break;
            }

            double custoOperacaoLinha = calcularCustoProducao()
            if(budget < custoOperacaoLinha){
                System.out.println("[ERRO] Budget insuficiente para rodar as máquinas!");
                break;
            }

            //consumindo materia prima e dinheiro
            materiaPrima.consumir(p.getMateriaPrimaPorUnidade());
            budget -= custoOperacaoLinha;

            boolean aprovado = true;
            for (Maquina m:maquinas){
                if(!m.processar(p)){
                    aprovado = false;
                    break;
                }
            }

            if(aprovado){
                produtosFabricados.add(p);
            } else {
                System.out.println("[ALERTA] Produto id #" + p.getId() + " falhou na linha e o chocolate foi destacado!");
            }
        }
        d.atender();
    }

    //calcula o custo total das maquinas para produzir o chocolate
    private double calcularCustoProducao(){
        double total = 0;
        for (Maquina m : maquinas){
            total += m.getCustoOperacao();
        }
        return total;
    }

    private Produto criarProdutoPorTipo(String tipo){
        switch(tipo.toLowerCase()){
            case "alta": return new TrufaArtesanal();
            case "media": return new BarraChocolate();
            case "baixa": return new GuardaChuvaChocolate();
            default: return null;
        }
    }

    public void exibirBudget(){
        System.out.println("Caixa Atual: R$" + budget);
    }

    public void exibirArmazem(){
        System.out.println("=====Armazém de chocolates prontos=====");
        for (Produto p : produtosFabricados){
            System.out.println("ID: " + p.getId() + " | " + p.getNome() + "| Status: " + p.getStatus());
        }
    }
}
