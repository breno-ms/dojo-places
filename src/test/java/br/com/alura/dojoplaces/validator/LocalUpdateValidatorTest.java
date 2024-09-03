package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import br.com.alura.dojoplaces.entity.Local;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.mockito.Mockito.*;

class LocalUpdateValidatorTest {

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalUpdateValidator localUpdateValidator;

    private Errors errors;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        errors = mock(Errors.class);
    }

    // TODO: adicionar display names

    @Test
    void validate__should_pass_validation_when_code_is_unique_or_same_record() {
        String code = "123456";
        Long updateId = 1L;

        LocalUpdateRequestDTO localUpdateDTO = new LocalUpdateRequestDTO();
        localUpdateDTO.setCode(code);
        localUpdateDTO.setId(updateId);

        when(localRepository.findByCodeAndIdNot(code, updateId)).thenReturn(Optional.empty());

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void validate__should_reject_validation_when_code_already_exists_for_different_record() {
        String code = "123456";
        Long updateId = 1L;
        Long existingId = 2L;

        LocalUpdateRequestDTO localUpdateDTO = new LocalUpdateRequestDTO();
        localUpdateDTO.setCode(code);
        localUpdateDTO.setId(updateId);

        Local existingLocal = new Local();
        existingLocal.setCode(code);

        when(localRepository.findByCodeAndIdNot(code, updateId)).thenReturn(Optional.of(existingLocal));

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(errors).rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
    }

}
