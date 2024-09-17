package br.com.alura.dojoplaces.controller;

import br.com.alura.dojoplaces.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.dto.LocalResponseDTO;
import br.com.alura.dojoplaces.entity.Local;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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

    @AfterEach
    public void tearDown() {
        localRepository.deleteAll();
    }

    @Test
    @DisplayName("Should show the list of locals successfully")
    public void showList__should_show_a_list_of_all_locals_successfully() throws Exception {
        Local local = new Local("Name", "123", "Neighbourhood", "City");
        localRepository.save(local);

        List<LocalResponseDTO> locals = localRepository.findAll().stream().map(LocalResponseDTO::new).collect(Collectors.toList());

        mockMvc.perform(get("/form/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/listLocal"))
                .andExpect(model().attribute("locals", locals));
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
    public void submitForm__should_create_a_local_successfully() throws Exception {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Code", "Neighbourhood", "City", "123");

        mockMvc.perform(post("/form/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", localCreateDTO.getName())
                        .param("code", localCreateDTO.getCode())
                        .param("city", localCreateDTO.getCity())
                        .param("neighbourhood", localCreateDTO.getNeighbourhood())
                        .param("cep", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/list"));
    }

    @Test
    @DisplayName("Should return errors when creating a local with invalid data")
    public void submitForm__should_have_errors_when_data_is_invalid() throws Exception {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("", "", "", "", "");

        mockMvc.perform(post("/form/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", localCreateDTO.getName())
                        .param("code", localCreateDTO.getCode())
                        .param("city", localCreateDTO.getCity())
                        .param("neighbourhood", localCreateDTO.getNeighbourhood()))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocalForm"))
                .andExpect(model().attributeHasFieldErrors("localCreateDTO", "name", "code", "city", "neighbourhood"));
    }

    @Test
    @DisplayName("Should return to register form when trying to create a local with an existing code")
    public void submitForm__should_return_to_register_form_when_trying_to_create_a_local_with_an_existing_code() throws Exception {
        Local existingLocal = new Local("Existing Name", "123", "Existing Neighbourhood", "Existing City");
        localRepository.save(existingLocal);

        mockMvc.perform(post("/form/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("code", "123")
                        .param("name", "Name")
                        .param("city", "City")
                        .param("neighbourhood", "Neighbourhood")
                        .param("cep", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/registerLocalForm"))
                .andExpect(model().attributeHasFieldErrors("localCreateDTO", "code"));
    }

    @Test
    @DisplayName("Should return a 404 error on trying to update a local that doesn't exist")
    public void editForm__should_return_local_not_found_error_when_trying_to_update_a_local_that_doesnt_exist() throws Exception {
        Long localId = 1L;

        mockMvc.perform(get("/form/update/{localId}", localId))
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"));
    }

    @Test
    @DisplayName("Should edit a local successfully")
    public void editForm__should_edit_local_successfully() throws Exception {
        Local local = new Local("Name", "123", "Neighbourhood", "City");
        localRepository.save(local);

        Long localId = local.getId();

        mockMvc.perform(post("/form/update/{localId}", localId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("code", "456")
                        .param("name", "Updated Name")
                        .param("city", "Updated City")
                        .param("neighbourhood", "Updated Neighbourhood")
                        .param("cep", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/list"));
    }

    @Test
    @DisplayName("Should return errors when updating a local with invalid data")
    public void editForm__should_have_errors_when_trying_to_edit_a_local_with_invalid_data() throws Exception {
        Local local = new Local("Name", "123", "Neighbourhood", "City");
        localRepository.save(local);

        Long localId = local.getId();

        mockMvc.perform(post("/form/update/{localId}", localId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("code", "")
                        .param("name", "")
                        .param("city", "")
                        .param("neighbourhood", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/updateLocalForm"))
                .andExpect(model().attributeHasFieldErrors("localUpdateRequestDTO", "code", "name", "city", "neighbourhood"));
    }

    @Test
    @DisplayName("Should return an error when trying to update a local with an existing code")
    public void editForm__should_have_error_when_trying_to_update_a_local_with_an_existing_code() throws Exception {
        Local existingLocal = new Local("Existing Name", "123", "Existing Neighbourhood", "Existing City");
        localRepository.save(existingLocal);

        Local localToUpdate = new Local("Local to Update", "1234", "Neighbourhood to Update", "City to Update");
        localRepository.save(localToUpdate);

        mockMvc.perform(post("/form/update/{localId}", localToUpdate.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", localToUpdate.getId().toString())
                        .param("code", "123")
                        .param("name", "Updated Name")
                        .param("city", "Updated City")
                        .param("neighbourhood", "Updated Neighbourhood")
                        .param("cep", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("/local/updateLocalForm"))
                .andExpect(model().attributeHasFieldErrors("localUpdateRequestDTO", "code"));
    }

    @Test
    @DisplayName("Should delete a local successfully")
    public void deleteLocal__should_delete_a_local_successfully() throws Exception {
        Local local = new Local("Name", "123", "Neighbourhood", "City");
        localRepository.save(local);

        Long localId = local.getId();

        mockMvc.perform(post("/form/delete")
                        .param("localId", String.valueOf(localId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form/list"));

        assertThat(localRepository.findById(localId)).isEmpty();
    }

}
