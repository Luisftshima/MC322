import java.util.Random; //para depois usar a aleatoriedade

public abstract class Produto {
    private static int proximoId = 1;
    private static int totalProdutosFabricados = 0;

    private int id;
    private String nome;
    private String status;
    private float quantidadeMateriaPrimaPorUnidade;
    private MateriaPrima materiaPrima;
    private double qualidade; //de 0.0 a 1.0
    private double probablidadeFalhaAcumulada = 0.0;
    

    public Produto(String n, float quantidade, MateriaPrima mP){
        this.id = proximoId++;
        this.nome = n;
        this.quantidadeMateriaPrimaPorUnidade= quantidade;
        materiaPrima = mP;
        totalProdutosFabricados++;
    }

    public abstract void processar();
    public abstract double calcularTempoProducao();
    public abstract String getTipo();

    public void definirDemandaMateriaPrima(float quantidade){
        quantidadeMateriaPrimaPorUnidade = quantidade;
    }

    public void AumentarProbabilidadeFalha(double chance){
        Random rand = new Random();
        if(rand.nextDouble() < chance){
            this.probablidadeFalhaAcumulada += 0.15; //incrementa a chance de falha
        }
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

    public double getQualidade(){
        return qualidade;
    }
    public int getTotalProdutosFabricados(){
        return totalProdutosFabricados;
    }

    public void adicionarEstoque(int quant){
        estoque += quant;
    }

}
