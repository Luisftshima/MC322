/**
 * Representa a estacao responsavel pela inspecao de qualidade dos produtos
 *
 * <p>Durante a inspecao, a estacao pode sofrer uma falha no sensor ou rejeitar o produto com base na probabilidade acumulada de falha</p>
 */
public class EstacaoInspecao extends Maquina{
    /**
     * Cria uma nova estacao de inspecao
     *
     * @param nome nome da maquina
     * @param capacidadeMaxima capacidade maxima de processamento da maquina
     * @param probabilidadeFalha probabilidade-base de falha do sensor
     * @param custoOperacao custo de operacao da maquina por unidade produzida
     * @param saude saude inicial da maquina
     * @param cenario cenario operacional associado a maquina
     */
    public EstacaoInspecao(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, saude, cenario);
    }

    /**
     * Inspeciona um produto e determina se ele foi aprovado na etapa de qualidade
     *
     * <p>Primeiramente, verifica uma possivel falha do sensor e deteriora a maquina. Caso o sensor falhe, o produto eh rejeitado. Caso contrario, o produto eh rejeitado com base em sua probabilidade acumulada de falha ou aprovado na inspecao</p>
     *
     * @param produto produto que será inspecionado
     * @return se o produto for aprovado
     */
    @Override 
    public boolean processar(Produto produto){
        if (!this.podeOperar()){
            System.out.println("Maquina " + this.getNome() + "inválida");
            return false;
        }

        // é a única máquina que pode falhar por conta própria
        boolean falhouSensor = verificarFalha();
        this.deteriorarMaquina();

        if(falhouSensor){
            produto.setStatus("Inspeção com defeito no sensor");
            return false;
        }

        if (RANDOM.nextDouble() < produto.getProbabilidadeFalha()){
            produto.setStatus("Rejeitado na Inspeção.");
            produto.resetarProbabilidadeFalha();
            return false;
        }

        produto.setStatus("Aprovado!");
        return true;
    }

    /**
     * Retorna o tipo da máquina.
     *
     * @return "Inspecao"
     */
    @Override public String getTipo(){
        return "Inspeção";
    }
}
