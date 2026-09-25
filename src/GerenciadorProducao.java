import java.util.ArrayList;

public class GerenciadorProducao {
    private ArrayList<Demanda> demandas = new ArrayList<>();
    private ArrayList<Produto> produtosFabricados = new ArrayList<>();
    private ArrayList<Maquina> maquinas = new ArrayList<>();
    private MateriaPrima materiaPrima;
    private double budget;
    private EstrategiaProducao estrategiaAtual;
    private Cenario cenario;

    public GerenciadorProducao(MateriaPrima materiaPrima, Cenario cenario){
        this.materiaPrima = materiaPrima;
        this.cenario = cenario;
        this.budget = cenario.getOrcamentoInicial();
        this.estrategiaAtual = new EstrategiaOrdemChegada();

        maquinas.add(new Misturador("Super Misturador de Ingredientes", 1000, 0.15, 0.25, 100, cenario));
        maquinas.add(new Embaladora("Embaladora Wonka", 500, 0.1, 1.5, 100, cenario));
        maquinas.add(new EstacaoInspecao("Sensor de controle de qualidade", 200, 0.05, 0.5, 100, cenario));


        //cadastrando demandas de produtos de diversas qualidades
        registrarDemanda("alta", 0);
        registrarDemanda("media", 0);
        registrarDemanda("baixa", 0);

    }

    public void registrarDemanda(String tipoProduto, int quantidade){
        demandas.add(new Demanda(tipoProduto, quantidade));
    }

    public void atualizarDemanda(String tipoProduto, int quantidade){
        for(Demanda demanda : demandas){
            if (demanda.getTipoProduto().equalsIgnoreCase(tipoProduto)){
                demanda.atualizarQuantidade(quantidade);
                if (demanda.getQuantidadeProdutos() > 0){
                    demanda.pendente();
                }
                return;
            }
        }
        //se o tipo nao existe ainda
        registrarDemanda(tipoProduto, quantidade);
    }

    public void comprarMateriaPrima(float quantidade){
        double custototal = quantidade * materiaPrima.getCustoPorUnidade();

        //so compra materia prima se tiver verba
        if(budget >= custototal){
            budget -= custototal;
            materiaPrima.adicionarEstoque(quantidade);
            System.out.println("[OBA!] Ingredientes comprados! Novo saldo: R$" + String.format("%.2f", budget));
        } else {
            System.out.println("[ERRO] Dinheiro insuficiente para a compra!");
        }
    }

    public void fabricarDemanda(Demanda alvo){

        if(alvo == null || alvo.getQuantidadeProdutos() <= 0){
            System.out.println("[ERRO] Nao há demanda para este chocolate ainda!");
            return;
        }
        alvo.emProdução();

        int quantidadeDesejada = alvo.getQuantidadeProdutos();
        int produzidos = 0;

        for(int i = 0; i < quantidadeDesejada; i++){
            Produto p = criarProdutoPorTipo(alvo.getTipoProduto());

            if (p == null){
                break;
            }

            if(!materiaPrima.verificarDisponibilidade(p.getMateriaPrimaPorUnidade())){
                System.out.println("[ERRO] Ingredientes insuficientes para produzir " + p.getNome() + "!");
                alvo.cancelar();
                break;
            }

            double custoOperacaoLinha = calcularCustoProducao();
            if(budget < custoOperacaoLinha){
                System.out.println("[ERRO] Verba insuficiente para rodar as máquinas!");
                alvo.cancelar();
                break;
            }

            //consumindo materia prima e dinheiro
            materiaPrima.consumir(p.getMateriaPrimaPorUnidade());
            budget -= custoOperacaoLinha;

            boolean aprovado = true;
            for (Maquina m:maquinas){
                m.ligar();
                if(!m.processar(p)){
                    aprovado = false;

                    break;
                }
                m.desligar();
            }

            if(aprovado){
                produtosFabricados.add(p);
                produzidos++;
                System.out.println("[OK] " + p.getNome() + " #" + p.getId() + " aprovado e guardado no armazém!");
            } else {
                System.out.println("[ALERTA] Produto id #" + p.getId() + " falhou na linha e o chocolate foi destacado!");
            }
        }
       
        alvo.atualizarQuantidade(-produzidos);
        if(alvo.getStatus() != StatusDemanda.CANCELADA){
            if(alvo.getQuantidadeProdutos() <= 0){
                alvo.atender();
            }else{
                alvo.pendente();
            }
        }
          System.out.println("[YUMMY] Produção de chocolates finalizada: " + produzidos + "/" + quantidadeDesejada + " unidades aprovadas.");
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
            case "alta": return new OvoArtesanal();
            case "media": return new BombonsSortidos();
            case "baixa": return new GuardaChuva();
            default: return null;
        }
    }

    public void exibirBudget(){
        System.out.println(String.format("Caixa Atual: R$%.2f",budget));
    }

    public void exibirArmazem(){
        System.out.println("===== ARMAZÉM DE CHOCOLATES =====");

        if (produtosFabricados.isEmpty()) {
            System.out.println("O mundo precisa dos nossos chocolates! Vamos fabricar!");
            return;
        }

        int ovos = 0;
        int bombons = 0;
        int guardaChuva = 0;

        for (Produto p : produtosFabricados) {
            switch (p.getTipo()) {
                case "Alta Qualidade":
                    ovos++;
                    break;

                case "Média Qualidade":
                    bombons++;
                    break;

                case "Baixa Qualidade":
                    guardaChuva++;
                    break;
            }
        }

        System.out.println("Ovos Artesanais | Quantidade:" + ovos);
        System.out.println("Bombons Sortidos | Quantidade:" + bombons);
        System.out.println("Guarda-chuvas | Quantidade:" + guardaChuva);
    }

    public void exibirEstoqueMateriaPrima(){
        System.out.println(materiaPrima.getNome() + " em estoque: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
    }

    public void exibirDemandas(){
        System.out.println("=====DEMANDAS PENDENTES=====");
        for(Demanda d : demandas){
            System.out.println(d.getTipoProduto() + " -> " + d.getQuantidadeProdutos() + " unidade(s) (atendida: " + d.getStatus() + ")");
        }
    }

    public void setEstrategia(EstrategiaProducao estrategia){
        this.estrategiaAtual = estrategia;
    }

    public EstrategiaProducao getEstrategia(){
        return this.estrategiaAtual;
    }

    public void executarProximaProducao(){
        Demanda alvo = estrategiaAtual.selecionarDemanda(demandas, budget);
        fabricarDemanda(alvo);
    }

    public void fabricarPorTipo(String tipoProduto){
        for (Demanda demanda: demandas){
            if (demanda.getTipoProduto().equalsIgnoreCase(tipoProduto)){
                fabricarDemanda(demanda);
                break;
            }
        }
    }

    public void gerarAuditoriaGeral(){
        System.out.println("RELATÓRIO GERAL");
        System.out.println("MÁQUINAS");
        for (Maquina maquina: maquinas){
            System.out.println(maquina.gerarRelatorioDiagnostico());
        }
        System.out.println("PRODUTOS");
        for (Produto produto: produtosFabricados){
            System.out.println(produto.gerarRelatorioDiagnostico());
        }
    }

}
