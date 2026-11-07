package br.unicamp.ic.mc322.persistence;

import java.util.Map;
import java.util.function.Supplier;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.unicamp.ic.mc322.exception.ArquivoCorrompidoException;
import br.unicamp.ic.mc322.exception.DadosInvalidosException;
import br.unicamp.ic.mc322.exception.PersistenciaException;


import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.MateriaPrima;
import br.unicamp.ic.mc322.model.Maquina;
import br.unicamp.ic.mc322.model.Cenario;
import br.unicamp.ic.mc322.model.Misturador;
import br.unicamp.ic.mc322.model.Embaladora;
import br.unicamp.ic.mc322.model.EstacaoInspecao;
import br.unicamp.ic.mc322.model.StatusDemanda;
import br.unicamp.ic.mc322.model.TipoProduto;

import br.unicamp.ic.mc322.strategy.EstrategiaProducao;
import br.unicamp.ic.mc322.strategy.EstrategiaOrdemChegada;
import br.unicamp.ic.mc322.strategy.EstrategiaMaiorDemanda;
import br.unicamp.ic.mc322.strategy.EstrategiaMaximoProdutos;
import br.unicamp.ic.mc322.strategy.EstrategiaNatal;

import br.unicamp.ic.mc322.service.GerenciadorProducao;

import java.util.ArrayList;
import java.util.List;

public class ConversorPlantaXml {
    private static final String VERSAO = "1";

    @FunctionalInterface 
    private interface FabricaMaquina{
        Maquina criar(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacao, int saudeParte, Cenario cenario);
    }

    private static final Map<String, FabricaMaquina> FABRICAS_MAQUINA = Map.of(
        Misturador.class.getSimpleName(), Misturador::new,
        Embaladora.class.getSimpleName(), Embaladora::new,
        EstacaoInspecao.class.getSimpleName(), EstacaoInspecao::new
    );

    private static final Map<String, Supplier<EstrategiaProducao>> FABRICAS_ESTRATEGIA = Map.of(
        EstrategiaOrdemChegada.class.getSimpleName(), EstrategiaOrdemChegada::new,
        EstrategiaMaiorDemanda.class.getSimpleName(), EstrategiaMaiorDemanda::new,
        EstrategiaMaximoProdutos.class.getSimpleName(), EstrategiaMaximoProdutos::new,
        EstrategiaNatal.class.getSimpleName(), EstrategiaNatal::new
    );

    // Escrita: objetos -> XML

    public void escrever(GerenciadorProducao planta, Document documento){
        Element raiz = documento.createElement("planta");
        raiz.setAttribute("versao", VERSAO);
        documento.appendChild(raiz);

        adicionarTexto(documento, raiz, "cenario", planta.getCenario().name());
        adicionarTexto(documento, raiz, "orcamento", Double.toString(planta.getBudget()));
        adicionarTexto(documento, raiz, "proximoLote", Integer.toString(planta.getProximoLote()));
        adicionarTexto(documento, raiz, "estrategia", chaveEstrategia(planta.getEstrategia()));

        Element materiasPrimas = adicionar(documento, raiz, "materiasPrimas");
        escreverMateriaPrima(documento, materiasPrimas, planta.getMateriaPrima());

        Element maquinas = adicionar(documento, raiz, "maquinas");
        for (Maquina maquina : planta.getMaquinas()){
            escreverMaquina(documento, maquinas, maquina);
        }

        Element demandas = adicionar(documento, raiz, "demandas");
        for (Demanda demanda : planta.getDemandas()){
            Element elemento = adicionar(documento, demandas, "demanda");
            elemento.setAttribute("tipo", demanda.getTipoProduto().name());
            adicionarTexto(documento, elemento, "quantidade", Integer.toString(demanda.getQuantidadeProdutos()));
            adicionarTexto(documento, elemento, "status", demanda.getStatus().name());
        }
    }

