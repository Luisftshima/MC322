import java.util.Scanner;

import excecoes.CompraInvalidaException;
import excecoes.OpcaoInvalidaException;
import excecoes.DemandaInvalidaException;
import excecoes.MaquinaIndisponivelException;
import excecoes.RecursoInsuficienteException;
import excecoes.MaquinaInexistenteException;

/**
 * Classe principal responsavel por iniciar e executar a interface de console da fabrica de chocolates
 */
public class Main {
    /**
     * Linhas de separacao utilizada na apresentacao da interface.
     */
    private static final String LINHA_DUPLA = "======================================================";
    private static final String LINHA_SIMPLES = "------------------------------------------------------";    
    /**
     * Inicia o programa da fabrica e executa o menu principal interativo
     *
     * <p>O metodo cria a materia-prima inicial, solicita ao usuario o cenario de operacao, cria o gerenciador da fabrica e mantem a interface em execucao ate que o usuario escolha sair</p>
     *
     * @param args argumentos fornecidos pela linha de comando
     */
    public static void main(String[] args){

        Scanner entrada = new Scanner(System.in);
        
        exibirBoasVindas();

        //Criando a materia prima, por enquanto temos so 1
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
                case 7: menuManutencao(entrada, fabrica); break;
                case 8: menuCasaDeMaquinas(entrada, fabrica); break;
                case 0:
                    rodando_programa = false;
                    System.out.println("Os confeiteiros e a fabrica precisam descansar...");
                    break;
                default:
                    System.out.println("Entrada Invalida");
            }
        }
        entrada.close();
    }

    /**
     * Exibe a mensagem inicial e as informacoes de apresentacao da fabrica
     */
    private static void exibirBoasVindas(){
        System.out.println(LINHA_DUPLA);
        System.out.println("BEM-VINDO A FANTASTICA FABRICA DE CHOCOLATE");
        System.out.println(LINHA_DUPLA);

        System.out.println("Sejam muito bem-vindos a nossa fabrica de chocolates!");
        System.out.println("Uma coisa eh mais do que certa, a vida nunca foi tao doce!");
        System.out.println("Produzimos ovos artesanais, bombons e guarda-chuvas (de chocolate)!");
        System.out.println("Confeiteiros: Alex Lei e Luis Felipe Tagawa Shimamoto");
        System.out.println(LINHA_DUPLA);
    }

    /**
     * Solicita ao usuario a escolha do cenario operacional da fabrica
     *
     * <p>A leitura eh repetida ate que uma opcao valida seja informada</p>
     *
     * @param entrada scanner utilizado para ler a entrada do usuario
     * @return cenario escolhido
     */
    private static Cenario escolherCenario(Scanner entrada){
        System.out.println("ESCOLHA O CENARIO DA OPERACaO");
        System.out.println("1 - Ideal (budget farto, poucas falhas, desgaste reduzido)");
        System.out.println("2 - Apocaliptico (budget limitado, mais falhas, desgaste acelerado)");
        System.out.print("Escolha: ");

        while(true){
            int opcao = lerInteiro(entrada);
            try{
                if(opcao == 1) return Cenario.IDEAL;
                if(opcao == 2) return Cenario.APOCALIPTICO;
                
                throw new OpcaoInvalidaException("Nao existe essa opcao. Escolha entre 1 ou 2.");

            } catch (OpcaoInvalidaException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            } catch (Exception e){
                System.out.println("[ERRO] A entrada deve ser um numero.");
            }
        }
    }

    /**
     * Exibe o cabecalho da interface, incluindo a estrategia atual, o cenario ativo e o orcamento disponivel
     *
     * @param fabrica gerenciador da fabrica
     * @param cenario cenario atualmente selecionado
     */
    private static void exibirCabecalho(GerenciadorProducao fabrica, Cenario cenario){
        System.out.println();
        System.out.println(LINHA_DUPLA);
        System.out.println("FANTASTICA FABRICA DE CHOCOLATE");
        System.out.println("Estrategia atual: " + fabrica.getEstrategia().getNomeEstrategia());
        System.out.println("Cenario ativo: " + cenario.getNome());
        System.out.println(String.format("Budget: R$ %.2f", fabrica.getBudget()));
        System.out.println(LINHA_DUPLA);
    }

    /**
     * Exibe no console as opcoes disponiveis no menu principal
     */
    private static void exibirMenuPrincipal(){
        System.out.println("1 - Demandas");
        System.out.println("2 - Fabricacao");
        System.out.println("3 - Consultar");
        System.out.println("4 - Comprar materia-prima");
        System.out.println("5 - Gerenciar estrategia");
        System.out.println("6 - Auditoria");
        System.out.println("7 - Manutencao das Maquinas");
        System.out.println("8 - Casa de Maquinas");
        System.out.println("0 - Sair");
        System.out.println(LINHA_SIMPLES);
        System.out.println("Escolha: ");
    }

    /**
     * Executa o menu de gerenciamento de demandas.
     *
     * <p>Permite adicionar ou remover unidades das demandas e consultar as demandas cadastradas</p>
     *
     * @param entrada scanner utilizado para ler as opcoes do usuario
     * @param fabrica gerenciador da fabrica
     */
    private static void menuDemandas(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[DEMANDAS]");
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Atualizar demanda de Ovos Artesanais (Alta)");
            System.out.println("2 - Atualizar demanda de Chocotone (Alta)");
            System.out.println("3 - Atualizar demanda de Bombons Sortidos (Media)");
            System.out.println("4 - Atualizar demanda de Guarda-chuvas de Chocolate (Baixa)");
            System.out.println("5 - Listar demandas");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            try {
                int opcao = lerInteiro(entrada);

                if (opcao < 0 || opcao > 5){
                    throw new OpcaoInvalidaException("Essa opcao nao existe no menu de demandas. Escolha um numero entre 0 e 5. \n");
                }

                switch(opcao){
                case 1: atualizarDemanda(entrada, fabrica, TipoProduto.OVO_ARTESANAL, "Ovo Artesanal"); break;
                case 2: atualizarDemanda(entrada, fabrica, TipoProduto.CHOCOTONE, "Chocotone"); break;
                case 3: atualizarDemanda(entrada, fabrica, TipoProduto.BOMBONS_SORTIDOS, "Bombons Sortidos"); break;
                case 4: atualizarDemanda(entrada, fabrica, TipoProduto.GUARDA_CHUVA, "Guarda-chuva de Chocolate"); break;
                case 5: fabrica.exibirDemandas(); break;
                case 0: voltar = true; break;
                }
            } catch (OpcaoInvalidaException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            } catch (Exception e){
                System.out.println("A entrada deve ser um inteiro entre 0 e 5.");
            }
        }
    }

    /**
     * Solicita ao usuario uma quantidade e atualiza a demanda de um tipo especifico de produto
     *
     * @param entrada scanner utilizado para ler a quantidade
     * @param fabrica gerenciador da fabrica
     * @param tipo tipo de produto cuja demanda sera alterada
     * @param nome_produto nome usado na mensagem apresentada ao usuario
     */
    private static void atualizarDemanda(Scanner entrada, GerenciadorProducao fabrica, TipoProduto tipo, String nome_produto){
        System.out.println("Quantas unidades de " + nome_produto + " adicionar a demanda?");
        int quantidade = lerInteiro(entrada);
        fabrica.atualizarDemanda(tipo, quantidade);
        System.out.println("[Legal!] Demanda de " + nome_produto + " atualizada");
    }

    /**
     * Executa o menu de fabricacao
     *
     * <p>Permite processar a proxima demanda utilizando a estrategia ativa ou fabricar diretamente um tipo especifico de produto</p>
     *
     * @param entrada scanner utilizado para ler as opcoes do usuario
     * @param fabrica gerenciador da fabrica
    */
    private static void menuFabricacao(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[FABRICACAO]");
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Processar proxima demanda (usa estrategia ativa: "
                + fabrica.getEstrategia().getNomeEstrategia() + ")"
            );
            System.out.println("2 - Fabricar Ovo Artesanal");
            System.out.println("3 - Fabricar Chocotone");
            System.out.println("4 - Fabricar Bombons Sortidos");
            System.out.println("5 - Fabricar Guarda-chuva de Chocolate");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            try{
                int opcao = lerInteiro(entrada);

                if (opcao < 0 || opcao > 5){
                    throw new OpcaoInvalidaException("Essa opcao nao existe no menu de fabricacao. Escolha um numero entre 0 e 5. \n");
                }

                switch(opcao){
                case 1: fabrica.executarProximaProducao(); break;
                case 2: fabrica.fabricarPorTipo(TipoProduto.OVO_ARTESANAL); break;
                case 3: fabrica.fabricarPorTipo(TipoProduto.CHOCOTONE); break;
                case 4: fabrica.fabricarPorTipo(TipoProduto.BOMBONS_SORTIDOS); break;
                case 5: fabrica.fabricarPorTipo(TipoProduto.GUARDA_CHUVA); break;
                case 0: voltar = true; break;
                }
            } catch (OpcaoInvalidaException | DemandaInvalidaException | MaquinaIndisponivelException| RecursoInsuficienteException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            } catch (Exception e){
                System.out.println("A entrada deve ser um inteiro entre 0 e 5.");
            }
        }
    }

    /**
     * Executa o menu de consulta de informacoes da fabrica
     *
     * <p>Permite consultar o armazem de produtos acabados e o estoque de materia-prima</p>
     *
     * @param entrada scanner utilizado para ler as opcoes do usuario
     * @param fabrica gerenciador da fabrica
     */
    private static void menuConsultar(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[CONSULTAR]");
            System.out.println("1 - Ver armazem (produtos acabados)");
            System.out.println("2 - Ver estoque de materia-prima");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            try{
                int opcao = lerInteiro(entrada);

                if (opcao < 0 || opcao > 2){
                    throw new OpcaoInvalidaException("Essa opcao nao existe no menu de consulta. Escolha um numero entre 0 e 2. \n");
                }

                switch(opcao){
                case 1: fabrica.exibirArmazem(); break;
                case 2: fabrica.exibirEstoqueMateriaPrima(); break;
                case 0: voltar = true; break;
                }

            } catch (OpcaoInvalidaException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            } catch (Exception e){
                System.out.println("A entrada deve ser um inteiro entre 0 e 2.");
            }
        }
    }

    /**
     * Executa o fluxo de compra de materia-prima pela interface de console
     *
     * @param entrada scanner utilizado para ler a quantidade
     * @param fabrica gerenciador da fabrica
     */
    private static void menuComprarMateriaPrima(Scanner entrada, GerenciadorProducao fabrica){
        System.out.println();
        System.out.println("[COMPRAR MATERIA-PRIMA]");
        System.out.println(LINHA_SIMPLES);
        System.out.println("Quantos gramas de chocolate quer comprar?");
        float quantidade = lerFloat(entrada);
        try{
            fabrica.comprarMateriaPrima(quantidade);
            System.out.println("\n[OBA!] Ingredientes comprados com sucesso!");
        } catch (CompraInvalidaException e){
            System.out.println("\n[ERRO]" + e.getMessage());
        }
    }

    /**
     * Executa o menu de gerenciamento da estrategia de producao
     *
     * <p>Permite escolher entre as estrategias disponiveis e atualizar a estrategia ativa do gerenciador</p>
     *
     * @param entrada scanner utilizado para ler as opcoes do usuario
     * @param fabrica gerenciador da fabrica
     */
    private static void menuEstrategia(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[GERENCIAR ESTRATEGIA]");
            System.out.println(LINHA_SIMPLES);
            System.out.println("Estrategia atual: " + fabrica.getEstrategia().getNomeEstrategia());
            System.out.println("1 - Chocofirst-In, Chocofirst-Out (Ordem de Chegada)");
            System.out.println("2 - ChocoWonka mais Pedido (Maior demanda)");
            System.out.println("3 - Maior Producao de ChocoWonka (Maximo de Produtos)");
            System.out.println("4 - Ceia de Natal (prioriza os chocotones)");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            EstrategiaProducao nova = null;

            try{
                int opcao = lerInteiro(entrada);

                if (opcao < 0 || opcao > 4){
                    throw new OpcaoInvalidaException("Essa opcao nao existe no menu de estrategia. Escolha um numero entre 0 e 4. \n");
                }

                switch(opcao){
                case 1: nova = new EstrategiaOrdemChegada(); break;
                case 2: nova = new EstrategiaMaiorDemanda(); break;
                case 3: nova = new EstrategiaMaximoProdutos(); break;
                case 4: nova = new EstrategiaNatal(); break;
                case 0: voltar = true; break;
                }
            } catch (OpcaoInvalidaException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            } catch (Exception e){
                System.out.println("A entrada deve ser um inteiro entre 0 e 4.");
            }

            if(nova != null){
                fabrica.setEstrategia(nova);
                System.out.println("[OK] Estrategia alterada para: " + nova.getNomeEstrategia());
            }
        }
    }

    /**
     * Executa o menu de auditoria da fabrica.
     *
     * <p>Permite gerar o relatorio geral ou consultar separadamente os diagnosticos das maquinas e dos produtos</p>
     *
     * @param entrada scanner utilizado para ler as opcoes do usuario
     * @param fabrica gerenciador da fabrica
     */
    private static void menuAuditoria(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[AUDITORIA] (interface Auditavel)");
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Relatorio geral (maquinas + produtos)");
            System.out.println("2 - Detalhar maquinas");
            System.out.println("3 - Detalhar produtos");
            System.out.println("0 - Voltar");
            System.out.println("Escolha: ");

            try {
                int opcao = lerInteiro(entrada);

                if (opcao < 0 || opcao > 3){
                    throw new OpcaoInvalidaException("Essa opcao nao existe no menu de auditoria. Escolha um numero entre 0 e 3. \n");
                }

                switch(opcao){
                case 1: fabrica.gerarAuditoriaGeral(); break;
                case 2: fabrica.gerarRelatorioMaquinas(); break;
                case 3: fabrica.gerarRelatorioProdutos(); break;
                case 0: voltar = true; break;
                }

            } catch (OpcaoInvalidaException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            } catch (Exception e){
                System.out.println("A entrada deve ser um inteiro entre 0 e 3.");
            }
        }
    }

    /**
     * Executa o menu de manutencao das maquinas
     *
     * <p>Permite reparar uma maquina especifica ou realizar a manutencao geral das maquinas que necessitam de reparo</p>
     *
     * @param entrada scanner utilizado para ler as opcoes do usuario
     * @param fabrica gerenciador da fabrica
     */
    private static void menuManutencao(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[MANUTENCAO]");
            System.out.println(LINHA_SIMPLES);
            fabrica.exibirMaquinas();
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Reparar uma maquina");
            System.out.println("2 - Reparar todas que precisam de manutencao (saude <= 30%)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            try{
                int opcao = lerInteiro(entrada);

                if (opcao < 0 || opcao > 2){
                    throw new OpcaoInvalidaException("Essa opcao nao existe no menu de auditoria. Escolha um numero entre 0 e 2. \n");
                }

                switch(opcao){
                case 1:
                    System.out.print("Numero da maquina: ");
                    fabrica.realizarManutencao(lerInteiro(entrada));
                    break;
                case 2: fabrica.realizarManutencaoGeral(); break;
                case 0: voltar = true; break;
                default: System.out.println("[ERRO] Opcao invalida.");
                }
            } catch (OpcaoInvalidaException | MaquinaIndisponivelException | RecursoInsuficienteException | MaquinaInexistenteException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            } catch (Exception e){
                System.out.println("A entrada deve ser um inteiro entre 0 e 2.");
            }
        }
    }

    private static void menuCasaDeMaquinas(Scanner entrada, GerenciadorProducao fabrica){
        boolean voltar = false;

        while(!voltar){
            System.out.println();
            System.out.println("[CASA DE MAQUINAS]");
            System.out.println(LINHA_SIMPLES);
            fabrica.exibirResumoCasa();
            System.out.println(LINHA_SIMPLES);
            System.out.println("1 - Listar maquinas");
            System.out.println("2 - Auditoria estrutural detalhada (Casa > Maquina > Componentes)");
            System.out.println("3 - Custo de producao por unidade");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                int opcao = lerInteiro(entrada);

                if (opcao < 0 || opcao > 3){
                    throw new OpcaoInvalidaException("Essa opcao nao existe no menu da casa de maquinas. Escolha um numero entre 0 e 3. \n");
                }

                switch(opcao){
                case 1: fabrica.exibirMaquinas(); break;
                case 2: fabrica.gerarRelatorioMaquinas(); break;
                case 3: fabrica.exibirCustoProducao(); break;
                case 0: voltar = true; break;
                }
            } catch (OpcaoInvalidaException e){
                System.out.println("\n[ERRO]" + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Le um numero inteiro do usuario, repetindo a leitura ate obter uma entrada valida
     *
     * @param entrada scanner utilizado para realizar a leitura
     * @return valor inteiro informado pelo usuario
     */
    private static int lerInteiro(Scanner entrada){
        while(true){
            String texto = entrada.nextLine().trim();
            try{
                return Integer.parseInt(texto);
            }catch (NumberFormatException e){
                System.out.println("[ERRO] Digite apenas numeros, por gentileza: ");
            }
        }
    }

    /**
     * Le um numero decimal do usuario, repetindo a leitura ate obter uma entrada valida
     *
     * @param entrada scanner utilizado para realizar a leitura
     * @return valor decimal informado pelo usuario
     */
    private static float lerFloat(Scanner entrada){
        while(true){
            String texto = entrada.nextLine().trim();
            try{
                return Float.parseFloat(texto);
            } catch (NumberFormatException e){
                System.out.println("[ERRO] Digite apenas numeros, por gentileza: ");
            }
        }
    }
}
