/**
 * 
 * ReguladorTemperatura
 * 
 * Componente para regular a temperatura das maquinas
 * 
 * No misturador a temperatura aumenta mas 
 * na embaladora e esteira inspecao a temperatura diminui
 */

public class ReguladorTemperatura {
    private final Saude saude;
    private final double probabilidadeFalha;
    private final double temperaturaAlvo;

    public ReguladorTemperatura(int saudeMaxima, double probabilidadeFalha, double temperaturaAlvo){
        if(probabilidadeFalha <= 0.0 || probabilidadeFalha >= 1.0){
            throw new IllegalArgumentException("A probabilidade de falha tem que ser entre 0.0 e 1.0");
        }
        this.saude = new Saude(saudeMaxima);
        this.probabilidadeFalha = probabilidadeFalha;
        this.temperaturaAlvo = temperaturaAlvo;
    }

    /**
     * Desgaste a ser feito no regulador
     * @param desgaste numero que sera desgastado
     */
    public void deteriorar(int desgaste){
        saude.deteriorar(desgaste);
    }

    /**
     * Repara o regulador
     */
    public void reparar(){
        saude.reparar();
    }

    /**
     * Ve se o regulador esta quebrado
     * @return true se o regulador esta quebrado
     */
    public boolean estaQuebrada(){
        return saude.estaEsgotada();
    }

    /**
     * Saude atual do regulador
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
     * Verifica se o regulador vai falhar ou se ja falhou
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
     * devolve a temperatura que o regulador consegue entregar
     * @return a nova temperatura no regulador
     */
    public float regular(){
        double porcentagemSaude = (double) getSaudeAtual() / getSaudeMaxima();
        double erro = 1 - porcentagemSaude;

        if (erro == 0){
            return (float) temperaturaAlvo;
        }

        return (float) (temperaturaAlvo + (1 + Maquina.RANDOM.nextDouble(-erro, erro)));
    }
}
