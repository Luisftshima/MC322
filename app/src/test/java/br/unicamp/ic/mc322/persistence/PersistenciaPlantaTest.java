
package br.unicamp.ic.mc322.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.MateriaPrima;
import br.unicamp.ic.mc322.model.TipoProduto;
import br.unicamp.ic.mc322.model.Cenario;
import br.unicamp.ic.mc322.model.Maquina;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import br.unicamp.ic.mc322.strategy.EstrategiaMaiorDemanda;

import br.unicamp.ic.mc322.exception.ArquivoInexistenteException;
import br.unicamp.ic.mc322.exception.ArquivoCorrompidoException;
import br.unicamp.ic.mc322.exception.DadosInvalidosException;

class PersistenciaPlantaTest {

    @TempDir
    Path diretorioTemporario;

    private GerenciadorProducao criarFabricaComEstado(Path arquivoXml, PersistenciaPlanta persistencia) {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);

        fabrica.comprarMateriaPrima(100.0f);
        fabrica.atualizarDemanda(TipoProduto.OVO_ARTESANAL, 7);

        Demanda bombons = buscarDemanda(fabrica, TipoProduto.BOMBONS_SORTIDOS);
        bombons.cancelar();

        fabrica.setEstrategia(new EstrategiaMaiorDemanda());

        Maquina misturador = fabrica.getMaquinas().get(0);
        misturador.restaurarEstado(true,80, List.of(95, 90, 85), 70);

        return fabrica;
    }

    private Demanda buscarDemanda(GerenciadorProducao fabrica, TipoProduto tipo) {
        Demanda demandaEncontrada = null;

        for (Demanda demanda : fabrica.getDemandas()) {
            if (demanda.getTipoProduto() == tipo) {
                demandaEncontrada = demanda;
                break;
            }
        }

        if (demandaEncontrada == null) {
            throw new NoSuchElementException();
        }
        
        return demandaEncontrada;
    }

    @Test
    void salvarFabricaEmArquivoXML() throws IOException {
        Path arquivoXml = diretorioTemporario.resolve("planta.xml");
        PersistenciaPlanta persistencia = new PersistenciaPlanta(arquivoXml);

        GerenciadorProducao fabrica = criarFabricaComEstado(arquivoXml, persistencia);

        persistencia.salvar(fabrica);

        assertTrue(Files.exists(arquivoXml));

        assertTrue(Files.size(arquivoXml) > 0);

        String conteudo = Files.readString(arquivoXml);

        assertTrue(conteudo.contains("<planta"));
    }

    @Test
    void preservarDadosAoSalvarECarregar() {
        Path arquivoXml = diretorioTemporario.resolve("planta.xml");
        PersistenciaPlanta persistencia = new PersistenciaPlanta(arquivoXml);

        GerenciadorProducao fabrica = criarFabricaComEstado(arquivoXml, persistencia);

        persistencia.salvar(fabrica);

        GerenciadorProducao fabricaRecuperada = persistencia.carregar();

        assertEquals(fabrica.getCenario(), fabricaRecuperada.getCenario());
        assertEquals(fabrica.getBudget(), fabricaRecuperada.getBudget());

        MateriaPrima estoqueOriginal = fabrica.getMateriaPrima();
        MateriaPrima estoqueRecuperado = fabricaRecuperada.getMateriaPrima();
        assertEquals(estoqueOriginal.getNome(), estoqueRecuperado.getNome());
        assertEquals(estoqueOriginal.getQuantidade(), estoqueRecuperado.getQuantidade());
        assertEquals(estoqueOriginal.getUnidade(), estoqueRecuperado.getUnidade());
        assertEquals(estoqueOriginal.getCustoPorUnidade(), estoqueRecuperado.getCustoPorUnidade());

        assertEquals(fabrica.getDemandas().size(), fabricaRecuperada.getDemandas().size());

        for (int i = 0; i < fabrica.getDemandas().size(); i++) {

            Demanda demandaOriginal = fabrica.getDemandas().get(i);
            Demanda demandaRecuperada = fabricaRecuperada.getDemandas().get(i);

            assertEquals(demandaOriginal.getTipoProduto(), demandaRecuperada.getTipoProduto());
            assertEquals(demandaOriginal.getQuantidadeProdutos(), demandaRecuperada.getQuantidadeProdutos());
            assertEquals(demandaOriginal.getStatus(), demandaRecuperada.getStatus());
        }

        assertTrue(fabricaRecuperada.getEstrategia() instanceof EstrategiaMaiorDemanda);

        assertEquals(fabrica.getQuantidadeMaquinas(), fabricaRecuperada.getQuantidadeMaquinas());

        Maquina maquinaOriginal = fabrica.getMaquinas().get(0);
        Maquina maquinaRecuperada = fabricaRecuperada.getMaquinas().get(0);

        assertEquals(maquinaOriginal.getClass(), maquinaRecuperada.getClass());
        assertEquals(maquinaOriginal.getNome(), maquinaRecuperada.getNome());
        assertEquals(maquinaOriginal.getCapacidadeMaxima(), maquinaRecuperada.getCapacidadeMaxima());
        assertEquals(maquinaOriginal.getCustoOperacao(), maquinaRecuperada.getCustoOperacao());
        assertEquals(maquinaOriginal.getSaudeBalanca(), maquinaRecuperada.getSaudeBalanca());
        assertEquals(maquinaOriginal.getSaudeBracos(), maquinaRecuperada.getSaudeBracos());
        assertEquals(maquinaOriginal.getSaudeRegulador(), maquinaRecuperada.getSaudeRegulador());
        assertEquals(maquinaOriginal.estaLigada(), maquinaRecuperada.estaLigada());
    }

    @Test
    void lancarExcecaoQuandoArquivoNaoExiste() {
        Path arquivoXml = diretorioTemporario.resolve("planta.xml");
        PersistenciaPlanta persistencia = new PersistenciaPlanta(arquivoXml);

        assertThrows(ArquivoInexistenteException.class, () -> persistencia.carregar());
    }

    @Test
    void lancarExcecaoQuandoArquivoEstaVazio() throws IOException {
        Path arquivoXml = diretorioTemporario.resolve("planta.xml");
        PersistenciaPlanta persistencia = new PersistenciaPlanta(arquivoXml);

        Files.createFile(arquivoXml);

        assertThrows(ArquivoCorrompidoException.class, () -> persistencia.carregar());
    }

    @Test
    void lancarExcecaoQuandoXMLEstaCorrompido() throws IOException {
        Path arquivoXml = diretorioTemporario.resolve("planta.xml");
        PersistenciaPlanta persistencia = new PersistenciaPlanta(arquivoXml);

        Files.writeString(arquivoXml, "<planta>");

        assertThrows(ArquivoCorrompidoException.class, () -> persistencia.carregar());
    }

    @Test
    void lancarExcecaoQuandoDadosSaoInvalidos() throws IOException {
        Path arquivoXml = diretorioTemporario.resolve("planta.xml");
        PersistenciaPlanta persistencia = new PersistenciaPlanta(arquivoXml);
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);

        persistencia.salvar(fabrica);

        String conteudo = Files.readString(arquivoXml);
        String conteudoInvalido = conteudo.replace("<quantidade>2000.0</quantidade>", "<quantidade>-1.0</quantidade>");

        assertNotEquals(conteudo, conteudoInvalido);

        Files.writeString(arquivoXml, conteudoInvalido);
        assertThrows(DadosInvalidosException.class, () -> persistencia.carregar());
    }

}

