package br.com.alura.dojoplaces.controller;

import br.com.alura.dojoplaces.dto.LocalResponseDTO;
import br.com.alura.dojoplaces.entity.Local;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LocalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalController localController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(localController).build();
        mockMvc = MockMvcBuilders.standaloneSetup(localController)
                .build();
    }

    @Test
    @DisplayName("Should show the list of locals successfully")
    public void showList__should_show_list_of_locals_successfully() throws Exception {
        Local local1 = new Local();
        Local local2 = new Local();

        List<Local> locals = List.of(local1, local2);

        when(localRepository.findAll()).thenReturn(locals);

        LocalResponseDTO dto1 = local1.createLocalResponseDto();
        LocalResponseDTO dto2 = local2.createLocalResponseDto();

        List<LocalResponseDTO> localsDtos = List.of(dto1, dto2);

        mockMvc.perform(get("/form/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/listLocal"))
                .andExpect(model().attribute("locais", localsDtos));

        verify(localRepository).findAll();
    }

    @Test
    @DisplayName("Should show register local form successfully")
    public void showRegisterForm__should_show_register_form_successfully() throws Exception {
        mockMvc.perform(get("/form/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocalForm"))
                .andExpect(model().attributeExists("localCreateDTO"));
    }

    @Test
    @DisplayName("Should create a local successfully")
    public void submitForm__should_submit_create_dto_form_successfully() throws Exception {
        when(localRepository.existsByCode("123")).thenReturn(false);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/form/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "123")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/create"));

        verify(localRepository, times(1)).save(any(Local.class));
    }

    @Test
    @DisplayName("Should return an 404 error on trying to update a local that doesn't exist")
    public void editForm__should_return_local_not_found_error_on_update_form() throws Exception {
        Long localId = 1L;
        when(localRepository.findById(localId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/form/update/{localId}", localId))
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"));
    }

    @Test
    @DisplayName("Should edit a local successfully")
    public void editForm__should_edit_local_successfully() throws Exception {
        Long localId = 1L;

        when(localRepository.findById(localId)).thenReturn(Optional.of(new Local()));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/form/update/{localId}", localId)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "123")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/update/" + localId));

        verify(localRepository).save(any(Local.class));
    }

    @Test
    @DisplayName("Should delete a local successfully")
    public void deleteLocal__should_delete_a_local_successfully() throws Exception {
        Long localId = 1L;

        when(localRepository.existsById(localId)).thenReturn(true);

        mockMvc.perform(post("/form/delete")
                        .param("localId", String.valueOf(localId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/list"));

        verify(localRepository).deleteById(localId);
    }

}
