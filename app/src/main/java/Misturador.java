import excecoes.MaquinaInoperanteException;

/**
 * Representa uma maquina responsavel pela etapa de mistura e processamento inicial dos ingredientes
 */
public class Misturador extends Maquina{
    /**
     * Cria um novo misturador
     *
     * @param nome nome da maquina
     * @param capacidadeMaxima capacidade maxima de processamento
     * @param probabilidadeFalha probabilidade-base de falha
     * @param custoOperacao custo de operacao por unidade processada
     * @param saude saude inicial da maquina
     * @param cenario cenario operacional associado a maquina
     */
    public Misturador(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        super(nome, capacidadeMaxima, probabilidadeFalha, custoOperacao, saude, cenario);
    }

    /**
     * Processa um produto na etapa de mistura
     *
     * <p>Se a maquina puder operar, verifica uma possivel falha, processa o produto, atualiza seu status e deteriora sua saude</p>
     *
     * @param produto produto que sera processado
     * @throws MaquinaInoperanteException avisa se a maquina esta com defeito
     * @return se o processamento for executado ou se a maquina nao puder operar
     */
    @Override public boolean processar(Produto produto){
        if (!this.podeOperar()){
            throw new MaquinaInoperanteException("Maquina " + this.getNome() + " invalida");
            
        }

        if (verificarFalha()){
            produto.aumentarProbabilidadeFalha();
        }
        produto.processar();
        this.deteriorarMaquina();
        return true;
    }

    /**
     * Retorna o tipo da maquina
     *
     * @return "Misturador"
     */
    @Override public String getTipo(){
        return "Misturador";
    }
}


