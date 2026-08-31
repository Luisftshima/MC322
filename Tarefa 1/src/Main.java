import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String linha = "=========================";
        
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
        500);
        Produto prod2 = new Produto("Barra de chocolate",
        300);
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

            int leitura;
            Scanner entrada = new Scanner(System.in);
            leitura = entrada.nextInt();

            switch (leitura) {
                case 1:
                    
                    break;
                case 2:
                    break;
                case 3:
                    break menu;
                default:
                    break;
            }

        }
    }

}
