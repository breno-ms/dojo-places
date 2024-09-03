package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
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

    // TODO: adicionar display names

    @Test
    void validate__should_pass_validation_when_code_is_unique() {
        String uniqueCode = "123456";

        LocalCreateDTO localCreateDTO = new LocalCreateDTO();
        localCreateDTO.setCode(uniqueCode);

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(false);

        localCreateValidator.validate(localCreateDTO, errors);

        verify(errors, never()).rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
    }

    @Test
    void validate__should_reject_validation_when_code_already_exists() {
        String duplicateCode = "123456";

        LocalCreateDTO localCreateDTO = new LocalCreateDTO();
        localCreateDTO.setCode(duplicateCode);

        when(localRepository.existsByCode(localCreateDTO.getCode())).thenReturn(true);

        localCreateValidator.validate(localCreateDTO, errors);

        verify(errors).rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
    }

}
