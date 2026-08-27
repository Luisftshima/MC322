public class MateriaPrima {
    private static int proximoId = 1;
    private int id;
    private String nome;
    private float quantidade;
    private String unidade;
    private float quantidadeMinima;

    public MateriaPrima(String n, float quant,
        String un, float min){
        id = proximoId++;
        nome = n;
        quantidade = quant;
        unidade = un;
        quantidadeMinima = min;
    }

    public void consumir(int quant){
        quantidade -= quant;
    }

    public void adicionarEstoque(int quant){
        quantidade -= quant;
    }

    public boolean verificarDisponibilidade(int quant){
        if (quant <= quantidade) {
            return true;
        }
        return false;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public float getQuantidade(){
        return quantidade;
    }
}
