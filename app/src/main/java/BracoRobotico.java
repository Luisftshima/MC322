/**
 * 
 * BracoRobotico
 * 
 * Componente da maquina responsavel por mover os ingredientes/produto e manipular embalagens
 */
public class BracoRobotico {
    private final Saude saude; //Composicao
    private final double probabilidadeFalha;
    private final double velocidade;

    public BracoRobotico(int saudeMaxima, double probabilidadeFalha, double velocidade){
        if (velocidade <= 0 || velocidade > 1.0){
            throw new IllegalArgumentException("A velocidade deve ser entre 0.0 e 1.0");
        }
        if (probabilidadeFalha < 0 || probabilidadeFalha > 1.0){
            throw new IllegalArgumentException("A probabilidade deve ser entre 0.0 e 1.0");
        }
        this.saude = new Saude(saudeMaxima);
        this.probabilidadeFalha = probabilidadeFalha;
        this.velocidade = velocidade;
    }

    /**
     * Desgaste a ser feito no braco robotico
     * @param desgaste numero que sera desgastado
     */
    public void deteriorar(int desgaste){
        saude.deteriorar(desgaste);
    }

    /**
     * Repara o braco robotico
     */
    public void reparar(){
        saude.reparar();
    }

    /**
     * Ve se o braco esta quebrado
     * @return true se o braco robotico esta quebrado
     */
    public boolean estaQuebrada(){
        return saude.estaEsgotada();
    }

    /**
     * Saude atual do braco robotico
     * @return saude atual
     */
    public int getSaudeAtual(){
        return saude.getAtual();
    }

    /**
     * Retorna a saude maxima
     * @return saude maxima
     */
    public int getSaudeMaxima(){
        return saude.getMaxima();
    }

    /**
     * Retorna o estado do braco para o relatorio
     * @return Em string se precisa de manutencao, esta quebrada ou esta ok
     */
    public String getEstado(){
        if (getSaudeAtual() > 0.30 * getSaudeMaxima()){
            return "OK";
        }else if (estaQuebrada()){
            return "QUEBRADA";
        }else{
            return "MANUTENCAO";
        }
    }

    /**
     * Verifica se o braco robotico vai falhar ou se ja falhou
     * @param multiplicadorFalha 
     * @return true se falhar/falhou
     */
    public boolean falhou(double multiplicadorFalha){
        if (estaQuebrada()){
            return true;
        }

        double fatorDesgaste = (double) getSaudeMaxima() / getSaudeAtual();

        double prob = probabilidadeFalha * multiplicadorFalha * fatorDesgaste;

        return Maquina.RANDOM.nextDouble() < prob;
    }

    /**
     * o tempo de producao depende da deterioracao e da velocidade do braco
     * @param tempo tempo base da operacao
     * @return tempo ja ajustado ja com o erro
     */
    public float calcular_tempo(float tempo){
        double porcentagemSaude = (double) getSaudeAtual() / getSaudeMaxima();
        double erro = ((1 - velocidade) + (1 - porcentagemSaude)) / 2;
        erro = Math.min(1.0, erro);

        return (float) (tempo * (1 + erro));
    }
}
