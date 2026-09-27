import java.util.Scanner;

public class Main {

    private static final String LINHA_DUPLA = "======================================================";
    private static final String LINHA_SIMPLES = "------------------------------------------------------";    
    public static void main(String[] args){

        Scanner entrada = new Scanner(System.in);
        
        exibirBoasVindas();

        //Criando a matéria prima, por enquanto temos só 1
        MateriaPrima chocolate = new MateriaPrima(
            "Chocolate", 2000, "g", 
            0.05);
        
        Cenario cenario = escolherCenario(entrada);

        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, cenario);
        
        boolean rodando_programa = true;

        while (rodando_programa){

            exibirCabecalho(fabrica, cenario);

            exibirMenuPrincipal();

            switch(lerInteiro(entrada)){
                case 1: menuDemandas(entrada, fabrica); break;
                case 2: menuFabricacao(entrada, fabrica); break;
                case 3: menuConsultar(entrada, fabrica); break;
                case 4: menuComprarMateriaPrima(entrada, fabrica); break;
                case 5: menuEstrategia(entrada, fabrica); break;
                case 6: menuAuditoria(entrada, fabrica); break;
                case 0:
                    rodando_programa = false;
                    System.out.println("Os confeiteiros e a fábrica precisam descansar...");
                    break;
                default:
                    System.out.println("Entrada Inválida");
            }
        }
        entrada.close();
    }

    private static void exibirBoasVindas(){
        System.out.println(LINHA_DUPLA);
        System.out.println("BEM-VINDO À FANTÁSTICA FÁBRICA DE CHOCOLATE");
        System.out.println(LINHA_DUPLA);

        System.out.println("Sejam muito bem-vindos a nossa fábrica de chocolates!");
        System.out.println("Uma coisa é mais do que certa, a vida nunca foi tão doce!");
        System.out.println("Produzimos ovos artesanais, bombons e guarda-chuvas (de chocolate)!");
        System.out.println("Confeiteiros: Alex Lei e Luis Felipe Tagawa Shimamoto");
        System.out.println(LINHA_DUPLA);
    }

    private static Cenario escolherCenario(Scanner entrada){
        System.out.println("ESCOLHA O CENÁRIO DA OPERAÇÃO");
        System.out.println("1 - Ideal (budget farto, poucas falhas, desgaste reduzido)");
        System.out.println("2 - Apocalíptico (budget limitado, mais falhas, desgaste acelerado)");
        System.out.print("Escolha: ");

        while(true){
            int opcao = lerInteiro(entrada);
            if(opcao == 1) return Cenario.IDEAL;
            if(opcao == 2) return Cenario.APOCALIPTICO;
            System.out.println("[ERRO] Escolha 1 ou 2: ");
        }
    }

    private static void exibirCabecalho(GerenciadorProducao fabrica, Cenario cenario){
        System.out.println();
        System.out.println(LINHA_DUPLA);
        System.out.println("FANTÁSTICA FÁBRICA DE CHOCOLATE");
        System.out.println("Estratégia atual: " + fabrica.getEstrategia().getNomeEstrategia());
        System.out.println("Cenário ativo: " + cenario.getNome());
        System.out.println("Budget atual: R$ %.2d%n", fabrica.getBudget());
        System.out.println(LINHA_DUPLA);
    }

    private static void exibirMenuPrincipal(){
        System.out.println("1 - Demandas");
        System.out.println("2 - Fabricação");
        System.out.println("3 - Consultar");
        System.out.println("4 - Comprar matéria-prima");
        System.out.println("5 - Gerenciar estratégia");
        System.out.println("6 - Auditoria");
        System.out.println("0 - Sair");
        System.out.println(LINHA_SIMPLES);
        System.out.println("Escolha: ");
    }

    private static void menuDemandas(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[DEMANDAS]");
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Atualizar demanda de Ovos Artesanais (Alta)");
            System.out.println("2 - Atualizar demanda de Bombons Sortidos (Média)");
            System.out.println("3 - Atualizar demanda de Guarda-chuvas de Chocolate (Baixa)");
            System.out.println("4 - Listar demandas");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            switch(lerInteiro(entrada)){
                case 1: atualizarDemanda(entrada, fabrica, NivelQualidade.ALTA, "Ovo Artesanal"); break;
                case 2: atualizarDemanda(entrada, fabrica, NivelQualidade.MEDIA, "Bombons Sortidos"); break;
                case 3: atualizarDemanda(entrada, fabrica, NivelQualidade.BAIXA, "Guarda-chuva de Chocolate"); break;
                case 4: fabrica.exibirDemandas(); break;
                case 0: voltar = true; break;
                default: System.out.println("[ERRO] Opção inválida.");
            }
        }
    }

    private static void atualizarDemanda(Scanner entrada, GerenciadorProducao fabrica, NivelQualidade tipo, String nome_produto){
        System.out.println("Quantas unidades de " + nome_produto + " adicionar a demanda?");
        int quantidade = lerInteiro(entrada);
        fabrica.atualizarDemanda(tipo, quantidade);
        System.out.println("[Legal!] Demanda de " + nome_produto + " atualizada");
    }

    private static void menuFabricacao(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[FABRICAÇÃO]");
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Processar próxima demanda (usa estratégia ativa: "
                + fabrica.getEstrategia().getNomeEstrategia() + ")"
            );
            System.out.println("2 - Fabricar Ovo Artesanal");
            System.out.println("3 - Fabricar Bombons Sortidos");
            System.out.println("4 - Fabricar Guarda-chuva de Chocolate");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            switch(lerInteiro(entrada)){
                case 1: fabrica.executarProximaProducao(); break;
                case 2: fabrica.fabricarPorTipo(NivelQualidade.ALTA);
                case 3: fabrica.fabricarPorTipo(NivelQualidade.MEDIA);
                case 4: fabrica.fabricarPorTipo(NivelQualidade.BAIXA);
                case 0: voltar = true; break;
                default: System.out.println("[ERRO] Opção inválida");
            }
        }
    }

    private static void menuConsultar(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[CONSULTAR]");
            System.out.println("1 - Ver armazém (produtos acabados");
            System.out.println("2 - Ver estoque de matéria-prima");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            switch(lerInteiro(entrada)){
                case 1: fabrica.exibirArmazem(); break;
                case 2: fabrica.exibirEstoqueMateriaPrima(); break;
                case 0: voltar = true; break;
                default: System.out.println("[ERRO] Opção inválida");
            }
        }
    }

    private static void menuComprarMateriaPrima(Scanner entrada, GerenciadorProducao fabrica){
        System.out.println();
        System.out.println("[COMPRAR MATÉRIA-PRIMA]");
        System.out.println(LINHA_SIMPLES);
        System.out.println("Quantos gramas de chocolate quer comprar?");
        float quantidade = lerFloat(entrada);
        fabrica.comprarMateriaPrima(quantidade);
    }

    private static void menuEstrategia(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[GERENCIAR ESTRATÉGIA]");
            System.out.println(LINHA_SIMPLES);
            System.out.println("Estratégia atual: " + fabrica.getEstrategia().getNomeEstrategia());
            System.out.println("1 - Chocofirst-In, Chocofirst-Out (Ordem de Chegada)");
            System.out.println("2 - ChocoWonka mais Pedido (Maior demanda)");
            System.out.println("3 - Maior Produção de ChocoWonka (Máximo de Produtos)");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            EstrategiaProducao nova = null;
            switch(lerInteiro(entrada)){
                case 1: nova = new EstrategiaOrdemChegada(); break;
                case 2: nova = new EstrategiaMaiorDemanda(); break;
                case 3: nova = new EstrategiaMaximoProdutos(); break;
                case 0: voltar = true; break;
                default: System.out.println("[ERRO] Opção Inválida.");
            }

            if(nova != null){
                fabrica.setEstrategia(nova);
                System.out.println("[OK] Estratégia alterada para: " + nova.getNomeEstrategia());
            }
        }
    }

    private static void menuAuditoria(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[AUDITORIA] (interface Auditavel)");
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Relatório geral (máquinas + produtos)");
            System.out.println("2 - Detalhar máquinas");
            System.out.println("3 - Detalhar produtos");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            switch(lerInteiro(entrada)){
                case 1: fabrica.gerarAuditoriaGeral(); break;
                case 2: fabrica.gerarRelatorioMaquinas(); break;
                case 3: fabrica.gerarRelatorioProdutos(); break;
                case 0: voltar = true; break;
                default: System.out.println("[ERRO] Opção inválida.");
            }
        }
    }

    private static int lerInteiro(Scanner entrada){
        while(true){
            String texto = entrada.nextLine().trim();
            try{
                return Integer.parseInt(texto);
            }catch (NumberFormatException e){
                System.out.println("[ERRO] Digite apenas números, por gentileza: ");
            }
        }
    }

    private static float lerFloat(Scanner entrada){
        while(true){
            String texto = entrada.nextLine().trim();
            try{
                return Float.parseFloat(texto);
            } catch (NumberFormatException e){
                System.out.println("[ERRO] Digite apenas números, por gentileza: ");
            }
        }
    }
}
