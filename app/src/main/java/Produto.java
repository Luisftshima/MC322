

public abstract class Produto implements Auditavel{
    private static int proximoId = 1;
    private static int totalProdutosFabricados = 0;
    
    private int id;
    private String nome;
    private String status;
    private float quantidadeMateriaPrimaPorUnidade;
    private double qualidade; //de 0.0 a 1.0
    private double probabilidadeFalhaAcumulada = 0.0; //chance de aumentar a cada maquina que passa
    private int lote = -1; //nao tem nenhum lote no comeco
    

    public Produto(String nome, float quantidadeMateriaPrimaPorUnidade, double qualidade){
        this.id = proximoId++;
        this.nome = nome;
        this.quantidadeMateriaPrimaPorUnidade = quantidadeMateriaPrimaPorUnidade;
        this.status = "Aguardando processamento...";
        this.qualidade = qualidade;
        totalProdutosFabricados++;
    }

    public abstract void processar();
    public abstract double calcularTempoProducao();
    public abstract NivelQualidade getTipo();

    public void aumentarProbabilidadeFalha(){
        probabilidadeFalhaAcumulada += (1 - qualidade) * 0.1;
    }

    public double getProbabilidadeFalha(){
        return probabilidadeFalhaAcumulada;
    }

    public void resetarProbabilidadeFalha(){
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
    public static int getTotalProdutosFabricados(){
        return totalProdutosFabricados;
    }

    public static Produto criarPorTipo(TipoProduto tipo){
        switch (tipo){
            case OVO_ARTESANAL: return new OvoArtesanal();
            case CHOCOTONE: return new Chocotone();
            case BOMBONS_SORTIDOS: return new BombonsSortidos();
            case GUARDA_CHUVA: return new GuardaChuva();
            default: return null;
        }
    }

    public String gerarRelatorioDiagnostico() {
        return String.format("ID: %d | Lote: #%d | Produto: %s | Tipo: %s | Qualidade: %.0f | Falha acumulada: %.2f%% | Status: %s | Manutenção necessária: %s",
            id, lote, nome, getTipo(), qualidade, probabilidadeFalhaAcumulada * 100, status, precisaManutencao() ? "SIM" : "NÃO");
}

    public boolean precisaManutencao(){
        return this.probabilidadeFalhaAcumulada > 0.5;
    }
    
    public void setLote(int lote){
        this.lote = lote;
    }

    public int getLote(){
        return lote;
    }
}
