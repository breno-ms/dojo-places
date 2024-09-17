package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

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

    @Test
    @DisplayName("Should not have validation errors when the local code is unique, valid and belongs to the record that we are updating")
    void validate__should_not_have_validation_errors_when_code_is_unique_or_belongs_to_the_same_record() {
        LocalUpdateRequestDTO localUpdateDTO = new LocalUpdateRequestDTO("Name", "Code", "Neighbourhood", "City", "123");

        when(localRepository.existsByCodeAndIdNot(localUpdateDTO.getCode(), localUpdateDTO.getId())).thenReturn(false);

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Should have validation errors when the local code already exists for a different record")
    void validate__should_have_validation_errors_when_code_already_exists_for_different_record() {
        LocalUpdateRequestDTO localUpdateDTO = new LocalUpdateRequestDTO("Name", "Code", "Neighbourhood", "City", "123");

        when(localRepository.existsByCodeAndIdNot(localUpdateDTO.getCode(), localUpdateDTO.getId())).thenReturn(true);

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(errors).rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
    }

}
