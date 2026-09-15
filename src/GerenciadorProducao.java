import java.util.ArrayList;

public class GerenciadorProducao {
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
        maquinas.add(new EstacaoInspecao());

        //cadastrando demandas de produtos de diversas qualidades
        registrarDemanda("alta", 0);
        registrarDemanda("media", 0);
        registrarDemanda("baixa", 0);
    }

    public void registrarDemanda(String tipoProduto, int quantidade){
        demandas.add(new Demanda(tipoProduto, quantidade));
    }

    public void atualizarDemanda(String tipoProduto, int quantidade){
        for(Demanda d : demandas){
            if (d.getTipoProduto().equalsIgnoreCase(tipoProduto)){
                d.atualizarQuantidade(quantidade);
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

    public void fabricarDemanda(String tipoProduto){

        Demanda alvo = null;

        for(Demanda d : demandas){
            if(d.getTipoProduto().equalsIgnoreCase(tipoProduto)){
                alvo = d;
                break;
            }
        }

        if(alvo == null || alvo.getQuantidadeProdutos() <= 0){
            System.out.println("[ERRO] Nao há demanda para este chocolate ainda!");
            return;
        }

        int quantidadeDesejada = alvo.getQuantidadeProdutos();
        int produzidos = 0;

        for(int i = 0; i < quantidadeDesejada; i++){
            Produto p = criarProdutoPorTipo(tipoProduto);

            if (p == null){
                break;
            }

            if(!materiaPrima.verificarDisponibilidade(p.getMateriaPrimaPorUnidade())){
                System.out.println("[ERRO] Ingredientes insuficientes para produzir " + p.getNome() + "!");
                break;
            }

            double custoOperacaoLinha = calcularCustoProducao();
            if(budget < custoOperacaoLinha){
                System.out.println("[ERRO] Verba insuficiente para rodar as máquinas!");
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
                produzidos++;
                System.out.println("[OK] " + p.getNome() + " #" + p.getId() + " aprovado e guardado no armazém!");
            } else {
                System.out.println("[ALERTA] Produto id #" + p.getId() + " falhou na linha e o chocolate foi destacado!");
            }
        }
        /*desconta da fila so o que foi tentado
        e marca como atendida quando nao sobrar
        mais nada pendente desse tipo
        */
        alvo.atualizarQuantidade(-produzidos);
        if(alvo.getQuantidadeProdutos() <= 0){
            alvo.atender();
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
        System.out.println("=====Armazém de chocolates prontos=====");

        if(produtosFabricados.isEmpty()){
            System.out.println("O mundo precisa dos nossos chocolates! Vamos fabricar!");
        }

        for (Produto p : produtosFabricados){
            System.out.println("ID: " + p.getId() + " | " + p.getNome() + "| Status: " + p.getStatus());
        }
    }

    public void exibirEstoqueMateriaPrima(){
        System.out.println(materiaPrima.getNome() + " em estoque: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
    }

    public void exibirDemandas(){
        System.out.println("=====DEMANDAS PENDENTES=====");
        for(Demanda d : demandas){
            System.out.println(d.getTipoProduto() + " -> " + d.getQuantidadeProdutos() + " unidade(s) (atendida: " + d.isAtendida() + ")");
        }
    }
}
