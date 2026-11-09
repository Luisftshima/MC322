package br.unicamp.ic.mc322.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.unicamp.ic.mc322.exception.RecursoInsuficienteException;

public class MateriaPrimaTest {
    @Test
    public void criarMateriaPrimaTest() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);

        assertTrue(chocolate.getId() > 0);
        assertEquals("Chocolate", chocolate.getNome());
        assertEquals(2000.0f, chocolate.getQuantidade());
        assertEquals("g", chocolate.getUnidade());
        assertEquals(0.05, chocolate.getCustoPorUnidade());
    }

    @Test
    public void naoCriarMateriaPrimaSemNome() {
        assertThrows(IllegalArgumentException.class, () -> new MateriaPrima("", 2000.0f, "g", 0.05));
        assertThrows(IllegalArgumentException.class, () -> new MateriaPrima(null, 2000.0f, "g", 0.05));
    }

    @Test
    public void naoCriarMateriaPrimaSemUnidade() {
        assertThrows(IllegalArgumentException.class, () -> new MateriaPrima("Chocolate", 2000.0f, "", 0.05));
        assertThrows(IllegalArgumentException.class, () -> new MateriaPrima("Chocolate", 2000.0f, null, 0.05));
    }

    @Test 
    public void naoCriarMateriaPrimacomQuantidadeNegativa(){
        assertThrows(IllegalArgumentException.class, () -> new MateriaPrima("Chocolate", -1, "g", 0.05));
    }
    
    @Test 
    public void naoCriarMateriaPrimacomCustoNegativo(){
        assertThrows(IllegalArgumentException.class, () -> new MateriaPrima("Chocolate", 2000.0f, "g", -1));
    }

    @Test
    public void consumirEstoqueTest() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        chocolate.consumir(1000);

        assertEquals(1000.0f, chocolate.getQuantidade());   
    }

    @Test
    public void naoConsumirMaisDoQueTemNoEstoque() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 1000.0f, "g", 0.05);
        
        assertThrows(RecursoInsuficienteException.class, () -> chocolate.consumir(2000));
        assertEquals(1000.0f, chocolate.getQuantidade());
    }
    
    @Test
    public void adicionarEstoqueTest() {
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);
        chocolate.adicionarEstoque(1000);

        assertEquals(3000.0f, chocolate.getQuantidade());
    }

    @Test
    public void verificarDisponibilidadeTest(){
        MateriaPrima chocolate = new MateriaPrima("Chocolate", 2000.0f, "g", 0.05);

        assertTrue(chocolate.verificarDisponibilidade(1000));
        assertTrue(chocolate.verificarDisponibilidade(2000));
        assertFalse(chocolate.verificarDisponibilidade(2001));
    }
}
