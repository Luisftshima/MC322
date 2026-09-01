public class Produto {
    private static int proximoId = 1;
    private int id;
    private String nome;
    private String status;
    private float quantidadeMateriaPrimaNecessaria;
    private MateriaPrima materiaPrima;
    private int estoque = 0;

    public Produto(String n, float quantidade, MateriaPrima mP){
        id = proximoId++;
        nome = n;
        quantidadeMateriaPrimaNecessaria = quantidade;
        materiaPrima = mP;
    }

    public void processar(){
        status = "Processado";
    }

    public void definirDemandaMateriaPrima(float quantidade){
        quantidadeMateriaPrimaNecessaria = quantidade;
    }

    public float getDemandaMateriaPrima(){
        return quantidadeMateriaPrimaNecessaria;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getStatus(){
        return status;
    }

    public MateriaPrima getMateriaPrima(){
        return materiaPrima;
    }

    public int getEstoque(){
        return estoque;
    }

    public void adicionarEstoque(int quant){
        estoque += quant;
    }
}
