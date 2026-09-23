public enum Cenario {
    IDEAL("Ideal", 10000.0, 0.5, 0, 1),
    APOCALIPTICO("Apocalíptico", 1200.0, 2.0, 2, 5);

    private final String nome;
    private final double orcamentoInicial;
    private final double multiplicadorFalha;
    private final int desgasteMinimo;
    private final int desgasteMaximo;

    Cenario(String nome, double orcamentoInicial, double multiplicadorFalha,
            int desgasteMinimo, int desgasteMaximo) {
        this.nome = nome;
        this.orcamentoInicial = orcamentoInicial;
        this.multiplicadorFalha = multiplicadorFalha;
        this.desgasteMinimo = desgasteMinimo;
        this.desgasteMaximo = desgasteMaximo;
    }

    public String getNome() {
        return nome;
    }

    public double getOrcamentoInicial() {
        return orcamentoInicial;
    }

    public double getMultiplicadorFalha() {
        return multiplicadorFalha;
    }

    public int getDesgasteMinimo() {
        return desgasteMinimo;
    }

    public int getDesgasteMaximo() {
        return desgasteMaximo;
    }
}
