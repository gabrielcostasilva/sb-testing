package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    @BeforeAll
    public void setup() {
        CidadeEntidade cidade = new CidadeEntidade();
        cidade.setNome("São Paulo");
        cidade.setEstado("SP");
        cidadeRepository.save(cidade);
    }

    @Test
    @DisplayName("Checks whether the method returns a non null value.")
    public void testFindByNomeAndEstadoReturnsNonNullObject() {
        var cidade = cidadeRepository.findByNomeAndEstado("São Paulo", "SP");
        assertNotNull(cidade);
    }
    
    @Test
    @DisplayName("Checks whether the method returns an object in the Optional result.")
    public void testFindByNomeAndEstadoReturnsValueForOptionalObject() {
        
        var cidade = cidadeRepository.findByNomeAndEstado("São Paulo", "SP");
        
        var cidadeValues = cidade.orElseThrow();
        assertNotNull(cidadeValues);
    }
    
    @Test
    @DisplayName("Checks whether the method returns valid values for attributes.")
    public void testFindByNomeAndEstadoValidValuesForAttributes() {

        var cidade = cidadeRepository.findByNomeAndEstado("São Paulo", "SP");
        var cidadeValues = cidade.orElseThrow();

        assertEquals("São Paulo", cidadeValues.getNome());
        assertEquals("SP", cidadeValues.getEstado());
        assertNotNull(cidadeValues.getId());
    }
}