    private void escreverMateriaPrima(Document documento, Element pai, MateriaPrima materiaPrima){
        Element elemento = adicionar(documento, pai, "materiaPrima");
        adicionarTexto(documento, elemento, "nome", materiaPrima.getNome());
        adicionarTexto(documento, elemento, "quantidade", Float.toString(materiaPrima.getQuantidade()));
        adicionarTexto(documento, elemento, "unidade", materiaPrima.getUnidade());
        adicionarTexto(documento, elemento, "custoPorUnidade", Double.toString(materiaPrima.getCustoPorUnidade()));
    }

    private void escreverMaquina(Document documento, Element pai, Maquina maquina){
        String tipo = maquina.getClass().getSimpleName();
        if(!FABRICAS_MAQUINA.containsKey(tipo)){
            throw new PersistenciaException("Tipo de maquina nao suportado pela persistencia: " + tipo);
        }

        Element elemento = adicionar(documento, pai, "maquina");
        elemento.setAttribute("tipo", tipo);
        adicionarTexto(documento, elemento, "nome", maquina.getNome());
        adicionarTexto(documento, elemento, "capacidadeMaxima", Integer.toString((int) maquina.getCapacidadeMaxima()));
        adicionarTexto(documento, elemento, "custoOperacao", Double.toString(maquina.getCustoOperacao()));
        adicionarTexto(documento, elemento, "probabilidadeFalha", Double.toString(maquina.getProbabilidadeFalha()));
        adicionarTexto(documento, elemento, "ligada", Boolean.toString(maquina.estaLigada()));

        int saudeMaxima = maquina.getSaudeMaximaPorParte();
        Element componentes = adicionar(documento, elemento, "componentes");
        escreverComponente(documento, componentes, "balanca", maquina.getSaudeBalanca(), saudeMaxima);
        for (int saudeBraco : maquina.getSaudeBracos()){
            escreverComponente(documento, componentes, "braco", saudeBraco, saudeMaxima);
        }
        escreverComponente(documento, componentes, "reguladorTemperatura", maquina.getSaudeRegulador(), saudeMaxima);
    }

    private void escreverComponente(Document documento, Element pai, String nome, int saudeAtual, int saudeMaxima){
        Element elemento = adicionar(documento, pai, nome);
        elemento.setAttribute("saudeAtual", Integer.toString(saudeAtual));
        elemento.setAttribute("saudeMaxima", Integer.toString(saudeMaxima));
    }

    private String chaveEstrategia(EstrategiaProducao estrategia){
        String chave = estrategia.getClass().getSimpleName();
        if(!FABRICAS_ESTRATEGIA.containsKey(chave)){
            throw new PersistenciaException("Estrategia de producao nao suportada pela persistencia: " + chave);
        }
        return chave;
    }

    private Element adicionar(Document documento, Element pai, String nome){
        Element elemento = documento.createElement(nome);
        pai.appendChild(elemento);
        return elemento;
    }

    private void adicionarTexto(Document documento, Element pai, String nome, String valor){
        adicionar(documento, pai, nome).setTextContent(valor);
    }

    //Leitura: XML -> objetos

    public GerenciadorProducao ler(Document documento){
        Element raiz = documento.getDocumentElement();
        if (raiz == null || !"planta".equals(raiz.getTagName())){
            throw new ArquivoCorrompidoException("O elemento raiz do arquivo deveria ser <planta>");
        }
        String versao = raiz.getAttribute("versao");
        if (!VERSAO.equals(versao)){
            throw new ArquivoCorrompidoException("Versao do arquivo nao suportada: '" + versao + "' (esperada: " + VERSAO + ")");
        }
        try{
            Cenario cenario = enumerado(Cenario.class, texto(raiz, "cenario"), "cenario");
            double orcamento = decimal(texto(raiz, "orcamento"), "orcamento");
            int proximoLote = inteiro(texto(raiz, "proximoLote"), "proximoLote");
            EstrategiaProducao estrategia = lerEstrategia(texto(raiz, "estrategia"));
            MateriaPrima materiaPrima = lerMateriaPrima(raiz);
            List<Maquina> maquinas = lerMaquinas(raiz, cenario);
            List<Demanda> demandas = lerDemandas(raiz);
            return new GerenciadorProducao(materiaPrima, cenario, orcamento, proximoLote, estrategia, maquinas, demandas);
        }catch (IllegalArgumentException | IllegalStateException e){
            throw new DadosInvalidosException("Os dados do arquivo violam as regras da fabrica: " + e.getMessage(), e);
        }
    }

