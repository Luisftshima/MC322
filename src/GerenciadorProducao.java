import java.util.ArrayList;

public class GerenciadorProducao {
    private ArrayList<Demanda> demandas = new ArrayList<>();
    private ArrayList<Produto> produtosFabricados = new ArrayList<>();
    private ArrayList<Maquina> maquinas = new ArrayList<>();
    private MateriaPrima materiaPrima;
    private double budget;
    private EstrategiaProducao estrategiaAtual;
    private Cenario cenario;
    private int proximoLote = 1;

    public GerenciadorProducao(MateriaPrima materiaPrima, Cenario cenario){
        this.materiaPrima = materiaPrima;
        this.cenario = cenario;
        this.budget = cenario.getOrcamentoInicial();
        this.estrategiaAtual = new EstrategiaOrdemChegada();

        maquinas.add(new Misturador("Super Misturador de Ingredientes", 1000, 0.15, 0.25, 100, cenario));
        maquinas.add(new Embaladora("Embaladora Wonka", 500, 0.1, 1.5, 100, cenario));
        maquinas.add(new EstacaoInspecao("Sensor de controle de qualidade", 200, 0.05, 0.5, 100, cenario));


        //cadastrando demandas de produtos de diversas qualidades
        registrarDemanda(TipoProduto.OVO_ARTESANAL, 0);
        registrarDemanda(TipoProduto.BOMBONS_SORTIDOS, 0);
        registrarDemanda(TipoProduto.GUARDA_CHUVA, 0);
        registrarDemanda(TipoProduto.CHOCOTONE, 0);

    }

    public void registrarDemanda(TipoProduto tipoProduto, int quantidade){
        demandas.add(new Demanda(tipoProduto, quantidade));
    }

