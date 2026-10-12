/**
 * Representa uma maquina responsavel pela etapa de embalagem dos produtos
 *
 * <p>A maquina pode aumentar a probabilidade acumulada de falha do produto caso ocorra uma falha durante sua operacao</p>
 */
public class Embaladora extends Maquina{
    /**
     * Cria uma nova maquina embaladora
     *
     * @param nome nome da maquina
     * @param capacidadeMaxima capacidade maxima de processamento da maquina
     * @param probabilidadeFalha probabilidade-base de falha da maquina
     * @param custoOperacao custo de operacao da maquina por unidade produzida
     * @param saude saude inicial da maquina
     * @param cenario cenario operacional associado a maquina
     */
    public Embaladora(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, saude, cenario);
    }

    /**
     * Processa um produto pela etapa de embalagem
     *
     * <p>A maquina precisa estar apta a operar. Caso uma falha ocorra, a probabilidade acumulada de falha do produto eh aumentada. Ao final, a saude da maquina eh deteriorada e o produto recebe o status "Embalado"</p>
     *
     * @param produto produto que sera embalado
     * @return se o processamento pode ser realizado
     */
    @Override public boolean processar(Produto produto){
        if (!this.podeOperar()){
            System.out.println("Maquina " + this.getNome() + "inválida");
            return false;
        }

        if (verificarFalha()){
            produto.aumentarProbabilidadeFalha();
        }
        
        produto.setStatus("Embalado");
        this.deteriorarMaquina();
        return true;
    }

    /**
     * Retorna o tipo da maquina
     *
     * @return "Embalagem"
     */
    @Override public String getTipo(){
        return "Embalagem";
    }


}