    private EstrategiaProducao lerEstrategia(String chave){
        Supplier<EstrategiaProducao> fabrica = FABRICAS_ESTRATEGIA.get(chave);
        if(fabrica == null){
            throw new DadosInvalidosException("Estrategia de Producao desconhecida: '" + chave + "'");
        }
        return fabrica.get(); 
    }

    private MateriaPrima lerMateriaPrima(Element raiz){
        Element elemento = filhoUnico(filhoUnico(raiz, "materiasPrimas"), "materiaPrima");
        return new MateriaPrima(
            texto(elemento, "nome"),
            decimalSimples(texto(elemento, "quantidade"), "quantidade"),
            texto(elemento, "unidade"),
            decimal(texto(elemento, "custoPorUnidade"), "custoPorUnidade"));
    }

    private List<Maquina> lerMaquinas(Element raiz, Cenario cenario){
        List<Maquina> maquinas = new ArrayList<>();
        for (Element elemento : filhos(filhoUnico(raiz, "maquinas"), "maquina")){
            maquinas.add(lerMaquina(elemento, cenario));
        }
        return maquinas;
    }

    private Maquina lerMaquina(Element elemento, Cenario cenario){
        String tipo = atributo(elemento, "tipo");
        FabricaMaquina fabrica = FABRICAS_MAQUINA.get(tipo);
        if(fabrica == null){
            throw new DadosInvalidosException("Tipo de maquina desconhecido: '" + tipo + "'");
        }
        String nome = texto(elemento, "nome");
        int capacidade = inteiro(texto(elemento, "capacidadeMaxima"), "capacidadeMaxima");
        double custo = decimal(texto(elemento, "custoOperacao"), "custoOperacao");
        double probabilidade = decimal(texto(elemento, "probabilidadeFalha"), "probabilidadeFalha");
        boolean ligada = booleano(texto(elemento, "ligada"), "ligada");

        exigir(!nome.isEmpty(), "O nome da maquina nao pode ser vazio");
        exigir(probabilidade > 0  && probabilidade < 1,
            "A probabilidade de falha da maquina '" + nome + "' deve ser entre 0 e 1");

        Element componentes = filhoUnico(elemento, "componentes");
        int[] balanca = lerComponente(filhoUnico(componentes, "balanca"));
        List<int[]> bracos = new ArrayList<>();
        for (Element braco : filhos(componentes, "braco")){
            bracos.add(lerComponente(braco));
        }
        int[] regulador = lerComponente(filhoUnico(componentes, "reguladorTemperatura"));

        //maquina eh criada com uma unica saude maxima, compartilhada pelos componentes
        int saudeMaxima = balanca[1];
        exigir(regulador[1] == saudeMaxima, "Todos os componentes da maquina '" + nome + "' devem ter a mesma saude maxima");
        List<Integer> saudeBracos = new ArrayList<>();
        for(int[] braco : bracos){
            exigir(braco[1] == saudeMaxima, "Todos os componentes da maquina '" + nome + "' devem ter a mesma saude maxima");
            saudeBracos.add(braco[0]);
        }

        Maquina maquina = fabrica.criar(nome, capacidade, probabilidade, custo, saudeMaxima, cenario);
        exigir(maquina.getQuantidadeBracos() == bracos.size(),
                "A maquina '" + nome + "' (" + tipo + ") deve ter " + maquina.getQuantidadeBracos()
                + " bracos, mas o arquivo informa " + bracos.size());
        maquina.restaurarEstado(ligada, balanca[0], saudeBracos, regulador[0]);
        return maquina;
    }

