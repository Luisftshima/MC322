public class Produto {
    private static int proximoId = 1;
    private int id;
    private String nome;
    private String status;
    private float quantidadeMateriaPrimaNecessaria;

    public Produto(String n, float quantidade){
        id = proximoId++;
        nome = n;
        quantidadeMateriaPrimaNecessaria = quantidade;
    }

    public void processar(){
        status = "Processado"
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
}
