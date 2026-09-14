import java.util.Scanner;
import java.lang.Math;


public class Main {
    public static void main(String[] args){

        String linha = "======================================================";
        Scanner entrada = new Scanner(System.in);
        
        System.out.println(linha);
        System.out.println("BEM-VINDO À FANTÁSTICA FÁBRICA DE CHOCOLATE");
        System.out.println(linha);

        System.out.println("Sejam muito bem-vindos a nossa fábrica de chocolates!");
        System.out.println("Uma coisa é mais do que certa, a vida nunca foi tão doce!");
        System.out.println("Produzimos ovos artesanais, bombons e guarda-chuvas (de chocolate)!");
        System.out.println("Confeiteiros: Alex Lei e Luis Felipe Tagawa Shimamoto");
        System.out.println(linha);


        //Criando a matéria prima, por enquanto temos só 1
        MateriaPrima chocolate = new MateriaPrima(
            "Chocolate", 2000, "g", 
            0.05);
        
        //passando a materia prima e a verba inicial

        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, 1000.0);


        // DA TAREFA 1
        // System.out.println(linha);
        // System.out.println("MATÉRIA PRIMA");
        // System.out.println(linha);
        // System.out.println("MATÉRIAS PRIMAS:");
        // System.out.println(String.format("%1$s: %2$.2f %3$s",
        //     matPrima1.getNome(), matPrima1.getQuantidade(), matPrima1.getUnidade()));
        
        // //Criando um produto, vamos ter ovo de páscoa e barra de chocolate  
        // Produto prod1 = new Produto("Ovo de pascoa",
        // 500, matPrima1);
        // Produto prod2 = new Produto("Barra de chocolate Wonka",
        // 300, matPrima1);
        // System.out.println(linha);
        // System.out.println("PRODUTO");
        // System.out.println(linha);
        // System.out.println("PRODUTOS DISPONÍVEIS:");
        // System.out.println(String.format("%1$d - %2$s (demanda: %4$s %3$.2f %5$s)",
        //     prod1.getId(), prod1.getNome(), prod1.getDemandaMateriaPrima(),
        //     matPrima1.getNome(), matPrima1.getUnidade()));
        // System.out.println(String.format("%1$d - %2$s (demanda: %4$s %3$.2f %5$s)",
        //     prod2.getId(), prod2.getNome(), prod2.getDemandaMateriaPrima(),
        //     matPrima1.getNome(), matPrima1.getUnidade()));
        
        // Maquina maq1 = new Maquina("0001", 1000);

        // Esteira esteira1 = new Esteira(10000);
        // Esteira esteira2 = new Esteira(10000);

        // EstacaoInspecao estInsp1 = new EstacaoInspecao();
        // Scanner entrada = new Scanner(System.in);

        // menu: while (true){
        //     System.out.println(linha);
        //     System.out.println("MENU PRINCIPAL");
        //     System.out.println("Selecione uma opção:");
        //     System.out.println(linha);
        //     System.out.println("1 - Iniciar produção");
        //     System.out.println("2 - Consultar estoque");
        //     System.out.println("3 - Sair");

        //     System.out.print("Escolha: ");
        //     int leitura;
        //     leitura = entrada.nextInt();

            
        //     switch (leitura) {
        //         case 1:
        //             System.out.print("Selecione o produto (1-2): ");
        //             leitura = entrada.nextInt();

        //             Produto produto;
        //             switch (leitura){
        //                 case 1:
        //                     produto = prod1;
        //                     break;
        //                 case 2:
        //                     produto = prod2;
        //                     break;
        //                 default:
        //                     System.out.println("Produto inexistente.");
        //                     continue menu;
        //             }
        //             System.out.print(String.format("Informe a demanda de matéria-prima (%1$s): ", 
        //                 matPrima1.getUnidade()));
        //             float leitura2 = entrada.nextFloat();

        //             //Caso não há matéria prima o suficiente
        //             if(!produto.getMateriaPrima().verificarDisponibilidade(leitura2)){
        //                 System.out.println(String.format("[ERRO] Verificando disponibilidade de %1$s", produto.getMateriaPrima().getNome()));
        //             }
        //             else if(produto.getDemandaMateriaPrima()>leitura2){
        //                 System.out.println(String.format("[ERRO] Demanda de %1$.2f %2$s insuficiente", leitura2, matPrima1.getUnidade()));
        //             }

