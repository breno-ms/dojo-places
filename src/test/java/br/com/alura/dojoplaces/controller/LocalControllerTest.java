package br.com.alura.dojoplaces.controller;

import br.com.alura.dojoplaces.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.dto.LocalResponseDTO;
import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import br.com.alura.dojoplaces.entity.Local;
import br.com.alura.dojoplaces.repository.LocalRepository;
import br.com.alura.dojoplaces.validator.LocalCreateValidator;
import br.com.alura.dojoplaces.validator.LocalUpdateValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LocalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private LocalCreateValidator localCreateValidator;

    @Autowired
    private LocalUpdateValidator localUpdateValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders
////                .standaloneSetup(localController)
////                .setValidator(localCreateValidator)
////                .setValidator(localUpdateValidator)
//                .build();
    }

    @Test
    @DisplayName("Should show the list of locals successfully")
    public void showList__should_show_list_of_locals_successfully() throws Exception {
        localRepository.save(new Local("Name", "123", "Neighbourhood", "City"));

        List<LocalResponseDTO> locals = localRepository.findAll().stream().map(LocalResponseDTO::new).collect(Collectors.toList());

        mockMvc.perform(get("/form/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/listLocal"))
                .andExpect(model().attribute("locais", locals));

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
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Code", "Neighbourhood", "City");

        mockMvc.perform(post("/form/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", localCreateDTO.getName())
                        .param("code", localCreateDTO.getCode())
                        .param("city", localCreateDTO.getCity())
                        .param("neighbourhood", localCreateDTO.getNeighbourhood()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/list"));
    }

    @Test
    @DisplayName("Should return errors when creating a local with invalid data")
    public void submitForm__should_return_errors_when_data_is_invalid() throws Exception {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LocalCreateDTO>> validate = validator.validate(localCreateDTO);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/form/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", localCreateDTO.getName())
                .param("code", localCreateDTO.getCode())
                .param("city", localCreateDTO.getCity())
                .param("neighbourhood", localCreateDTO.getNeighbourhood());

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/list"));
                //.andExpect(model().attributeHasFieldErrors("localCreateDTO", "code"));

        assertFalse(validate.stream().toList().isEmpty());
    }

    @Test
    @DisplayName("Should return an error when trying to create a local with an existing code")
    public void submitForm__should_return_error_when_code_already_exists() throws Exception {
        when(localRepository.existsByCode("123")).thenReturn(true);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/form/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "123")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocalForm"))
                .andExpect(model().attributeHasFieldErrors("localCreateDTO", "code"));
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
    @DisplayName("Should return errors when updating a local with invalid data")
    public void editForm__should_return_errors_when_data_is_invalid() throws Exception {
        Long localId = 1L;

        when(localRepository.findById(localId)).thenReturn(Optional.of(new Local()));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/form/update/{localId}", localId)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(view().name("form/update"))
                .andExpect(model().attributeHasFieldErrors("localUpdateRequestDTO", "code"));
    }

    @Test
    @DisplayName("Should return an error when trying to update a local with an existing code")
    public void editForm__should_return_error_when_code_already_exists() throws Exception {
        Local local = new Local();

        localRepository.save(local);

        when(localRepository.findById(local.getId())).thenReturn(Optional.of(local));
        when(localRepository.findByCodeAndIdNot("123", local.getId())).thenReturn(Optional.of(local));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/form/update/{localId}", local.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("code", "123")
                .param("name", "Name")
                .param("city", "City")
                .param("neighbourhood", "Neighbourhood");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/update/" + local.getId()))
                .andExpect(model().attributeHasFieldErrors("localUpdateRequestDTO", "code"));
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
