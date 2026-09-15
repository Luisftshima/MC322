import java.util.Random; //para depois usar a aleatoriedade

public abstract class Produto {
    private static int proximoId = 1;
    private static int totalProdutosFabricados = 0;

    private int id;
    private String nome;
    private String status;
    private float quantidadeMateriaPrimaPorUnidade;
    private double qualidade; //de 0.0 a 1.0
    private double probablidadeFalhaAcumulada = 0.0; //aumenta a cada maquina que passa
    

    public Produto(String nome, float quantidadeMateriaPrimaPorUnidade, double qualidade){
        this.id = proximoId++;
        this.nome = nome;
        this.quantidadeMateriaPrimaPorUnidade= quantidadeMateriaPrimaPorUnidade;
        this.status = "Aguardando processamento...";
        this.qualidade = qualidade;
        totalProdutosFabricados++;
    }

    public abstract void processar();
    public abstract double calcularTempoProducao();
    public abstract String getTipo();

    public void AumentarProbabilidadeFalha(double chance){
        Random rand = new Random();
        if(rand.nextDouble() < chance){
            this.probablidadeFalhaAcumulada += chance; //incrementa a chance de falha
        }
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

    public void setStatus(String status){
        this.status = status;
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

}
