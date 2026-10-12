import excecoes.RecursoInsuficienteException;
/**
 * Representa uma demanda de producao de um determinado tipo de produto
 *
 * <p>A demanda armazena o tipo de produto solicitado, a quantidade atualmente pendente e o estado de atendimento da solicitacao</p>
 */
public class Demanda {
    /**
     * Tipo de produto associado a demanda
     */
    private TipoProduto tipoProduto;
    /**
     * Quantidade de unidades atualmente associadas a demanda
     */
    private int quantidadeProdutos;
    /**
     * Estado atual da demanda
     *
     * <p>Uma demanda eh inicialmente criada como concluida e pode ser alterada para pendente, em producao ou cancelada conforme a operacao da fabrica</p>
     */
    private StatusDemanda status = StatusDemanda.CONCLUIDA; 

    /**
     * Cria uma nova demanda para um tipo de produto
     *
     * @param tipoProduto tipo de produto solicitado
     * @param quantidade quantidade inicial de unidades solicitadas
     */
    public Demanda(TipoProduto tipoProduto, int quantidade){
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidade;
    }

    /**
     * Atualiza a quantidade de produtos da demanda
     *
     * <p>O valor informado pode ser positivo ou negativo. A operacao nao permite que a quantidade resultante fique abaixo de zero</p>
     *
     * @param quantidade valor a ser somado a quantidade atual
     * @throws RecursoInsuficienteException se a quantidade resultante for negativa
     */
    public void atualizarQuantidade(int quantidade){
        //quantidade pode ser tanto um numero negativo como um positivo
        if (this.quantidadeProdutos + quantidade < 0){
            throw new RecursoInsuficienteException("Materia-prima insuficiente.");
        }
        this.quantidadeProdutos += quantidade;
    }

    /**
     * Calcula a quantidade de materia-prima necessaria para produzir toda a quantidade atualmente solicitada
     *
     * @param produto produto utilizado como referencia para obter o consumo de materia-prima por unidade
     * @return quantidade total de materia-prima necessaria
     */
    public float calcularMateriaPrimaNecessaria(Produto produto){
        return produto.getMateriaPrimaPorUnidade() * this.quantidadeProdutos;
    }

    /**
     * Calcula o custo estimado para produzir toda a demanda.
     *
     * <p>O calculo utiliza a quantidade solicitada e o custo unitario associado ao nivel de qualidade do tipo de produto</p>
     *
     * @return custo estimado da demanda
     */
    public double calcularCustoEstimado() {
        return quantidadeProdutos * tipoProduto.getNivelQualidade().getCustoUnitario();
    }

    /**
     * Verifica se a producao completa da demanda cabe no orcamento disponivel
     *
     * @param orcamentoDisponivel valor atualmente disponivel para producao
     * @return se o custo estimado eh menor ou igual ao orcamento ou nao
     */
    public boolean isViavel(double orcamentoDisponivel){
        return calcularCustoEstimado() <= orcamentoDisponivel;
    }

    /**
     * Altera o estado da demanda para em producao
     */
    public void emProducao(){
        this.status = StatusDemanda.EM_PRODUCAO;
    }

    /**
     * Altera o estado da demanda para pendente
     */
    public void pendente(){
        this.status = StatusDemanda.PENDENTE;
    }

    /**
     * Marca a demanda como concluida
     *
     * @throws IllegalStateException se a demanda estiver cancelada
     */
    public void atender(){
        if (this.status == StatusDemanda.CANCELADA){
            throw new IllegalStateException("Nao eh possivel concluir uma demanda ChocoWonka cancelada");
        }
        this.status = StatusDemanda.CONCLUIDA;
    }

    /**
     * Marca a demanda como cancelada.
     */
    public void cancelar(){
        this.status = StatusDemanda.CANCELADA;
    }

    /**
     * Retorna o tipo de produto da demanda
     *
     * @return tipo de produto solicitado
     */
    public TipoProduto getTipoProduto(){
        return tipoProduto;
    }

    /**
     * Retorna a quantidade atual de produtos da demanda
     *
     * @return quantidade de unidades ainda associadas a demanda
     */
    public int getQuantidadeProdutos(){
        return quantidadeProdutos;
    }

    /**
     * Retorna o estado atual da demanda
     *
     * @return status atual da demanda
     */
    public StatusDemanda getStatus(){
        return status;
    }
}
