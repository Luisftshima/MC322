package br.unicamp.ic.mc322.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.unicamp.ic.mc322.exception.RecursoInsuficienteException;

public class DemandaTest {
    @Test
    public void criarDemandaTest(){
        Demanda demanda = new Demanda(TipoProduto.OVO_ARTESANAL, 5);
        
        assertEquals(TipoProduto.OVO_ARTESANAL, demanda.getTipoProduto());
        assertEquals(5, demanda.getQuantidadeProdutos());
        assertEquals(StatusDemanda.CONCLUIDA, demanda.getStatus());
    }

    @Test
    void atualizarQuantidadeTest() {
        Demanda demanda = new Demanda(TipoProduto.OVO_ARTESANAL, 5);

        demanda.atualizarQuantidade(5);
        assertEquals(10, demanda.getQuantidadeProdutos());

        demanda.atualizarQuantidade(-3);
        assertEquals(7, demanda.getQuantidadeProdutos());
    }

    @Test
    void naoPermitirQuantidadeNegativa() {
        Demanda demanda = new Demanda(TipoProduto.OVO_ARTESANAL, 5);

        assertThrows(RecursoInsuficienteException.class, () -> demanda.atualizarQuantidade(-6));
        assertEquals(5, demanda.getQuantidadeProdutos());
    }

    @Test
    void alterarStatusCorretamente() {
        Demanda demanda = new Demanda(TipoProduto.CHOCOTONE, 5);

        demanda.pendente();
        assertEquals(StatusDemanda.PENDENTE, demanda.getStatus());

        demanda.emProducao();
        assertEquals(StatusDemanda.EM_PRODUCAO, demanda.getStatus());

        demanda.atender();
        assertEquals(StatusDemanda.CONCLUIDA, demanda.getStatus());
    }

    @Test
    void naoConcluirDemandaCancelada() {
        Demanda demanda = new Demanda(TipoProduto.BOMBONS_SORTIDOS, 5);

        demanda.cancelar();
        assertEquals(StatusDemanda.CANCELADA, demanda.getStatus());

        assertThrows(IllegalStateException.class, () -> demanda.atender());

        assertEquals(StatusDemanda.CANCELADA, demanda.getStatus());
    }

    @Test
    void calcularCustoEstimadoTest() {
        Demanda demanda = new Demanda( TipoProduto.OVO_ARTESANAL, 5);

        assertEquals(23.75, demanda.calcularCustoEstimado());
    }
}