    private int[] lerComponente(Element elemento){
        int atual = inteiro(atributo(elemento, "saudeAtual"), "saudeAtual");
        int maxima = inteiro(atributo(elemento, "saudeMaxima"), "saudeMaxima");
        return new int[]{atual, maxima};
    }

    private List<Demanda> lerDemandas(Element raiz){
        List<Demanda> demandas = new ArrayList<>();
        for(Element elemento : filhos(filhoUnico(raiz, "demandas"), "demanda")){
            TipoProduto tipo = enumerado(TipoProduto.class, atributo(elemento, "tipo"), "tipo");
            int quantidade  = inteiro(texto(elemento, "quantidade"), "quantidade");
            StatusDemanda status = enumerado(StatusDemanda.class, texto(elemento, "status"), "status");
            demandas.add(new Demanda(tipo, quantidade, status));
        }
        return demandas;
    }

    //metodos usados na leitura

    private void exigir(boolean condicao, String mensagem){
        if(!condicao){
            throw new IllegalArgumentException(mensagem);
        }
    }

    private List<Element> filhos(Element pai, String nome){
        List<Element> encontrados = new ArrayList<>();
        NodeList nos = pai.getChildNodes();
        for(int i = 0; i < nos.getLength(); i++){
            Node no = nos.item(i);
            if (no instanceof Element elemento && elemento.getTagName().equals(nome)){
                encontrados.add(elemento);
            }
        }
        return encontrados;
    }

    private Element filhoUnico(Element pai, String nome){
        List<Element> encontrados = filhos(pai, nome);
        if(encontrados.size() != 1){
            throw new ArquivoCorrompidoException("Esperando exatamente um elemento <" + nome + "> dentro de <"
                + pai.getTagName() + ">, mas foram encontrados " + encontrados.size());
        }
        return encontrados.get(0);
    }

    private String texto(Element pai, String nome){
        return filhoUnico(pai, nome).getTextContent().trim();
    }

    private String atributo(Element elemento, String nome){
        if(!elemento.hasAttribute(nome)){
            throw new ArquivoCorrompidoException("O elemento <" + elemento.getTagName() + "> nao possui o atributo '" + nome + "'");
        }
        return elemento.getAttribute(nome).trim();
    }

    private int inteiro(String valor, String campo){
        try{
            return Integer.parseInt(valor);
        }catch (NumberFormatException e){
            throw new ArquivoCorrompidoException("O campo '" + campo + "' deve ser um numero inteiro, mas contem '" + valor + "'", e);
        }
    }

    private double decimal(String valor, String campo){
        try{
            return Double.parseDouble(valor);
        } catch(NumberFormatException e){
            throw new ArquivoCorrompidoException("O campo '" + campo + "' deve ser um numero, mas contem '" + valor + "'", e);
        }
    }

    private float decimalSimples(String valor, String campo){
        try{
            return Float.parseFloat(valor);
        }catch(NumberFormatException e){
            throw new ArquivoCorrompidoException("O campo '" + campo + "' deve ser um numero, mas contem '" + valor + "'", e);
        }
    }

    private boolean booleano(String valor, String campo){
        if ("true".equals(valor)){
            return true;
        }
        if ("false".equals(valor)){
            return false;
        }
        throw new ArquivoCorrompidoException("O campo '" + campo + "' deve ser true ou false, mas contem '" + valor + "'");
    }
    
    private <E extends Enum<E>> E enumerado(Class<E> tipo, String valor, String campo){
        try { return Enum.valueOf(tipo, valor); }
        catch (IllegalArgumentException e){
            throw new DadosInvalidosException("Valor desconhecido para '" + campo + "': '" + valor + "'", e);
        }
    }
}
