package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

class LocalCreateValidatorTest {

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalCreateValidator localCreateValidator;

    private Errors errors;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        errors = mock(Errors.class);
    }

    @Test
    @DisplayName("Should not have validation errors when the local code is unique and valid")
    void validate__should_not_have_validation_errors_when_local_code_is_unique_and_valid() {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Code", "Neighbourhood", "City", "123");

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(false);

        localCreateValidator.validate(localCreateDTO, errors);

        verify(errors, never()).rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
    }

    @Test
    @DisplayName("Should have validation errors when the local code already exists")
    void validate__should_have_local_already_exists_error_when_local_code_already_exists() {
        LocalCreateDTO localCreateDTO = new LocalCreateDTO("Name", "Code", "Neighbourhood", "City", "123");

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(true);

        localCreateValidator.validate(localCreateDTO, errors);

        verify(errors).rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
    }

}
