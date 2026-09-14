public class MateriaPrima {
    private static int proximoId = 1;

    private int id;
    private String nome;
    private float quantidade;
    private String unidade;
    private double custoPorUnidade;


    public MateriaPrima(String nome, float quantidade, String unidade, double custoPorUnidade){
        this.id = proximoId++;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.custoPorUnidade = custoPorUnidade;
    }

    public void consumir(float quant){
        quantidade -= quant;
    }

    public void adicionarEstoque(float quant){
        quantidade += quant;
    }

    public boolean verificarDisponibilidade(float quant){
        return quant <= quantidade;
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

    public double getCustoPorUnidade(){
        return custoPorUnidade;
    }
}
