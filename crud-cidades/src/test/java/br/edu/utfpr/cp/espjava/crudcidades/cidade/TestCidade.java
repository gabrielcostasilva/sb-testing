package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class TestCidade {

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void testNewInstance() {
        var cidade = new Cidade("São Paulo", "SP");
        assertNotNull(cidade);
    }

    @Test
    public void testClonar() {

        var cidade = new Cidade("São Paulo", "SP");
        var cidadeEntidade = cidade.clonar();
        
        assertNotNull(cidadeEntidade);
        
        var cidadePersisted = entityManager.persist(cidadeEntidade);
        assertNotNull(cidadePersisted.getId());
    }
}
