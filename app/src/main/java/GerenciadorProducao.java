import java.util.ArrayList;

import excecoes.DemandaInvalidaException;
import excecoes.CompraInvalidaException;

/**
 * Gerencia o funcionamento da fabrica de chocolates
 *
 * <p>Eh responsável por controlar demandas, produtos fabricados, maquinas, materia-prima, orcamento, cenario operacional e estrategia de producao</p>
 */
public class GerenciadorProducao {
    /**
     * Lista de demandas cadastradas na fabrica
     */
    private ArrayList<Demanda> demandas = new ArrayList<>();
    /**
     * Lista de produtos aprovados e armazenados apos a fabricacao
     */
    private ArrayList<Produto> produtosFabricados = new ArrayList<>();
    /**
     * Lista de maquinas que compoem a linha de producao
     */
    private ArrayList<Maquina> maquinas = new ArrayList<>();
    /**
     * Materia-prima atualmente utilizada pela fabrica
     */
    private MateriaPrima materiaPrima;
    /**
     * Orcamento atual disponivel para compras, producao e manutencao
     */
    private double budget;
    /**
     * Estrategia atualmente utilizada para selecionar a proxima demanda
     */
    private EstrategiaProducao estrategiaAtual;
    /**
     * Cenario operacional que define parametros financeiros e de falha da fabrica
     */
    private Cenario cenario;
    /**
     * Numero que sera atribuido ao proximo lote produzido
     */
    private int proximoLote = 1;

    /**
     * Cria um gerenciador de producao para a materia-prima e o cenario informados
     *
     * <p>O construtor inicializa o orcamento a partir do cenario, define a estrategia de producao como EstrategiaOrdemChegada, cria as maquinas da linha de producao e cadastra uma demanda inicial para cada tipo de produto</p>
     *
     * @param materiaPrima materia-prima utilizada pela fabrica
     * @param cenario cenario operacional da fabrica
     */
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
    
    /**
     * Cadastra uma nova demanda na lista de demandas da fabrica
     *
     * @param tipoProduto tipo de produto solicitado
     * @param quantidade quantidade de unidades solicitadas
     */
    public void registrarDemanda(TipoProduto tipoProduto, int quantidade){
        demandas.add(new Demanda(tipoProduto, quantidade));
    }

    /**
     * Atualiza a quantidade de uma demanda existente.
     *
     * <p>Caso o tipo de produto ja esteja cadastrado, a quantidade eh atualizada e a demanda passa a ser pendente quando sua quantidade fica positiva. Caso o tipo ainda nao exista, uma nova demanda eh cadastrada, desde que a quantidade informada nao seja negativa</p>
     *
     * @param tipoProduto tipo de produto cuja demanda sera atualizada
     * @param quantidade valor a ser adicionado a demanda
     * @throws DemandaInvalidaException se o tipo ainda nao estiver cadastrado e a quantidade informada for negativa
     * @throws RecursoInsuficienteException se a atualizacao de uma demanda existente resultar em quantidade negativa
     */
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

        if (quantidade < 0){
            throw new DemandaInvalidaException("Nao podemos iniciar uma demanda negativa para um novo tipo de produto.");
        }

