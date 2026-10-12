/**
 * Representa uma materia-prima armazenada no estoque da fabrica
 *
 * <p>Armazena informacoes de identificacao, quantidade disponivel, unidade de medida e custo por unidade</p>
 */
public class MateriaPrima {
    private static int proximoId = 1;
    /**
     * Identificador utilizado para diferenciar materias-primas
     */
    private int id;
    /**
     * Nome da materia-prima
     */
    private String nome;
    /**
     * Quantidade atualmente disponivel em estoque
     */
    private float quantidade;
    /**
     * Unidade de medida da quantidade armazenada
     */
    private String unidade;
    /**
     * Custo de uma unidade da materia-prima
     */
    private double custoPorUnidade;

    /**
     * Cria uma materia-prima com os dados informados
     *
     * @param nome nome da materia-prima
     * @param quantidade quantidade inicial em estoque
     * @param unidade unidade de medida da materia-prima
     * @param custoPorUnidade custo por unidade da materia-prima
     */
    public MateriaPrima(String nome, float quantidade, String unidade, double custoPorUnidade){
        this.id = proximoId++;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.custoPorUnidade = custoPorUnidade;
    }

    /**
     * Reduz a quantidade disponivel em estoque
     *
     * @param quant quantidade que sera consumida
     */
    public void consumir(float quant){
        quantidade -= quant;
    }

    /**
     * Adiciona uma quantidade de materia-prima ao estoque
     *
     * @param quant quantidade a ser adicionada
     */
    public void adicionarEstoque(float quant){
        quantidade += quant;
    }

    /**
     * Verifica se o estoque possui pelo menos a quantidade solicitada
     *
     * @param quant quantidade necessaria
     * @return se houver quantidade suficiente
     */
    public boolean verificarDisponibilidade(float quant){
        return quant <= quantidade;
    }

    /**
     * Retorna o identificador da materia-prima.
     *
     * @return identificador da materia-prima
     */
    public int getId(){
        return id;
    }

    /**
     * Retorna o nome da materia-prima
     *
     * @return nome da materia-prima
     */
    public String getNome(){
        return nome;
    }

    /**
     * Retorna a quantidade atualmente disponivel
     *
     * @return quantidade em estoque
     */
    public float getQuantidade(){
        return quantidade;
    }

    /**
     * Retorna a unidade de medida utilizada
     *
     * @return unidade da materia-prima
     */
    public String getUnidade(){
        return unidade;
    }

    /**
     * Retorna o custo por unidade da materia-prima
     *
     * @return custo por unidade
     */
    public double getCustoPorUnidade(){
        return custoPorUnidade;
    }
}
