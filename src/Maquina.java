import java.util.Random;

public abstract class Maquina {
    private String nome;
    private boolean ligada = false;
    private float capacidadeMaxima;
    private double probabilidadeFalha; //numero entre 0.0 e 1.0
    private double custoOperacao;

    public Maquina(String nome, int max, double probabilidadeFalha, double custoOperacao){
        this.nome = nome;
        this.capacidadeMaxima = max;
        this.probabilidadeFalha = probabilidadeFalha;
        this.custoOperacao = custoOperacao;
    }

    public abstract boolean processar(Produto produto);
    public abstract String getTipo();

    public void ligar(){
        this.ligada = true;
    }

    public void desligar(){
        this.ligada = false;
    }

    public void processar(MateriaPrima m, Produto p, float quant){
        if(ligada){
            if(m.verificarDisponibilidade(quant*p.getDemandaMateriaPrima())){
                m.consumir(quant * p.getDemandaMateriaPrima());
            }
        }
    }

    public String getNome(){
        return nome;
    }

    public boolean estaLigada(){
        return ligada;
    }

    public boolean temCapacidade(float quant){
        if(quant <= capacidadeMaxima){
            return true;
        }
        return false;
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
}
