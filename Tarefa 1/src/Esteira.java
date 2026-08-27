public class Esteira {
    private String item;
    private boolean emMovimento = false;
    private float capacidadeMaxima;

    public Esteira(float max){
        capacidadeMaxima = max;
    }

    public void ligar(){
        emMovimento = true;
    }

    public void desligar(){
        emMovimento = false;
    }

    public void adicionarItem(String obj){
        item = obj;
    }

    public void removerItem(){
        item = "";
    }

    public float verificarCapacidade(){
        return capacidadeMaxima;
    }
}
