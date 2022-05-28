package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class CidadeEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testNewInstance() {
        var cidade = new CidadeEntidade();
        assertNotNull(cidade);
    }

    @Test
    public void testValidEntity() {
        var cidade = new CidadeEntidade();
        cidade.setNome("São Paulo");
        cidade.setEstado("SP");
        
        var cidadePersisted = entityManager.persist(cidade);
        assertEquals("São Paulo", cidadePersisted.getNome());
        assertEquals("SP", cidadePersisted.getEstado());
        assertNotNull(cidadePersisted.getId());
    }

    
}