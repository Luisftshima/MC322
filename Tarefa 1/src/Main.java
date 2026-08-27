import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        MateriaPrima matPrima1 = new MateriaPrima(
            "Chocolate", 2000, "g", 
            0);

        Produto prod1 = new Produto("Ovo de pascoa",
        500);
        Produto prod2 = new Produto("Barra de chocolate",
        300);

        Maquina maq1 = new Maquina("0001", 1000);

        Esteira esteira1 = new Esteira(10000);

        EstacaoInspecao estInsp1 = new EstacaoInspecao();
        
        int leitura;
        Scanner entrada = new Scanner(System.in);

        System.out.println("1 - " + prod1.getNome() + " (demanda:"
            +prod2.getDemandaMateriaPrima() + ")");
        System.out.println("2 - " + prod2.getNome() + " (demanda:"
            +prod1.getDemandaMateriaPrima() + ")");
        
        leitura = entrada.nextInt();

    }


}
