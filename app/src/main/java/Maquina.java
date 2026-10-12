import java.util.Random;

/**
 * Classe base para todas as maquinas da linha de producao
 *
 * <p>Armazena caracteristicas comuns, como nome, capacidade, custo, saude, estado de operacao, probabilidade de falha e cenario. Tambem implementa o contrato Auditavel</p>
 */
public abstract class Maquina implements Auditavel{

    /**
     * Gerador de numeros aleatorios compartilhado pelas maquinas
     */
    protected static Random RANDOM = new Random();
    /**
     * Nome identificador da maquina
     */
    private String nome;
    /**
     * Indica se a maquina esta ligada
     */
    private boolean ligada;
    /**
     * Capacidade maxima de processamento da maquina
     */
    private float capacidadeMaxima;
    /**
     * Probabilidade-base de falha da maquina
     */
    private double probabilidadeFalha; //numero entre 0.0 e 1.0
    /**
     * Custo de operacao da maquina por unidade processada
     */
    private double custoOperacao;
    /**
     * Saude atual da maquina
     */
    private int saude;
    /**
     * Saude maxima que a maquina pode atingir
     */
    private final int saudeMaxima;
    /**
     * Cenario operacional utilizado pela maquina
     */
    private Cenario cenario;
    /**
     * Custo de manutencao por ponto de saude perdido
     */
    private static final double CUSTO_MANUTENCAO_POR_PONTO = 0.5;

