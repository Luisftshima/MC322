public abstract class Produto {
    private static int proximoId = 1;
    private int id;
    private String nome;
    private String status;
    private float quantidadeMateriaPrimaPorUnidade;
    private MateriaPrima materiaPrima;
    private int estoque = 0;
    private float qualidade;
    private float probablidadeFalhaAcumulada;
    private static totalProdutosFabricados;

    public Produto(String n, float quantidade, MateriaPrima mP){
        id = proximoId++;
        nome = n;
        quantidadeMateriaPrimaPorUnidade= quantidade;
        materiaPrima = mP;
    }
    /* 
    public void processar(){
        status = "Processado";
    }*/

    abstract void processar();
    abstract void calcularTempoProducao();
    abstract void getTipo();

    public void definirDemandaMateriaPrima(float quantidade){
        quantidadeMateriaPrimaPorUnidade = quantidade;
    }

    public float getDemandaMateriaPrima(){
        return quantidadeMateriaPrimaPorUnidade;
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

    public float getMateriaPrimaPorUnidade(){
        return quantidadeMateriaPrimaPorUnidade;
    }

    public void AumentarProbabilidadeFalha(){
        probablidadeFalhaAcumulada += rand
    }

    public float getQualidade(){
        return qualidade;
    }
    public int getEstoque(){
        return estoque;
    }

    public void adicionarEstoque(int quant){
        estoque += quant;
    }

}
