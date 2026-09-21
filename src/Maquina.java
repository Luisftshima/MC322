import java.util.Random;

public abstract class Maquina implements Auditavel{
    private String nome;
    private boolean ligada;
    private float capacidadeMaxima;
    private double probabilidadeFalha; //numero entre 0.0 e 1.0
    private double custoOperacao;
    private int saude;

    public Maquina(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saude){
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
        this.ligada = false;
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
        Random random = new Random();
        return random.nextDouble() < probabilidadeFalha;
    }

    public String gerarRelatorioDiagnostico(){
        return "";
    }

    public boolean precisaManutencao(){
        return true;
    }
}
