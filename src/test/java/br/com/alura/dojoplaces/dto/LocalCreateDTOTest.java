package br.com.alura.dojoplaces.dto;

import br.com.alura.dojoplaces.repository.LocalRepository;
import br.com.alura.dojoplaces.validator.LocalCreateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalCreateDTOTest {

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalCreateValidator localCreateValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void localCreateForm__when_local_does_not_exist_by_code() {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Code", "Neighbourhood", "City");
        BindingResult bindingResult = new BeanPropertyBindingResult(localCreateDTO, "localCreateDTO");

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(false);

        localCreateValidator.validate(localCreateDTO, bindingResult);

        assertFalse(bindingResult.hasErrors(), "Should not have any validation errors");
    }

    @Test
    public void localCreateForm__when_local_already_exists_by_code() {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Code", "Neighbourhood", "City");
        BindingResult bindingResult = new BeanPropertyBindingResult(localCreateDTO, "localCreateDTO");

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(true);

        localCreateValidator.validate(localCreateDTO, bindingResult);

        assertTrue(bindingResult.hasErrors(), "Should have validation errors");
        assertEquals("Já existe um local com este código", Objects.requireNonNull(bindingResult.getFieldError("code")).getDefaultMessage());
    }

    @Test
    public void localCreateForm__when_code_contains_special_characters() {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Invalid@Code!", "Neighbourhood", "City");
        BindingResult bindingResult = new BeanPropertyBindingResult(localCreateDTO, "localCreateDTO");

        localCreateValidator.validate(localCreateDTO, bindingResult);

        assertTrue(bindingResult.hasErrors(), "Should have validation errors");
        assertEquals("Code must not contain special characters or spaces.", Objects.requireNonNull(bindingResult.getFieldError("code")).getDefaultMessage());
    }

    @Test
    public void localCreateForm__when_code_is_too_long() {
        String longCode = "A".repeat(101);
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", longCode, "Neighbourhood", "City");
        BindingResult bindingResult = new BeanPropertyBindingResult(localCreateDTO, "localCreateDTO");

        localCreateValidator.validate(localCreateDTO, bindingResult);

        assertTrue(bindingResult.hasErrors(), "Should have validation errors");
        assertEquals("Code must have a maximum of 100 characters.", Objects.requireNonNull(bindingResult.getFieldError("code")).getDefaultMessage());
    }

}
