/**
 * Representa os cenarios disponiveis para a operacao da fabrica
 *
 * <p>Cada cenario define o orcamento inicial, o multiplicador aplicado a probabilidade de falha das maquinas e a faixa de desgaste das maquinas</p>
 */
public enum Cenario {
    /**
     * Cenario com orcamento inicial elevado, menor probabilidade de falha e baixo desgaste das maquinas
     */
    IDEAL("Ideal", 10000.0, 0.5, 0, 1),
    /**
     * Cenario com orcamento inicial limitado, maior probabilidade de falha e desgaste acelerado das maquinas
     */
    APOCALIPTICO("Apocaliptico", 1200.0, 2.0, 2, 5);

    /**
     * Nome exibido para o cenario
     */
    private final String nome;
    /**
     * Orcamento inicial disponivel para a fabrica
     */
    private final double orcamentoInicial;
    /**
     * Multiplicador aplicado as possibilidades de falha
     */
    private final double multiplicadorFalha;
    /**
     * Menor valor possivel de desgaste de operacao
     */
    private final int desgasteMinimo;
    /**
     * Maior valor possivel de desgaste de operacao
     */
    private final int desgasteMaximo;
    /**
     * Cria um cenario com seus parametros operacionais
     *
     * @param nome nome exibido para o cenario
     * @param orcamentoInicial orcamento inicial disponivel para a fabrica
     * @param multiplicadorFalha multiplicador aplicado as probabilidades de falha
     * @param desgasteMinimo menor valor possivel de desgaste por operacao
     * @param desgasteMaximo maior valor possivel de desgaste por operacao
     */
    Cenario(String nome, double orcamentoInicial, double multiplicadorFalha,
            int desgasteMinimo, int desgasteMaximo) {
        this.nome = nome;
        this.orcamentoInicial = orcamentoInicial;
        this.multiplicadorFalha = multiplicadorFalha;
        this.desgasteMinimo = desgasteMinimo;
        this.desgasteMaximo = desgasteMaximo;
    }
    /**
    * Retorna o nome do cenario
    *
    * @return nome do cenario
    */
    public String getNome() {
        return nome;
    }
    /**
     * Retorna o orcamento inicial definido para o cenario
     *
     * @return orcamento inicial
     */
    public double getOrcamentoInicial() {
        return orcamentoInicial;
    }
    /**
     * Retorna o multiplicador utilizado no calculo da probabilidade de falha das maquinas
     *
     * @return multiplicador de probabilidade de falha
     */
    public double getMultiplicadorFalha() {
        return multiplicadorFalha;
    }
    /**
     * Retorna o menor desgaste possivel de uma maquina em uma operacao
     *
     * @return desgaste minimo por operacao
     */
    public int getDesgasteMinimo() {
        return desgasteMinimo;
    }
    /**
     * Retorna o maior desgaste possivel de uma maquina em uma operacao
     *
     * @return desgaste maximo por operacao
     */
    public int getDesgasteMaximo() {
        return desgasteMaximo;
    }
}