        //             //verifica se tanto a máquina como a esteira tem capacidade para a matéria prima
        //             else if(!maq1.temCapacidade(leitura2) || esteira1.verificarCapacidade() < leitura){
        //                 System.out.println(String.format("[ERRO] Demanda de %1$.2f %2$s não pode ser atendida", leitura2, matPrima1.getUnidade()));
        //             }
        //             else{
        //                 System.out.println(String.format("[OK] Verificando disponibilidade de %1$s.", produto.getMateriaPrima().getNome()));
        //                 System.out.println(String.format("[OK] Demanda de %1$.2f %2$s pode ser atendida.", leitura2, matPrima1.getUnidade()));
                        
        //                 //ligando a esteira 1
        //                 int quantidade_produzida = (int) Math.floor(leitura2 / produto.getDemandaMateriaPrima());
        //                 esteira1.ligar();
        //                 System.out.println("[VRUMM] Esteira 1 ligada!");
                        
        //                 //ligando a máquina
        //                 maq1.ligar();
        //                 System.out.println("[VRUMM] Máquina ligada!");
                        
        //                 //colocando a matéria prima na esteira e transportando até a máquina
        //                 esteira1.adicionarItem(matPrima1.getNome());
        //                 System.out.println(String.format("[OK] Matéria-prima %1$s colocada na esteira 1.", esteira1.getItemNaEsteira()));
        //                 System.out.println("[VRUMM] Matéria-prima transportada até a máquina.");
        //                 esteira1.removerItem();
        //                 esteira1.desligar();

        //                 //processando a matéria prima
        //                 maq1.processar(matPrima1, produto, quantidade_produzida);
        //                 System.out.println(String.format("[OK] Máquina processando %1$.2f %2$s de %3$s", leitura2, matPrima1.getUnidade(), produto.getMateriaPrima().getNome()));
        //                 System.out.println(String.format("[OK] Produto %1$d - %2$s criado.", produto.getId(), produto.getNome()));
        //                 maq1.desligar();
                        
        //                 //ligando a esteira 2
        //                 esteira2.ligar();
        //                 System.out.println("[VRUMM] Esteira 2 ligada!");

        //                 //levando o produto até a inspeção
        //                 esteira2.adicionarItem(produto.getNome());
        //                 System.out.println(String.format("[OK] Produto %1$s colocada na esteira 2.", esteira2.getItemNaEsteira()));
                        
        //                 //inspecionando o item
        //                 estInsp1.ativar(estInsp1);
        //                 esteira2.removerItem();
        //                 esteira2.desligar();
        //                 System.out.println("[VRUMM] Estação de inspeção ativada.");
        //                 System.out.println(String.format("[VRUMM] Produto %1$s aprovado na inspeção.", produto.getId()));
        //                 estInsp1.desativar(estInsp1);

        //                 //concluindo a produção
        //                 produto.adicionarEstoque(quantidade_produzida);
        //                 System.out.println(linha);
        //                 System.out.println("PRODUÇÃO CONCLUÍDA COM SUCESSO, APROVEITE O CHOCOLATE!");
        //                 System.out.println(linha);

        //                 System.out.println(String.format("Estoque restante de %1$s: %2$.2f %3$s", matPrima1.getNome(), matPrima1.getQuantidade(), matPrima1.getUnidade()));

        //             }
                    
        //             break;


        //         case 2:

        //             //consultar o estoque do produto ou matéria-prima
        //             System.out.println(linha);
        //             System.out.println("Informe o que você gostaria de consultar:");
        //             System.out.println("1 - Matéria Prima");
        //             System.out.println("2 - Produtos");
        //             System.out.println(linha);
        //             leitura = entrada.nextInt();
        //             switch (leitura){

