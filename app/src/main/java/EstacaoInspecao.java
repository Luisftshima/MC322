import excecoes.MaquinaIndisponivelException;

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
     * @param qtdBracos a quantidade de bracos da maquina
     * @param temperaturaAlvo a temperatura em que a maquina deve entrar em contato com o produto
     * @param cenario cenario operacional associado a maquina
     */
    public EstacaoInspecao(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, saude, 2, 15,cenario);
    }

    /**
     * Inspeciona um produto e determina se ele foi aprovado na etapa de qualidade
     *
     * <p>Primeiramente, verifica uma possivel falha do sensor e deteriora a maquina. Caso o sensor falhe, o produto eh rejeitado. Caso contrario, o produto eh rejeitado com base em sua probabilidade acumulada de falha ou aprovado na inspecao</p>
     *
     * @param produto produto que sera inspecionado
     * @throws MaquinaIndisponivelException avisa se a maquina esta inoperante/indisponivel
     * @return se o produto for aprovado
     */
    @Override 
    public boolean processar(Produto produto){
        if (!this.podeOperar()){
            throw new MaquinaIndisponivelException("Maquina " + this.getNome() + " invalida");
        }

        // eh a unica maquina que pode falhar por conta propria
        boolean falhouSensor = verificarFalha();
        aplicarEfeitosDasPartes(produto);
        this.deteriorarMaquina();

        if(falhouSensor){
            produto.setStatus("Inspecao com defeito no sensor");
            return false;
        }

        if (RANDOM.nextDouble() < produto.getProbabilidadeFalha()){
            produto.setStatus("Rejeitado na Inspecao.");
            produto.resetarProbabilidadeFalha();
            return false;
        }

        produto.setStatus("Aprovado!");
        return true;
    }

    /**
     * Retorna o tipo da maquina.
     *
     * @return "Inspecao"
     */
    @Override public String getTipo(){
        return "Inspecao";
    }
}
