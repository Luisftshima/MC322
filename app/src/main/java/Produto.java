/**
 * Classe base para os produtos fabricados pela fabrica
 *
 * <p>Armazena os dados comuns dos produtos, como identificacao, nome, status, consumo de materia-prima, qualidade, probabilidade acumulada de falha e lote de producao. Tambem implementa o contrato Auditavel</p>
 */
public abstract class Produto implements Auditavel{
    /**
     * Proximo identificador disponivel para um produto
     */
    private static int proximoId = 1;
    /**
     * Quantidade total de produtos criados
     */
    private static int totalProdutosFabricados = 0;
    
    /**
     * Identificador do produto
     */
    private int id;
    /**
     * Nome do produto
     */
    private String nome;
    /**
     * Status textual atual do produto
     */
    private String status;
    /**
     * Quantidade de materia-prima necessaria para produzir uma unidade
     */
    private float quantidadeMateriaPrimaPorUnidade;
    /**
     * Indice de qualidade do produto, variando de 0.0 a 1.0
     */
    private double qualidade; //de 0.0 a 1.0
    /**
     * Probabilidade acumulada de falha do produto ao longo da linha de producao
     */
    private double probabilidadeFalhaAcumulada = 0.0; //chance de aumentar a cada maquina que passa
    /**
     * Numero do lote ao qual o produto pertence
     *
     * <p>O valor -1 indica que o produto ainda nao possui lote</p>
     */
    private int lote = -1; //nao tem nenhum lote no comeco
  
    /**
     * Inicializa um produto com os parametros informados
     *
     * @param nome nome do produto
     * @param quantidadeMateriaPrimaPorUnidade quantidade de materia-prima necessaria para uma unidade
     * @param qualidade indice de qualidade do produto, entre 0.0 e 1.0
     */
    public Produto(String nome, float quantidadeMateriaPrimaPorUnidade, double qualidade){
        this.id = proximoId++;
        this.nome = nome;
        this.quantidadeMateriaPrimaPorUnidade = quantidadeMateriaPrimaPorUnidade;
        this.status = "Aguardando processamento...";
        this.qualidade = qualidade;
        totalProdutosFabricados++;
    }

    /**
     * Executa o processamento especifico do produto
     *
     * <p>Cada subclasse deve definir como o status do produto eh alterado durante sua etapa de processamento</p>
     */
    public abstract void processar();
    /**
     * Retorna o tempo de producao especifico do produto
     *
     * @return tempo necessario para produzir o produto
     */
    public abstract double calcularTempoProducao();
    /**
     * Retorna o nivel de qualidade do produto
     *
     * @return nivel de qualidade do produto
     */
    public abstract NivelQualidade getTipo();

    /**
     * Aumenta a probabilidade acumulada de falha do produto.
     */
    public void aumentarProbabilidadeFalha(){
        probabilidadeFalhaAcumulada += (1 - qualidade) * 0.1;
    }

    /**
     * Retorna a probabilidade acumulada de falha do produto
     *
     * @return probabilidade acumulada, representada como valor entre 0 e 1 conforme os incrementos realizados
     */
    public double getProbabilidadeFalha(){
        return probabilidadeFalhaAcumulada;
    }

    /**
     * Zera a probabilidade acumulada de falha do produto
     */
    public void resetarProbabilidadeFalha(){
        probabilidadeFalhaAcumulada = 0.0;
    }

    /**
     * Retorna o identificador unico do produto
     *
     * @return identificador do produto
     */
    public int getId(){
        return id;
    }

    /**
     * Retorna o nome do produto
     *
     * @return nome do produto
     */
    public String getNome(){
        return nome;
    }

    /**
     * Retorna o status textual atual do produto
     *
     * @return status atual
     */
    public String getStatus(){
        return status;
    }

    /**
     * Atualiza o status textual do produto
     *
     * @param status novo status do produto
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * Retorna a quantidade de materia-prima necessaria para produzir uma unidade do produto
     *
     * @return consumo de materia-prima por unidade
     */
    public float getMateriaPrimaPorUnidade(){
        return quantidadeMateriaPrimaPorUnidade;
    }

    /**
     * Retorna o indice de qualidade do produto
     *
     * @return indice de qualidade, normalmente entre 0.0 e 1.0
     */
    public double getQualidade(){
        return qualidade;
    }

    /**
     * Retorna o numero total de produtos criados
     *
     * @return quantidade total de produtos criados
     */
    public static int getTotalProdutosFabricados(){
        return totalProdutosFabricados;
    }

    /**
     * Cria uma instancia do produto correspondente ao tipo informado
     *
     * @param tipo tipo de produto a ser criado
     * @return nova instancia do produto correspondente ou IllegalStateException se nenhum caso do tipo informado for reconhecido\
     * @throws IllegalStateException 
     */
    public static Produto criarPorTipo(TipoProduto tipo){
        switch (tipo){
            case OVO_ARTESANAL: return new OvoArtesanal();
            case CHOCOTONE: return new Chocotone();
            case BOMBONS_SORTIDOS: return new BombonsSortidos();
            case GUARDA_CHUVA: return new GuardaChuva();
            default: throw new IllegalStateException("Nao existe fabrica de chocoWonka para o tipo: " + tipo);
        }
    }

    /**
     * Gera um relatorio textual com as principais informacoes de diagnostico do produto, incluindo lote, tipo, qualidade, probabilidade acumulada de falha, status e necessidade de manutencao
     *
     * @return relatorio de diagnostico do produto
     */
    public String gerarRelatorioDiagnostico() {
        return String.format("ID: %d | Lote: #%d | Produto: %s | Tipo: %s | Qualidade: %.0f | Falha acumulada: %.2f%% | Status: %s | Manutencao necessaria: %s",
            id, lote, nome, getTipo(), qualidade, probabilidadeFalhaAcumulada * 100, status, precisaManutencao() ? "SIM" : "NaO");
    }

    /**
     * Verifica se o produto apresenta uma probabilidade acumulada de falha superior a 0.5
     *
     * @return se a probabilidade acumulada de falha for maior que 0.5
     */
    public boolean precisaManutencao(){
        return this.probabilidadeFalhaAcumulada > 0.5;
    }
    
    /**
     * Define o lote ao qual o produto pertence
     *
     * @param lote numero do lote
     */
    public void setLote(int lote){
        this.lote = lote;
    }

    /**
     * Retorna o numero do lote do produto
     *
     * @return numero do lote ou -1 caso nenhum lote tenha sido definido
     */
    public int getLote(){
        return lote;
    }
}
