import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("-----|BEM-VINDO À FANTÁSTICA FÁBRICA DE CHOCOLATE|-----");
        
        MateriaPrima matPrima1 = new MateriaPrima(
            "Chocolate", 2000, "g", 
            0);
        System.out.println("--------------------|MATÉRIA PRIMA|--------------------");
        System.out.println(String.format("%1$s: %2$.2f",matPrima1.getNome(), matPrima1.getQuantidade()));
        
        Produto prod1 = new Produto("Ovo de pascoa",
        500);
        Produto prod2 = new Produto("Barra de chocolate",
        300);
        System.out.println("-----------------------|PRODUTO|-----------------------");
        System.out.println(String.format("%1$d - %2$s (demanda: %3$.2f)",
            prod1.getId(), prod1.getNome(), prod1.getDemandaMateriaPrima()));
        System.out.println(String.format("%1$d - %2$s (demanda: %3$.2f)",
            prod2.getId(), prod2.getNome(), prod2.getDemandaMateriaPrima()));
        
        Maquina maq1 = new Maquina("0001", 1000);

        Esteira esteira1 = new Esteira(10000);

        EstacaoInspecao estInsp1 = new EstacaoInspecao();
        
        int leitura;
        Scanner entrada = new Scanner(System.in);
        leitura = entrada.nextInt();
    }


}
