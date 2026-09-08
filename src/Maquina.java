import java.util.Random;

public abstract class Maquina {
    private String nome;
    private boolean ligada = false;
    private float capacidadeMaxima;
    private float probabilidadeFalha;
    private float custoOperacao;

    abstract void processar;
    abstract void getTipo;

    public Maquina(String n, int max){
        nome = n;
        capacidadeMaxima = max;
    }

    public void ligar(){
        ligada = true;
    }

    public void desligar(){
        ligada = false;
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

    public String getName(){
        return nome;
    }

    public float getCustoOperacao(){
        return custoOperacao;
    }

    public int verificarFalha(){
        Random random = new Random();

        boolean valorBoolean = random.nextBoolean();
    }
}
