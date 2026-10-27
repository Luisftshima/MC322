import java.util.Random;
import java.util.List;
import java.util.ArrayList;

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
     * Custo de operacao da maquina por unidade processada
     */
    private double custoOperacao;
    /**
     * Cenario operacional utilizado pela maquina
     */
    private Cenario cenario;
    /**
     * Custo de manutencao por ponto de saude perdido
     */
    private static final double CUSTO_MANUTENCAO_POR_PONTO = 0.5;
    private static final double FATOR_PERDA_PESO = 0.5;
    private static final double FATOR_PERDA_TEMPERATURA = 0.5;

    private final Balanca balanca;
    private final List<BracoRobotico> bracos;
    private final ReguladorTemperatura regulador;

    private static final double PRECISAO_BALANCA = 0.99;
    private static final double VELOCIDADE_BRACO = 0.99;
    private static final double LIMITE_MANUTENCAO = 0.30;

    /**
     * Inicializa uma maquina com os parametros operacionais informados
     *
     * <p>A maquina eh criada desligada e sua saude maxima eh definida como a saude inicial fornecida</p>
     *
     * @param nome nome da maquina
     * @param capacidadeMaxima capacidade maxima de processamento
     * @param probabilidadeFalha probabilidade-base de falha
     * @param custoOperacao custo de operacao por unidade processada
     * @param saudeParte saude maxima de cada parte
     * @param qtdBracos quantidade de bracos roboticos
     * @param temperaturaAlvo temperatura que o regulador deve manter
     * @param cenario cenario operacional da maquina
     * @throws IllegalArgumentException se a quantidade de bracos for menor que 1
     */
    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saudeParte, 
                    int qtdBracos, double temperaturaAlvo, Cenario cenario){
        if (qtdBracos < 1){
            throw new IllegalArgumentException("A maquina precisa de pelo menos um braco robotico");
        }
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.custoOperacao = custoOperacao;
        this.ligada = false;
        this.cenario = cenario;

        this.balanca = new Balanca(saudeParte, probabilidadeFalha, PRECISAO_BALANCA);
        this.regulador = new ReguladorTemperatura((saudeParte), probabilidadeFalha, temperaturaAlvo);
        this.bracos = new ArrayList<>();
        for (int i = 0; i < qtdBracos; i++){
            this.bracos.add(new BracoRobotico(saudeParte, probabilidadeFalha, VELOCIDADE_BRACO));
        }
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
        if (estaQuebrada() || !ligada) {
            return false;
        }

        double multiplicador = cenario.getMultiplicadorFalha();
        if (balanca.falhou(multiplicador) | regulador.falhou(multiplicador)){
            return true;
        }
        for (BracoRobotico braco : bracos){
            if (braco.falhou(multiplicador)){
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se a saude da maquina atingiu o nivel que exige manutencao
     *
     * <p>Uma maquina necessita de manutencao quando sua saude eh menor ou igual a 30</p>
     *
     * @return se a saude for menor ou igual a 30
     */
    public boolean precisaManutencao(){
        return getSaude() <= LIMITE_MANUTENCAO * getSaudeMaxima();
    }

    /**
     * Verifica se a maquina esta quebrada
     *
     * @return se a saude da maquina for menor ou igual a zero
     */
    public boolean estaQuebrada(){
        if (balanca.estaQuebrada() || regulador.estaQuebrada()){
            return true;
        }
        for (BracoRobotico braco : bracos){
            if (braco.estaQuebrada()){
                return true;
            }
        }
        return false;
    }

    /**
     * Determina o estado atual da maquina
     *
     * <p>A prioridade dos estados são: quebrada, em manutencao, desligada e operacional</p>
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
        int total = balanca.getSaudeAtual() + regulador.getSaudeAtual();
        for (BracoRobotico braco : bracos){
            total += braco.getSaudeAtual();
        }
        return total;
    }

    /**
     * Retorna a saude maxima da maquina
     *
     * @return saude maxima
     */
    public int getSaudeMaxima(){
        int total = balanca.getSaudeMaxima() + regulador.getSaudeMaxima();
        for (BracoRobotico braco : bracos){
            total += braco.getSaudeMaxima();
        }
        return total;
    }

    /**
     * Verifica se a maquina possui qualquer quantidade de saude perdida
     *
     * @return se a saude atual for menor que a saude maxima
     */
    public boolean precisaReparo(){
        return getSaude() < getSaudeMaxima();
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
     * @throws IllegalArgumentException se o argumento de minimo for maior que o maximo
     */
    public void deteriorarMaquina(){
        balanca.deteriorar(sortearDesgaste());
        regulador.deteriorar(sortearDesgaste());
        for (BracoRobotico braco : bracos){
            braco.deteriorar(sortearDesgaste());
        }
    }

    /**
     * determina aleatoriamente um desgaste para a parte
     * @return retorna um desgaste entre o desgaste minimo e maximo
     */
    private int sortearDesgaste(){
        try {
            return RANDOM.nextInt(cenario.getDesgasteMinimo(), cenario.getDesgasteMaximo() + 1);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("O desgaste minimo precisa ser menor que o maximo");
        }
    }

    /**
     * Calcula o custo necessario para restaurar a maquina a saude maxima
     *
     * @return custo de manutencao da maquina
     */
    public double calcularCustoManutencao(){
        return (getSaudeMaxima() - getSaude()) * CUSTO_MANUTENCAO_POR_PONTO;
    }

    /**
     * Restaura a saude da maquina ao valor maximo
     */
    public void realizarManutencao(){
        balanca.reparar();
        regulador.reparar();
        for (BracoRobotico braco : bracos){
            braco.reparar();
        }
    }
    /**
     * o braco mais lento define o tempo
     * @param tempoBase tempo que o produto leva para ser produzido
     * @return tempo de cada etapa
     */
    private double calcularTempoEtapa(double tempoBase){
        float maisLento = 0f;
        for (BracoRobotico braco:bracos){
            maisLento= Math.max(maisLento, braco.calcular_tempo((float) tempoBase));
        }
        return maisLento;
    }

    protected void aplicarEfeitosDasPartes(Produto produto){
        //balanca
        float real = produto.getMateriaPrimaPorUnidade();
        float medido = balanca.medir(real);
        produto.reduzirQualidade(Math.abs(medido-real) / real * FATOR_PERDA_PESO);

        //regulador de temperatura
        double alvo = regulador.getTemperaturaAlvo();
        if (alvo != 0){
            double desvio = Math.abs(regulador.regular() - alvo) / Math.abs(alvo);
            produto.reduzirQualidade((desvio * FATOR_PERDA_TEMPERATURA));
        }

        //bracos roboticos
        produto.adicionarTempo(calcularTempoEtapa(produto.calcularTempoProducao()));
    }

    /**
     * Gera um relatorio hierarquico: uma linha da maquina e por componente da maquina
     *
     * @return relatorio de diagnostico da maquina
     */
    public String gerarRelatorioDiagnostico(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Maquina: %s | Tipo: %s | Saude %d%% | Manutencao necessaria: %s",
            getNome(), getTipo(), getSaude() * 100 / getSaudeMaxima(), precisaManutencao()? "SIM" :"NAO"));
        sb.append("\n   >").append(linhaComponente("Balanca", balanca.getSaudeAtual(), balanca.getSaudeMaxima(), balanca.estaQuebrada()));
        for (int i = 0; i < bracos.size(); i++){
            BracoRobotico braco = bracos.get(i);
            sb.append("\n   >").append(linhaComponente("Braco " + (i + 1), braco.getSaudeAtual(), braco.getSaudeMaxima(), braco.estaQuebrada()));
        }
        sb.append("\n   >").append(linhaComponente("Regulador de temperatura", regulador.getSaudeAtual(), regulador.getSaudeMaxima(), regulador.estaQuebrada()));
        return sb.toString();
    }

    /**
     * Funcao auxiliar para printar o relatorio das maquinas
     * @param nomeComponente nome do componente da maquina
     * @param atual saude atual do componente
     * @param maxima saude maxima do componente
     * @param quebrada se o componente esta quebrado
     * @return uma linha de string formatado
     */
    private String linhaComponente(String nomeComponente, int atual, int maxima, boolean quebrada){
        return String.format("%s: %s (%d/%d)", nomeComponente, quebrada? "QUEBRADA" : "OK", atual, maxima);
    }
}
