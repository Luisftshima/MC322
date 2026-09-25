public abstract class Produto implements Auditavel{
    private static int proximoId = 1;
    private static int totalProdutosFabricados = 0;

    private int id;
    private String nome;
    private String status;
    private float quantidadeMateriaPrimaPorUnidade;
    private double qualidade; //de 0.0 a 1.0
    private double probabilidadeFalhaAcumulada = 0.0; //chance de aumentar a cada maquina que passa
    

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

    public void AumentarProbabilidadeFalha(){
        probabilidadeFalhaAcumulada += qualidade * 0.1;
    }

    public double getProbabilidadeFalha(){
        return probabilidadeFalhaAcumulada;
    }

    public void ResetarProbabilidadeFalha(){
        probabilidadeFalhaAcumulada = 0.0;
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

    public String gerarRelatorioDiagnostico() {
        return String.format("ID: %d | Produto: %s | Tipo: %s | Qualidade: %.0f%% | Falha acumulada: %.2f%% | Status: %s | Manutenção necessária: %s",
            id, nome, getTipo(), qualidade * 100, probabilidadeFalhaAcumulada * 100, status, precisaManutencao() ? "SIM" : "NÃO");
}

    public boolean precisaManutencao(){
        return this.probabilidadeFalhaAcumulada > 0.5;
    }
    
}
