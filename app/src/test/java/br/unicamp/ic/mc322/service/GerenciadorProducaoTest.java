package br.unicamp.ic.mc322.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import br.unicamp.ic.mc322.exception.CompraInvalidaException;
import br.unicamp.ic.mc322.exception.DemandaInvalidaException;
import br.unicamp.ic.mc322.exception.RecursoInsuficienteException;
import br.unicamp.ic.mc322.model.Cenario;
import br.unicamp.ic.mc322.model.Demanda;
import br.unicamp.ic.mc322.model.MateriaPrima;
import br.unicamp.ic.mc322.model.StatusDemanda;
import br.unicamp.ic.mc322.model.TipoProduto;

public class GerenciadorProducaoTest {
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
    void criarFabricaTest() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);

        assertEquals(3, fabrica.getQuantidadeMaquinas());
        assertEquals(4, fabrica.getDemandas().size());
        assertEquals(Cenario.IDEAL, fabrica.getCenario());
        assertSame(chocolate, fabrica.getMateriaPrima());
    }

    @Test
    void atualizarDemandaTest() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);

        fabrica.atualizarDemanda(TipoProduto.OVO_ARTESANAL, 5);

        Demanda demanda = buscarDemanda(fabrica, TipoProduto.OVO_ARTESANAL);

        assertEquals(5, demanda.getQuantidadeProdutos());
        assertEquals(StatusDemanda.PENDENTE, demanda.getStatus());
    }

    @Test
    void naoAtualizarDemandaComQuantidadeNegativa() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);
        Demanda demanda = buscarDemanda(fabrica, TipoProduto.OVO_ARTESANAL);

        assertThrows(RecursoInsuficienteException.class, () -> fabrica.atualizarDemanda(TipoProduto.OVO_ARTESANAL, -1));

        assertEquals(0, demanda.getQuantidadeProdutos());
        assertEquals(StatusDemanda.CONCLUIDA, demanda.getStatus());
    }

    @Test
    void comprarMateriaPrimaTest() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);

        double orcamentoInicial = fabrica.getBudget();
        float estoqueInicial = chocolate.getQuantidade();

        fabrica.comprarMateriaPrima(20.0f);

        assertEquals(estoqueInicial + 20.0f, chocolate.getQuantidade());
        assertEquals(orcamentoInicial - 1.0, fabrica.getBudget());
    }

    @Test
    void naoComprarMateriaPrimaSemOrcamentoSuficiente() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 100.0f, "g", 1000.0);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);

        assertThrows(CompraInvalidaException.class, () -> fabrica.comprarMateriaPrima(11.0f));
        assertEquals(100.0f, chocolate.getQuantidade());
        assertEquals(10000.0, fabrica.getBudget());
    }

    @Test
    void naoFabricarDemandaSemQuantidade() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);
        Demanda demanda = buscarDemanda(fabrica, TipoProduto.OVO_ARTESANAL);

        assertThrows(DemandaInvalidaException.class, () -> fabrica.fabricarDemanda(demanda));
    }

    @Test
    void naoFabricarDemandaSemMateriaPrimaSuficiente() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 0.0f, "g", 0.05);
        GerenciadorProducao fabrica = new GerenciadorProducao(chocolate, Cenario.IDEAL);

        fabrica.atualizarDemanda(TipoProduto.OVO_ARTESANAL, 1);

        Demanda demanda = buscarDemanda(fabrica, TipoProduto.OVO_ARTESANAL);

        assertThrows(RecursoInsuficienteException.class, () -> fabrica.fabricarDemanda(demanda));
        assertEquals(1, demanda.getQuantidadeProdutos());
        assertEquals(StatusDemanda.PENDENTE, demanda.getStatus());
        assertEquals(0.0f, chocolate.getQuantidade());
    }
}