    public void atualizarDemanda(TipoProduto tipoProduto, int quantidade){
        for(Demanda demanda : demandas){
            if (demanda.getTipoProduto() == tipoProduto){
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

        Maquina quebrada = primeiraMaquinaQuebrada();
        if(quebrada != null){
            System.out.println("[ERRO] " + quebrada.getNome() + " esta QUEBRADA. Faça a manutenção (menu 7) antes de produzir.");
            return;
        }
        alvo.emProdução();

        int loteAtual = proximoLote++;
        int quantidadeDesejada = alvo.getQuantidadeProdutos();
        int produzidos = 0;

        for(int i = 0; i < quantidadeDesejada; i++){

            quebrada = primeiraMaquinaQuebrada();
            if(quebrada != null){
                System.out.println("[ALERTA] " + quebrada.getNome() + " quebrou durante a produção! Lote interrompido; faça a manutenção (menu 7).");
                break;
            }

            Produto p = Produto.criarPorTipo(alvo.getTipoProduto());

            if (p == null){
                break;
            }
            p.setLote(loteAtual);

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
                boolean ok = m.processar(p);
                m.desligar();
                if(!ok){
                    aprovado = false;
                    break;
                }
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

    private Maquina primeiraMaquinaQuebrada(){
        for (Maquina m : maquinas){
            if(!m.estaApta()){
                return m;
            }
        }
        return null;
    }

    public void exibirMaquinas(){
        System.out.println("===== MÁQUINAS =====");
        for (int i = 0; i < maquinas.size(); i++){
            Maquina m = maquinas.get(i);
            System.out.println(String.format("%d - %s | Saúde: %d/%d | Status: %s | Custo do reparo: R$ %.2f",
                i + 1, m.getNome(), m.getSaude(), m.getSaudeMaxima(),
                m.getStatusMaquina(), m.calcularCustoManutencao()));
        }
    }

    public int getQuantidadeMaquinas(){
        return maquinas.size();
    }

    public void realizarManutencao(int numeroMaquina){
        if(numeroMaquina < 1 || numeroMaquina > maquinas.size()){
            System.out.println("[ERRO] Máquina existente");
            return;
        }
        reparar(maquinas.get(numeroMaquina - 1));
    }

    //quando a saude das maquinas estão abaixo de 30
    public void realizarManutencaoGeral(){
        boolean algumaPrecisava = false;

        for (Maquina m : maquinas){
            if (m.precisaManutencao()){
                algumaPrecisava = true;
                reparar(m);
            }
        }

        if(!algumaPrecisava){
            System.out.println("[OK] Nenhuma máquina precisa de manutenção urgente.");
        }
    }

    private void reparar(Maquina m){
        if(!m.precisaReparo()){
            System.out.println("[OK] " + m.getNome() + " já está com a saúde máxima.");
            return;
        }
        double custo = m.calcularCustoManutencao();
        if(budget < custo){
            System.out.println("[ERRO] Verba insuficiente para reparar " + m.getNome()
                + " (custo R$ " + String.format("%.2f", custo) + ").");
            return;
        }
        budget -= custo;
        m.realizarManutencao();
        System.out.println("[OBA!] " + m.getNome() + " reparada por R$ " + String.format("%.2f", custo)
            + ". Novo saldo: R$" + String.format("%.2f", budget));
    }

    //calcula o custo total das maquinas para produzir o chocolate
    private double calcularCustoProducao(){
        double total = 0;
        for (Maquina m : maquinas){
            total += m.getCustoOperacao();
        }
        return total;
    }

    public void exibirBudget(){
        System.out.println(String.format("Caixa Atual: R$%.2f",budget));
    }

    public double getBudget(){
        return budget;
    }

    public void exibirArmazem(){
        System.out.println("===== ARMAZÉM DE CHOCOLATES =====");

        if (produtosFabricados.isEmpty()) {
            System.out.println("O mundo precisa dos nossos chocolates! Vamos fabricar!");
            return;
        }

        java.util.LinkedHashMap<String, java.util.List<Produto>> grupos = new java.util.LinkedHashMap<>();
        for (Produto p : produtosFabricados) {
            String chave = p.getNome() + "#" + p.getLote();
            grupos.computeIfAbsent(chave, k -> new java.util.ArrayList<>()).add(p);
        }

        System.out.printf("%-28s | %5s | %-10s | %-8s | %-6s | %s%n",
            "Produto", "Qtd.", "Tipo", "Lote", "Qual.", "Risco");
        System.out.println("-".repeat(85));

        for (java.util.List<Produto> itens : grupos.values()) {
            Produto amostra = itens.get(0);

            int emRisco = 0;
            for (Produto p : itens) {
                if (p.precisaManutencao()) {
                    emRisco++;
                }
            }
            String risco = (emRisco == 0) ? "OK" : emRisco + " em risco";

            System.out.printf("%-28s | %5d | %-10s | #%-7d | %5.0f%% | %s%n",
                amostra.getNome(), itens.size(), amostra.getTipo(), amostra.getLote(),
                amostra.getQualidade() * 100, risco);
        }
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

    public void fabricarPorTipo(TipoProduto tipoProduto){
        for (Demanda demanda: demandas){
            if (demanda.getTipoProduto() == tipoProduto){
                fabricarDemanda(demanda);
                break;
            }
        }
    }

    public void gerarAuditoriaGeral(){
        System.out.println("RELATÓRIO GERAL");
        gerarRelatorioMaquinas();
        gerarRelatorioProdutos();
    }

    public void gerarRelatorioMaquinas(){
        System.out.println("===== DIAGNÓSTICO DAS MÁQUINAS =====");
        for(Maquina maquina:maquinas){
            System.out.println(maquina.gerarRelatorioDiagnostico());
        }
    }

    public void gerarRelatorioProdutos(){
        System.out.println("===== DIAGNÓSTICO DOS PRODUTOS =====");
        if(produtosFabricados.isEmpty()){
            System.out.println("Nenhum ChocoWonka fabricado ainda!");
            return;
        }
        for(Produto produto:produtosFabricados){
            System.out.println(produto.gerarRelatorioDiagnostico());
        }
    }

    public Cenario getCenario(){
        return this.cenario;
    }
}
