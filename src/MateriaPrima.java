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

    public void consumir(float quant){
        quantidade -= quant;
    }

    public void adicionarEstoque(float quant){
        quantidade += quant;
    }

    public boolean verificarDisponibilidade(float quant){
        if (quant <= quantidade && quant >= quantidadeMinima ) {
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

    public String getUnidade(){
        return unidade;
    }
}