        registrarDemanda(tipoProduto, quantidade);
    }

    /**
     * Compra a quantidade informada de materia-prima e desconta seu custo do orcamento da fabrica
     *
     * @param quantidade quantidade de materia-prima a ser comprada
     * @throws CompraInvalidaException se a quantidade for negativa ou se o orcamento disponivel for insuficiente para a compra
     */
    public void comprarMateriaPrima(float quantidade){

        if (quantidade < 0){
            throw new CompraInvalidaException("Entre com uma quantidade positiva para comprar materia-prima");
        }

        double custototal = quantidade * materiaPrima.getCustoPorUnidade();

        //so compra materia prima se tiver verba
        if(budget < custototal){
            throw new CompraInvalidaException(" [ERRO] Nao ha verba suficiente para efetuar a compra.");
        }
        budget -= custototal;
        materiaPrima.adicionarEstoque(quantidade);
    }

    /**
     * Executa a fabricacao da demanda informada
     a
     * <p>O metodo cria produtos individualmente, verifica materia-prima e orcamento disponiveis, executa cada produto por todas as maquinas da linha e armazena somente os produtos aprovados na inspecao. A demanda eh atualizada ao final de acordo com a quantidade efetivamente produzida</p>
     *
     * <p>A fabricação eh interrompida quando uma maquina esta quebrada, quando faltam ingredientes, quando falta orcamento para operar a linha ou quando nao eh possivel criar o produto correspondente</p>
     *
     * @param alvo demanda que sera produzida
     */
    public void fabricarDemanda(Demanda alvo){
        if(alvo == null || alvo.getQuantidadeProdutos() <= 0){
            System.out.println("[ERRO] Não há demanda para este chocolate ainda!");
            return;
        }

        Maquina quebrada = primeiraMaquinaQuebrada();
        if(quebrada != null){
            System.out.println("[ERRO] " + quebrada.getNome() + " esta QUEBRADA. Faça a manutenção antes de produzir mais chocolates!.");
            return;
        }
        alvo.emProducao();

        int loteAtual = proximoLote++;
        int quantidadeDesejada = alvo.getQuantidadeProdutos();
        int produzidos = 0;

        for(int i = 0; i < quantidadeDesejada; i++){

            quebrada = primeiraMaquinaQuebrada();
            if(quebrada != null){
                System.out.println("[ALERTA] " + quebrada.getNome() + " quebrou durante a produção! Lote interrompido. Faça a manutenção.");
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
                boolean funcionando = m.processar(p);
                m.desligar();
                if(!funcionando){
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

    /**
     * Procura a primeira maquina da linha que nao esta apta a operar
     *
     * @return primeira maquina quebrada encontrada ou NULL se todas estiverem aptas
     */
    private Maquina primeiraMaquinaQuebrada(){
        for (Maquina m : maquinas){
            if(!m.estaApta()){
                return m;
            }
        }
        return null;
    }

    /**
     * Exibe no console o estado atual de todas as maquinas da fabrica, incluindo saude, status e custo de manutencao
     */
    public void exibirMaquinas(){
        System.out.println("===== MÁQUINAS =====");
        for (int i = 0; i < maquinas.size(); i++){
            Maquina m = maquinas.get(i);
            System.out.println(String.format("%d - %s | Saúde: %d/%d | Status: %s | Custo do reparo: R$ %.2f",
                i + 1, m.getNome(), m.getSaude(), m.getSaudeMaxima(),
                m.getStatusMaquina(), m.calcularCustoManutencao()));
        }
    }

    /**
     * Retorna a quantidade de maquinas presentes na linha de producao
     *
     * @return numero de maquinas cadastradas
     */
    public int getQuantidadeMaquinas(){
        return maquinas.size();
    }

    /**
     * Solicita a manutencao de uma maquina especifica
     *
     * <p>O numero recebido eh tratado como uma posicao iniciada em 1, correspondente a numeracao exibida ao usuario</p>
     *
     * @param numeroMaquina numero da maquina a ser reparada
     */
    public void realizarManutencao(int numeroMaquina){
        if(numeroMaquina < 1 || numeroMaquina > maquinas.size()){
            System.out.println("[ERRO] Máquina existente");
            return;
        }
        reparar(maquinas.get(numeroMaquina - 1));
    }

    /**
     * Realiza a manutencao de todas as maquinas que estejam marcadas como necessitando de manutencao
     *
     * <p>O metodo tenta reparar cada maquina cuja saude seja menor ou igual a 30, respeitando o orcamento disponivel</p>
     */
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

    /**
     * Repara uma maquina ate sua saude maxima, descontando do orcamento o custo correspondente ao desgaste acumulado
     *
     * @param m maquina que sera reparada
     */
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

    /**
     * Calcula o custo de operacao de todas as maquinas da linha para produzir uma unidade de produto
     *
     * @return soma dos custos de operacao das maquinas
     */
    private double calcularCustoProducao(){
        double total = 0;
        for (Maquina m : maquinas){
            total += m.getCustoOperacao();
        }
        return total;
    }

    /**
     * Exibe no console o orcamento atual disponivel para a fabrica
     */
    public void exibirBudget(){
        System.out.println(String.format("Caixa Atual: R$%.2f",budget));
    }

    /**
     * Retorna o orcamento atual disponivel
     *
     * @return saldo atual do orcamento
     */
    public double getBudget(){
        return budget;
    }

    /**
     * Exibe no console os produtos aprovados e armazenados na fabrica
     *
     * <p>Os produtos sao agrupados por nome e lote e sao apresentados com quantidade, tipo, lote, qualidade e quantidade de itens em risco</p>
     */
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

    /**
     * Exibe no console a quantidade atualmente disponivel da materia-prima
     */
    public void exibirEstoqueMateriaPrima(){
        System.out.println(materiaPrima.getNome() + " em estoque: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
    }

    /**
     * Exibe no console todas as demandas cadastradas, juntamente com suas quantidades e respectivos status
     */
    public void exibirDemandas(){
        System.out.println("=====DEMANDAS PENDENTES=====");
        for(Demanda d : demandas){
            System.out.println(d.getTipoProduto() + " -> " + d.getQuantidadeProdutos() + " unidade(s) (atendida: " + d.getStatus() + ")");
        }
    }

    /**
     * Define a estrategia que sera utilizada para selecionar a proxima demanda
     *
     * @param estrategia nova estrategia de producao
     */
    public void setEstrategia(EstrategiaProducao estrategia){
        this.estrategiaAtual = estrategia;
    }

    /**
     * Retorna a estrategia de producao atualmente ativa
    *
    * @return estrategia atualmente configurada
    */
    public EstrategiaProducao getEstrategia(){
        return this.estrategiaAtual;
    }

    /**
     * Seleciona a proxima demanda utilizando a estrategia ativa e solicita sua fabricacao
     */
    public void executarProximaProducao(){
        Demanda alvo = estrategiaAtual.selecionarDemanda(demandas, budget);
        fabricarDemanda(alvo);
    }

    /**
     * Procura uma demanda do tipo informado e inicia sua fabricacao
     *
     * <p>Somente a primeira demanda encontrada para o tipo eh processada</p>
     *
     * @param tipoProduto tipo de produto cuja demanda deve ser fabricada
     */
    public void fabricarPorTipo(TipoProduto tipoProduto){
        for (Demanda demanda: demandas){
            if (demanda.getTipoProduto() == tipoProduto){
                fabricarDemanda(demanda);
                break;
            }
        }
    }

    /**
     * Gera no console um relatorio geral contendo o diagnostico das maquinas e o diagnostico dos produtos fabricados
     */
    public void gerarAuditoriaGeral(){
        System.out.println("RELATÓRIO GERAL");
        gerarRelatorioMaquinas();
        gerarRelatorioProdutos();
    }

    /**
     * Gera no console o relatorio de diagnostico de todas as maquinas
     */
    public void gerarRelatorioMaquinas(){
        System.out.println("===== DIAGNÓSTICO DAS MÁQUINAS =====");
        for(Maquina maquina:maquinas){
            System.out.println(maquina.gerarRelatorioDiagnostico());
        }
    }

    /**
     * Gera no console o relatorio de diagnostico de todos os produtos aprovados e armazenados
     */
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

    /**
     * Retorna o cenario operacional atual da fabrica
     *
     * @return cenario configurado
     */
    public Cenario getCenario(){
        return this.cenario;
    }
}
