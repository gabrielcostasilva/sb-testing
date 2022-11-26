package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class CidadeControllerTest {
    
    @MockBean
    private CidadeRepository cidadeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listar() throws Exception {

        CidadeEntidade cidadeEntidade = new CidadeEntidade();
        cidadeEntidade.setId(1L);
        cidadeEntidade.setNome("São Paulo");
        cidadeEntidade.setEstado("SP");

        Mockito
            .when(cidadeRepository.findAll())
            .thenReturn(List.of(cidadeEntidade));

        mockMvc
            .perform(get("/"))
            .andExpect(view().name("/crud"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/HTML;charset=UTF-8"))
            .andExpect(model().attributeExists("listaCidades"));
    }

    @Test
    public void criar() throws Exception {
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcde")
                    .param("estado", "ae"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    public void criarInvalidAttributeNomeSizeMin() throws Exception {
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcd")
                    .param("estado", "PR"))
            .andExpect(status().isOk())
            .andExpect(view().name("/crud"))
            .andExpect(model().attributeHasFieldErrors("cidade", "nome"))
            .andExpect(model().attributeExists("nomeInformado"));
    }

    @Test
    public void criarInvalidAttributeNomeSizeMax() throws Exception {
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdef")
                    .param("estado", "PR"))
            .andExpect(status().isOk())
            .andExpect(view().name("/crud"))
            .andExpect(model().attributeHasFieldErrors("cidade", "nome"))
            .andExpect(model().attributeExists("nomeInformado"));
    }

    @Test
    public void criarInvalidAttributeNomeBlank() throws Exception {
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "")
                    .param("estado", "PR"))
            .andExpect(status().isOk())
            .andExpect(view().name("/crud"))
            .andExpect(model().attributeHasFieldErrors("cidade", "nome"))
            .andExpect(model().attributeExists("nomeInformado"));
    }

    @Test
    public void criarInvalidAttributeEstadoSizeMax() throws Exception {
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcde")
                    .param("estado", "PRR"))
            .andExpect(status().isOk())
            .andExpect(view().name("/crud"))
            .andExpect(model().attributeHasFieldErrors("cidade", "estado"))
            .andExpect(model().attributeExists("estadoInformado"));
    }

    @Test
    public void criarInvalidAttributeEstadoSizeMin() throws Exception {
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcde")
                    .param("estado", "P"))
            .andExpect(status().isOk())
            .andExpect(view().name("/crud"))
            .andExpect(model().attributeHasFieldErrors("cidade", "estado"))
            .andExpect(model().attributeExists("estadoInformado"));
    }    

    @Test
    public void criarInvalidAttributeEstadoBlank() throws Exception {
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcde")
                    .param("estado", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("/crud"))
            .andExpect(model().attributeHasFieldErrors("cidade", "estado"))
            .andExpect(model().attributeExists("estadoInformado"));
    }
    
    @Test
    public void excluir() throws Exception {

        mockMvc
            .perform(
                get("/excluir")
                    .param("nome", "São Paulo")
                    .param("estado", "SP"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));

    }

    public void preparaAlterar() {

    }

    public void alterar() {
        
    }
}
