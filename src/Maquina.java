import java.util.Random;

public abstract class Maquina implements Auditavel{
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

        Random random = new Random();
        double fatorSaude = 100.0 / saude;
        double prob = probabilidadeFalha * cenario.getMultiplicadorFalha() * fatorSaude;
        return random.nextDouble() < prob;
    }

    public String gerarRelatorioDiagnostico(){
        return this.nome + "| Saúde: " + this.saude +"| Manutenção Necessária: " + this.precisaManutencao();
    }

    public boolean precisaManutencao(){
        if (saude <= 30){
            return true;
        }
        return false;
        
    }

    public void deteriorarMaquina(){
         Random random = new Random();
         saude -= random.nextInt(cenario.getDesgasteMinimo(), cenario.getDesgasteMaximo());
    }
}
