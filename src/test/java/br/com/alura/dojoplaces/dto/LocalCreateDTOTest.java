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
        verify(localRepository, times(1)).existsByCode(localCreateDTO.getCode());

        // TODO: verificar se os errors do validador foram encontrados no binding result:
        verify(localRepository, times(1)).existsByCode(localCreateDTO.getCode());
        verify(bindingResult, times(0)).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    public void localCreateForm__when_local_already_exists_by_code() {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Code", "Neighbourhood", "City");
        BindingResult bindingResult = new BeanPropertyBindingResult(localCreateDTO, "localCreateDTO");

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(true);
        localCreateValidator.validate(localCreateDTO, bindingResult);

        assertEquals(1, bindingResult.getErrorCount(), "Should have one validation error");
        assertEquals("error.local.already.exists",
                Objects.requireNonNull(bindingResult.getFieldError("code")).getCode(), "Error code should match");
        assertEquals("Já existe um local com este código",
                Objects.requireNonNull(bindingResult.getFieldError("code")).getDefaultMessage(), "Error message should match");
        verify(localRepository, times(1)).existsByCode(localCreateDTO.getCode());

        // TODO: verificar se os errors do validador foram encontrados no binding result:
        verify(bindingResult, times(1)).rejectValue(eq("code"), eq("error.local.already.exists"), eq("Já existe um local com este código"));
    }

}
