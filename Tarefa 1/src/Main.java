import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String linha = "======================================================";
        
        System.out.println(linha);
        System.out.println("BEM-VINDO À FANTÁSTICA FÁBRICA DE CHOCOLATE");
        System.out.println(linha);

        MateriaPrima matPrima1 = new MateriaPrima(
            "Chocolate", 2000, "g", 
            0);
        System.out.println(linha);
        System.out.println("MATÉRIA PRIMA");
        System.out.println(linha);
        System.out.println(String.format("%1$s: %2$.2f %3$s",
            matPrima1.getNome(), matPrima1.getQuantidade(), matPrima1.getUnidade()));
        
        Produto prod1 = new Produto("Ovo de pascoa",
        500, matPrima1);
        Produto prod2 = new Produto("Barra de chocolate",
        300, matPrima1);
        System.out.println(linha);
        System.out.println("PRODUTO");
        System.out.println(linha);
        System.out.println(String.format("%1$d - %2$s (demanda: %4$s %3$.2f %5$s)",
            prod1.getId(), prod1.getNome(), prod1.getDemandaMateriaPrima(),
            matPrima1.getNome(), matPrima1.getUnidade()));
        System.out.println(String.format("%1$d - %2$s (demanda: %4$s %3$.2f %5$s)",
            prod2.getId(), prod1.getNome(), prod2.getDemandaMateriaPrima(),
            matPrima1.getNome(), matPrima1.getUnidade()));
        
        Maquina maq1 = new Maquina("0001", 1000);

        Esteira esteira1 = new Esteira(10000);

        EstacaoInspecao estInsp1 = new EstacaoInspecao();
        
        menu: while (true){
            System.out.println(linha);
            System.out.println("MENU PRINCIPAL");
            System.out.println(linha);
            System.out.println("1 - Iniciar produção");
            System.out.println("2 - Consultar estoque");
            System.out.println("3 - Sair");

            System.out.print("Escolha: ");
            int leitura;
            Scanner entrada = new Scanner(System.in);
            leitura = entrada.nextInt();

            
            switch (leitura) {
                case 1:
                    System.out.print("Selecione o produto (1-n): ");
                    entrada = new Scanner(System.in);
                    leitura = entrada.nextInt();

                    Produto produto;
                    switch (leitura){
                        case 1:
                            produto = prod1;
                            break;
                        case 2:
                            produto = prod2;
                            break;
                        default:
                            System.out.println("Produto inexistente.");
                            continue menu;
                    }
                    System.out.print(String.format("Informe a demanda de matéria-prima (%1$s): ", 
                        matPrima1.getUnidade()));
                    entrada = new Scanner(System.in);
                    float leitura2 = entrada.nextFloat();
                    if(!produto.getMateriaPrima().verificarDisponibilidade(leitura2)){
                        System.out.println(String.format("[ERRO] Verificando disponibilidade de %1$s", produto.getMateriaPrima().getNome()));
                    }
                    else if(produto.getDemandaMateriaPrima()>leitura2){
                        System.out.println(String.format("[ERRO] Demanda de %1$.2f %2$s insuficiente", leitura2, matPrima1.getUnidade()));
                    }
                    else if(!maq1.temCapacidade(leitura2) || esteira1.verificarCapacidade() < leitura){
                        System.out.println(String.format("[ERRO] Demanda de %1$.2f %2$s não pode ser atendida", leitura2, matPrima1.getUnidade()));
                    }
                    else{
                        System.out.println(String.format("[OK] Verificando disponibilidade de %1$s", produto.getMateriaPrima().getNome()));
                        System.out.println(String.format("[OK] Demanda de %1$.2f %2$s pode ser atendida", leitura2, matPrima1.getUnidade()));

                        esteira1.ligar();
                        System.out.println("[OK] Esteira ligada.");

                        maq1.ligar();
                        System.out.println("[OK] Máquina ligada");

                        esteira1.adicionarItem(matPrima1.getNome());
                        matPrima1.consumir(leitura);
                        System.out.println(String.format("[OK] Matéria-prima %1$s colocada na esteira.", matPrima1.getNome()));
                        System.out.println("[OK] Matéria-prima transportada até a máquina.");
                        esteira1.removerItem();

                        maq1.processar(matPrima1, produto);
                        System.out.println(String.format("[OK] Máquina processando %1$.2f %2$s de %3$s", leitura2, matPrima1.getUnidade(), produto.getMateriaPrima().getNome()));
                        System.out.println(String.format("[OK] Produto %1$d - %2$s criado.", produto.getId(), produto.getNome()));
                        maq1.desligar();

                        esteira1.adicionarItem(produto.getNome());
                        System.out.println(String.format("[OK] Produto %1$d transportado para a inspeção.",produto.getId()));

                        estInsp1.ativar(estInsp1);
                        esteira1.removerItem();
                        esteira1.desligar();
                        System.out.println("[OK] Estação de inspeção ativada.");
                        System.out.println(String.format("[OK] Produto %1$s aprovado na inspeção.", produto.getId()));
                        estInsp1.desativar(estInsp1);

                        System.out.println(linha);
                        System.out.println("PRODUÇÃO CONCLUÍDA COM SUCESSO, APROVEITE O CHOCOLATE!");
                        System.out.println(linha);

                        System.out.println(String.format("Estoque restante de %1$s: %2$.2f %3$s", matPrima1.getNome(), matPrima1.getQuantidade(), matPrima1.getUnidade()));

                    }
                    
                    break;
                case 2:
                    System.out.println("Informe o ");
                    entrada = new Scanner(System.in);
                    leitura = entrada.nextInt();
                    break;
                case 3:
                    break menu;
                default:
                    break;
            }
        }
    }

}
