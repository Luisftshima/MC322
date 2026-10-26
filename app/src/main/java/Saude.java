/**
 * Controla a saude de cada componente: valor inicial, maximo, desgaste e reparo
 * 
 * A saude de um componente nao eh compartilhado com outros componentes
 */

public class Saude {
    private int atual;
    private final int maxima;

    /**
     * Cria uma saude cheia e com a atual igual a maxima
     * 
     * @param maxima saude maxima do componente
     * @throws IllegalArgumentException se a saude maxima nao for positiva
     */
    public Saude(int maxima){
        if(maxima <= 0){
            throw new IllegalArgumentException("A saude maxima precisa ser positiva");
        }
        this.maxima = maxima;
        this.atual = maxima;
    }

    /**
     * 
     * @param desgaste quantidade positiva a ser desgastada
     * @throws IllegalArgumentException se o desgaste for negativo
     * 
     */
    public void deteriorar (int desgaste){
        if (desgaste < 0){
            throw new IllegalArgumentException("Desgaste nao pode ser um numero negativo");
        }

        atual = Math.max(0, atual - desgaste);
    }

    /**
     * Restaura a saude maxima
     */
    public void reparar(){
        this.atual = this.maxima;
    }

    /**
     * Verifica se a saude chego a zero
     * @return true se a saude esta no zero
     */
    public boolean estaEsgotada(){
        return atual <= 0;
    }

    /**
     * 
     * @return saude atual
     */
    public int getAtual(){
        return atual;
    }

    /**
     * @return saude maxima
     */
    public int getMaxima(){
        return maxima;
    }
}
