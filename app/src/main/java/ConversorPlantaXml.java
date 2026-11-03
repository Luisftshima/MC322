import java.util.Map;
import java.util.function.Supplier;

import javax.swing.text.Document;

import org.w3c.dom.Element;

import excecoes.ArquivoCorrompidoException;
import excecoes.DadosInvalidosException;
import excecoes.PersistenciaException;

public class ConversorPlantaXml {
    private static final String VERSAO = "1";

    @FunctionalInterface 
    private interface FabricaMaquina{
        Maquina criar(String nome, int capacidadeMaxima, double probabilidadeFalha, double custoOperacaom, int saudeParte, Cenario cenario);
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

        int saudeMaxima = maquina.getSaudeMaximaPorParte():
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
            throw new ArquivoCorrompidoException("Versao do arquivo nao suportada: '" + versao + "' (esperada: )" + VERSAO + ")");
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
}
