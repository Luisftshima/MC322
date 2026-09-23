import java.util.Random;

public abstract class Maquina implements Auditavel{
    private String nome;
    private boolean ligada;
    private float capacidadeMaxima;
    private double probabilidadeFalha; //numero entre 0.0 e 1.0
    private double custoOperacao;
    private int saude;
    private int totalOperacoes = 0;
    private int totalFalhas = 0;

    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude){
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.ligada = false;
        this.saude = saude;
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

    protected boolean verificarFalha(double multiplicadorFalha){
        Random random = new Random();
        if (saude <= 0) {
            return 1.0;
        }

        // Inversamente proporcional à saúde: com 50% de saúde, a chance dobra.
        double fatorSaude = 100.0 / saude;
        double prob = probabilidadeFalha * multiplicadorFalha * fatorSaude;
        return random.nextDouble() < prob;
    }
    }

    public String gerarRelatorioDiagnostico(){
        return "";
    }

    public boolean precisaManutencao(){
        if (saude <= 30){
            return true;
        }
        return false;
        
    }
}
