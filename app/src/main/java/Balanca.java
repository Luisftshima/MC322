/**
 * Componente da maquina responsavel por pesar os ingredientes e o produto final
 * 
 * Se quebrar, o produto sai fora de peso e com proporcoes de ingredientes erradas
 */
public class Balanca {
    private final Saude saude;
    private final double probabilidadeFalha;
    private final double precisao;

    public Balanca(int saudeMaxima, double probabilidadeFalha, double precisao){
        this.saude = new Saude(saudeMaxima); //Composicao
        this.probabilidadeFalha = probabilidadeFalha;
        this.precisao = precisao;
    }

    /**
     * Desgaste a ser feito na balanca
     * @param desgaste numero que sera desgastado
     */
    public void deteriorar(int desgaste){
        saude.deteriorar(desgaste);
    }

    /**
     * Repara a balanca
     */
    public void reparar(){
        saude.reparar();
    }

    /**
     * Ve se a balanca eesta quebrada
     * @return true se a balanca esta quebrada
     */
    public boolean estaQuebrada(){
        return saude.estaEsgotada();
    }

    /**
     * Saude atual da balanca
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
     * Verifica se a balanca vai falhar ou se ja falhou
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
     * A medida da balanca depende da deterioracao dela e da precisao
     * @param quantidade
     * @return quantidade ja com o erro
     */
    public float medir(float quantidade){
        double porcentagemSaude = (double) getSaudeAtual() / getSaudeMaxima();
        double erro = ((1 - precisao) + (1 - porcentagemSaude)) / 2;
        erro = Math.min(1.0, erro);

        if (erro == 0){
            return quantidade;
        }
        return (float) (quantidade * (1 + Maquina.RANDOM.nextDouble(-erro, erro)));
    }
}