        //                 //se for matéria prima
        //                 case 1:
        //                     System.out.println(linha);
        //                     System.out.println("Informe a matéria prima:");
        //                     System.out.println(String.format("%1$d - %2$s", matPrima1.getId(), matPrima1.getNome()));
        //                     System.out.println(linha);
        //                     leitura = entrada.nextInt();
        //                     switch (leitura) {
        //                         case 1:
        //                             System.out.println(String.format("O estoque é de %1$.2f %2$s", matPrima1.getQuantidade(), matPrima1.getUnidade()));
        //                             break;
        //                         default:
        //                             System.out.println("Escolha inválida!");
        //                             break;
        //                     }
        //                     break;

        //                 //se for produto
        //                 case 2:
        //                     System.out.println(linha);
        //                     System.out.println("Informe o produto:");
        //                     System.out.println(String.format("%1$d - %2$s", prod1.getId(), prod1.getNome()));
        //                     System.out.println(String.format("%1$d - %2$s", prod2.getId(), prod2.getNome()));
        //                     System.out.println(linha);
        //                     leitura = entrada.nextInt();
        //                     switch (leitura) {
        //                         case 1:
        //                             System.out.println(String.format("O estoque é de %1$d unidades", prod1.getEstoque()));
        //                             break;
        //                         case 2:
        //                             System.out.println(String.format("O estoque é de %1$d unidades", prod2.getEstoque()));
        //                             break;
        //                         default:
        //                             System.out.println("Escolha inválida!");
        //                             break;
        //                     }
        //                     break;
        //                 default:
        //                     System.out.println("Escolha inválida!");
        //                     break;
        //             }
        //             continue menu;
        //         case 3:
        //             break menu;
        //         default:
        //             break;
        //     }
        // }
        // entrada.close();

        boolean rodando_programa = true;

        while (rodando_programa){
            System.out.println();
            fabrica.exibirBudget();
            System.out.println(linha);
            System.out.println("Atualizar Demandas");
            System.out.println("1 - Atualizar demanda de Ovos Artesanais (Alta)");
            System.out.println("2 - Atualizar demanda de Bombons Sortidos (Média)");
            System.out.println("3 - Atualizar demanda de Guarda-chuvas de Chocolate (Baixa)");
            System.out.println("Fabricar");
            System.out.println("4 - Fabricar Ovo Artesanal");
            System.out.println("5 - Fabricar Bombons Sortidos");
            System.out.println("6 - Fabricar Guarda-chuva de Chocolate");
            System.out.println("Consultar");
            System.out.println("7 - Ver armazém");
            System.out.println("8 - Ver estoque de matéria-prima");
            System.out.println("9 - Ver demandas pendentes");
            System.out.println("COMPRAR MATÉRIA-PRIMA");
            System.out.println("10 - Comprar chocolate");
            System.out.println("0 - SAIR");
            System.out.println(linha);
            System.out.print("Escolha: ");

            int opcao = lerInteiro(entrada);

            switch(opcao){
                case 1:
                    atualizarDemanda(entrada, fabrica, "alta", "Ovo Artesanal");
                    break;
                case 2:
                    atualizarDemanda(entrada, fabrica, "media", "Bombons Sortidos");
                    break;
                case 3:
                    atualizarDemanda(entrada, fabrica, "baixa", "Guarda-chuva de chocolate");
                    break;
                case 4:
                    fabrica.fabricarDemanda("alta");
                    break;
                case 5:
                    fabrica.fabricarDemanda("media");
                    break;
                case 6:
                    fabrica.fabricarDemanda("baixa");
                    break;
                case 7:
                    fabrica.exibirArmazem();
                    break;
                case 8:
                    fabrica.exibirEstoqueMateriaPrima();
                    break;
                case 9:
                    fabrica.exibirDemandas();
                    break;
                case 10:
                    System.out.println("Quantos gramas de chocolate quer comprar?");
                    float quant = lerFloat(entrada);
                    fabrica.comprarMateriaPrima(quant);
                    break;
                default:
                    System.out.println("Entrada Inválida");
            }
        }
        entrada.close()
    }

    private static void atualizarDemanda(Scanner entrada, GerenciadorProducao fabrica, String tipo, String nome_produto){
        System.out.println("Quantas unidades de " + nome_produto + " adicionar a demanda?");
        int quantidade = lerInteiro(entrada);
        fabrica.atualizarDemanda(tipo, quantidade);
        System.out.println("[Legal!] Demanda de " + nome_produto + " atualizada");
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
