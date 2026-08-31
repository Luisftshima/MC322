public class Maquina {
    private String nome;
    private boolean ligada = false;
    private float capacidadeMaxima;

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

    public void processar(MateriaPrima m, Produto p){
        if(ligada){
            if(m.verificarDisponibilidade(p.getDemandaMateriaPrima())){
                m.consumir(p.getDemandaMateriaPrima());
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
}