    /**
     * Inicializa uma maquina com os parametros operacionais informados
     *
     * <p>A maquina eh criada desligada e sua saude maxima eh definida como a saude inicial fornecida</p>
     *
     * @param nome nome da maquina
     * @param capacidadeMaxima capacidade maxima de processamento
     * @param probabilidadeFalha probabilidade-base de falha
     * @param custoOperacao custo de operacao por unidade processada
     * @param saude saude inicial da maquina
     * @param cenario cenario operacional da maquina
     */
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.ligada = false;
        this.saude = saude;
        this.saudeMaxima = saude;
        this.cenario = cenario;
    }

    /**
     * Configura a semente utilizada pelo gerador aleatorio compartilhado pelas maquinas
     *
     * <p>Pode ser utilizada para tornar os resultados aleatorios reproduziveis durante testes</p>
     *
     * @param seed semente utilizada para inicializar o gerador aleatorio
     */
    public static void configurarSemente(long seed){
        RANDOM = new Random(seed);
    }

    /**
     * Processa um produto na maquina.
     *
     * <p>Cada subclasse deve implementar o comportamento especifico de sua etapa da linha de producao</p>
     *
     * @param produto produto a ser processado
     * @return se o processamento foi concluido com sucesso
     */
    public abstract boolean processar(Produto produto);

    /**
     * Retorna o tipo da maquina
     *
     * @return tipo de maquina
     */
    public abstract String getTipo();

    /**
     * Liga a maquina
     */
    public void ligar(){
        this.ligada = true;
    }

    /**
     * Desliga a maquina
     */
    public void desligar(){
        this.ligada = false;
    }

    /**
     * Verifica se a maquina esta ligada
     * 
     * @return se a maquina esta ligada
     */
    public boolean estaLigada(){
        return ligada;
    }

    /**
     * Retorna o nome da maquina
     * 
     * @return nome da maquina
     */
    public String getNome(){
        return nome;
    }

    /**
     * Retorna a capacidade maxima de processamento da maquina
     *
     * @return capacidade maxima
     */
    public float getCapacidadeMaxima(){
        return capacidadeMaxima;
    }

    /**
     * Retorna o custo de operacao da maquina
     *
     * @return custo de operacao por unidade processada
     */
    public double getCustoOperacao(){
        return custoOperacao;
    }

    /**
     * Verifica aleatoriamente se a maquina sofre uma falha durante sua operacao
     *
     * <p>A probabilidade efetiva considera a probabilidade-base da maquina, o multiplicador de falha do cenario e a saude atual da maquina</p>
     *
     * @return True se uma falha for sorteada e False se a maquina estiver desligada/quebrada ou se nenhuma falha ocorrer
     */
    protected boolean verificarFalha(){
        if (saude <= 0 || this.ligada == false) {
            return false;
        }

        double fatorSaude = 100.0 / saude;
        double prob = probabilidadeFalha * cenario.getMultiplicadorFalha() * fatorSaude;
        return RANDOM.nextDouble() < prob;
    }

    /**
     * Gera um relatorio textual com nome, tipo, saude e necessidade de manutencao da maquina
     *
     * @return relatorio de diagnostico da maquina
     */
    public String gerarRelatorioDiagnostico(){
        return  String.format("Máquina: %s | Tipo: %s | Saúde: %d%% | Manutenção necessária: %s",
        getNome(), getTipo(), saude, precisaManutencao() ? "SIM" : "NÃO");
    }

    /**
     * Verifica se a saude da maquina atingiu o nivel que exige manutencao
     *
     * <p>Uma maquina necessita de manutencao quando sua saude eh menor ou igual a 30</p>
     *
     * @return se a saude for menor ou igual a 30
     */
    public boolean precisaManutencao(){
        if (saude <= 30){
            return true;
        }
        return false;
        
    }

    /**
     * Verifica se a maquina esta quebrada
     *
     * @return se a saude da maquina for menor ou igual a zero
     */
    public boolean estaQuebrada(){
        return saude <= 0;
    }

    /**
     * Determina o estado atual da maquina
     *
     * <p>A prioridade dos estados é: quebrada, em manutencao, desligada e operacional</p>
     *
     * @return status atual da maquina
     */
    public StatusMaquina getStatusMaquina(){
        if(estaQuebrada()){
            return StatusMaquina.QUEBRADA;
        }
        if(precisaManutencao()){
            return StatusMaquina.MANUTENCAO;
        }
        if(!estaLigada()){
            return StatusMaquina.DESLIGADA;
        }
        
        return StatusMaquina.OPERACIONAL;
        
    }

    /**
     * Verifica se a maquina ainda esta apta a operar, considerando apenas se esta ou nao quebrada
     *
     * @return se a saude for maior que zero
     */
    public boolean estaApta(){
        return !estaQuebrada();
    }

    /**
     * Retorna a saude atual da maquina
     *
     * @return saude atual
     */
    public int getSaude(){
        return saude;
    }

    /**
     * Retorna a saude maxima da maquina
     *
     * @return saude maxima
     */
    public int getSaudeMaxima(){
        return saudeMaxima;
    }

    /**
     * Verifica se a maquina possui qualquer quantidade de saude perdida
     *
     * @return se a saude atual for menor que a saude maxima
     */
    public boolean precisaReparo(){
        return saude < saudeMaxima;
    }

    /**
     * Verifica se a maquina pode participar de uma operacao
     *
     * <p>Maquinas operacionais e maquinas que necessitam de manutencao ainda sao consideradas aptas para operar. Maquinas desligadas ou quebradas nao podem operar</p>
     *
     * @return se o status for Operacional ou Manutencao
     */
    public boolean podeOperar(){
        StatusMaquina status = getStatusMaquina();
        return status == StatusMaquina.OPERACIONAL || status == StatusMaquina.MANUTENCAO;
    }

    /**
     * Reduz a saude da maquina utilizando um valor aleatorio dentro da faixa de desgaste definida pelo cenario
     *
     * <p>O valor sorteado inclui tanto o desgaste minimo quanto o maximo</p>
     */
    public void deteriorarMaquina(){
         int desgaste = RANDOM.nextInt(cenario.getDesgasteMinimo(), cenario.getDesgasteMaximo() + 1);
         this.saude = Math.max(0, saude - desgaste);
    }

    /**
     * Calcula o custo necessario para restaurar a maquina a saude maxima
     *
     * @return custo de manutencao da maquina
     */
    public double calcularCustoManutencao(){
        return (saudeMaxima - saude) * CUSTO_MANUTENCAO_POR_PONTO;
    }

    /**
     * Restaura a saude da maquina ao valor maximo
     */
    public void realizarManutencao(){
        this.saude = saudeMaxima;
    }
}
