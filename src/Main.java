import java.util.Scanner;

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

        System.out.println("ESCOLHA O CENÁRIO DA OPERAÇÃO");
        System.out.println("1 - Ideal (budget farto, poucas falhas, desgaste reduzido)");
        System.out.println("2 - Apocalíptico (budget limitado, mais falhas, desgaste acelerado)");
        System.out.print("Escolha: ");


        Cenario cenario;
        while (true) {
            int opcao = lerInteiro(entrada);
            if (opcao == 1) {
                cenario = Cenario.IDEAL;
                break;
            }
            if (opcao == 2) {
                cenario = Cenario.APOCALIPTICO;
                break;
            }
            System.out.print("[ERRO] Escolha 1 ou 2: ");
        }

        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, cenario);
        
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
        entrada.close();
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
