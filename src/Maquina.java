import java.util.Random;

public abstract class Maquina implements Auditavel{

    protected static Random RANDOM = new Random();
    private String nome;
    private boolean ligada;
    private float capacidadeMaxima;
    private double probabilidadeFalha; //numero entre 0.0 e 1.0
    private double custoOperacao;
    private int saude;
    private Cenario cenario;

    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude, Cenario cenario){
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.ligada = false;
        this.saude = saude;
        this.cenario = cenario;
    }

    public static void configurarSemente(long seed){
        RANDOM = new Random(seed);
    }

    public abstract boolean processar(Produto produto);
    public abstract String getTipo();

    public void ligar(){
        this.ligada = true;
    }

    public void desligar(){
        this.ligada = false;
    }

    public boolean estaLigada(){
        return ligada;
    }

    public String getNome(){
        return nome;
    }

    public float getCapacidadeMaxima(){
        return capacidadeMaxima;
    }

    public double getCustoOperacao(){
        return custoOperacao;
    }

    protected boolean verificarFalha(){
        if (saude <= 0 || this.ligada == false) {
            return false;
        }

        double fatorSaude = 100.0 / saude;
        double prob = probabilidadeFalha * cenario.getMultiplicadorFalha() * fatorSaude;
        return RANDOM.nextDouble() < prob;
    }

    public String gerarRelatorioDiagnostico(){
        return  String.format("Máquina: %s | Tipo: %s | Saúde: %d%% | Manutenção necessária: %s",
        getNome(), getTipo(), saude, precisaManutencao() ? "SIM" : "NÃO");
    }

    public boolean precisaManutencao(){
        if (saude <= 30){
            return true;
        }
        return false;
        
    }

    public boolean estaQuebrada(){
        return saude <= 0;
    }

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

    public boolean podeOperar(){
        StatusMaquina status = getStatusMaquina();
        return status == StatusMaquina.OPERACIONAL || status == StatusMaquina.MANUTENCAO;
    }

    public void deteriorarMaquina(){
         int desgaste = RANDOM.nextInt(cenario.getDesgasteMinimo(), cenario.getDesgasteMaximo() + 1);
         this.saude = Math.max(0, saude - desgaste);
    }
}
