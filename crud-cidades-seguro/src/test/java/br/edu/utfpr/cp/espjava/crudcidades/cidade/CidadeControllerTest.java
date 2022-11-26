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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
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
    public void criarSemAutorizacao() throws Exception {
        
        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcde")
                    .param("estado", "ae"))
            .andExpect(status().isUnauthorized());

        mockMvc
            .perform(
                post("/criar")
                    .param("nome", "abcde")
                    .param("estado", "ae")
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "ttttt")))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "test123", roles = {"USER"})
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
    @WithMockUser(username = "user", password = "test123", roles = {"USER"})
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
    @WithMockUser(username = "user", password = "test123", roles = {"USER"})
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
    @WithMockUser(username = "user", password = "test123", roles = {"USER"})
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
    @WithMockUser(username = "user", password = "test123", roles = {"USER"})
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
    @WithMockUser(username = "user", password = "test123", roles = {"USER"})
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
    @WithMockUser(username = "user", password = "test123", roles = {"USER"})
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
    public void excluirSemAutorizacao() throws Exception {

        mockMvc
            .perform(
                get("/excluir")
                    .param("nome", "São Paulo")
                    .param("estado", "SP"))
            .andExpect(status().isUnauthorized());

        mockMvc
            .perform(
                get("/excluir")
                    .param("nome", "São Paulo")
                    .param("estado", "SP")
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "ttttt")))
            .andExpect(status().isUnauthorized());

        mockMvc
            .perform(
                get("/excluir")
                    .param("nome", "São Paulo")
                    .param("estado", "SP")
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "test123")))
            .andExpect(status().isForbidden());
            
    }

    @Test
    @WithMockUser(username = "admin", password = "test123", roles = {"ADMIN"})
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
