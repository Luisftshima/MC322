import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import excecoes.MaquinaInexistenteException;

/**
 * CasaDeMaquinasWonka
 * 
 * <p> Relacao de Agregacao com Maquina: as maquinas nascem fora da casa e apenas sao adicionadas a ela.
 * Ja a relacao entre a maquina e suas partes eh uma composicao
 * 
 * <p> A casa nao cuida da verba, apenas informa os custos. Quem cuida da verba eh gerenciador producao
 */
public class CasaDeMaquinasWonka implements Auditavel{
    /**
     * Nome do agrupador
     */
    private final String nome;
    /**
     * Lista de maquinas que compoem a linha de producao
     */
    private ArrayList<Maquina> maquinas = new ArrayList<>();


    /**
     * Cria uma casa de maquinas vazia
     * @param nome nome da casa de maquina
     */
    public CasaDeMaquinasWonka(String nome){
        this.nome = nome;
    }

    /**
     * retorna o nome da casa de maquinas
     * @return nome da casa de maquinas
     */
    public String getNome(){
        return nome;
    }

    /**
     * Adiciona uma maquina a casa
     * @param maquina
     * @throws IllegalArgumentException se a maquina for nula ou ja tiver a maquina na casa
     */
    public void adicionar(Maquina maquina){
        if (maquina == null){
            throw new IllegalArgumentException("Nao eh possivel adicionar uma maquina nula");
        }
        if(maquinas.contains(maquina)){
            throw new IllegalArgumentException("A maquina " + maquina.getNome() + " ja esta na casa de maquinas.");
        }
        maquinas.add(maquina);
    }

    /**
     * retorna uma lista apenas para visualizacao
     * @return uma lista das maquinas imutavel
     */
    public List<Maquina> getMaquinas(){
        return Collections.unmodifiableList(maquinas);
    }

    /**
     * quantidade de maquinas na casa
     * @return quantidade de maquinas
     */
    public int getQuantidadeMaquinas(){
        return maquinas.size();
    }
    
    /**
     * retorna a maquina de acordo com o numero fornecido
     * @param numero numero da maquina
     * @return a maquina de acordo com o numero dado
     */
    public Maquina buscarPorNumero(int numero){
        if(numero < 1 || numero > maquinas.size()){
            throw new MaquinaInexistenteException("O numero da maquina nao existe");
        }
        return maquinas.get(numero - 1);
    }

    /**
     * Procura a primeira maquina da linha que nao esta apta a operar
     *
     * @return primeira maquina quebrada encontrada ou NULL se todas estiverem aptas
     */
    public Maquina primeiraMaquinaQuebrada(){
        for (Maquina m : maquinas){
            if(!m.estaApta()){
                return m;
            }
        }
        return null;
    }

    /**
     * Calcula o custo de operacao de todas as maquinas da linha para produzir uma unidade de produto
     *
     * @return soma dos custos de operacao das maquinas
     */
    public double calcularCustoProducao(){
        double total = 0;
        for (Maquina m : maquinas){
            total += m.getCustoOperacao();
        }
        return total;
    }


    /**
     * Exibe no console o estado atual de todas as maquinas da fabrica, incluindo saude, status e custo de manutencao
     */
    public void exibirMaquinas(){
        System.out.println("===== MAQUINAS =====");
        for (int i = 0; i < maquinas.size(); i++){
            Maquina m = maquinas.get(i);
            System.out.println(String.format("%d - %s | Saude: %d/%d | Status: %s | Custo do reparo: R$ %.2f",
                i + 1, m.getNome(), m.getSaude(), m.getSaudeMaxima(),
                m.getStatusMaquina(), m.calcularCustoManutencao()));
        }
    }

    /**
     * auditoria em cascata: casa > maquina > componentes
     *
     * <p>Chama o relatorio de cada maquina, que por sua vez lista suas partes. Cada nivel eh
     * indentado para mostrar a hierarquia</p>
     *
     * @return relatorio hierarquico e indentado da casa
     */
    @Override
    public String gerarRelatorioDiagnostico(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Casa de Maquinas: %s | Maquinas: %d | Manutencao necessaria: %s",
            nome, maquinas.size(), precisaManutencao() ? "SIM" : "NAO"));
        if (maquinas.isEmpty()){
            sb.append("\n  (nenhuma maquina cadastrada)");
        }
        for (Maquina m : maquinas){
            for (String linha : m.gerarRelatorioDiagnostico().split("\n")){
                sb.append("\n  ").append(linha);
            }
        }
        return sb.toString();
    }

    /**
     * Indica se alguma maquina da casa precisa de manutencao
     *
     * @return true se pelo menos uma maquina precisar de manutencao
     */
    @Override
    public boolean precisaManutencao(){
        for (Maquina m : maquinas){
            if (m.precisaManutencao()){
                return true;
            }
        }
        return false;
    }
}
