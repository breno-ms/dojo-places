package br.com.alura.dojoplaces.dto;

import br.com.alura.dojoplaces.repository.LocalRepository;
import br.com.alura.dojoplaces.validator.LocalUpdateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class LocalUpdateRequestDTOTest {

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalUpdateValidator localUpdateValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void localUpdateForm__when_local_does_not_exist_by_code() {
        LocalUpdateRequestDTO localUpdateRequestDTO = new LocalUpdateRequestDTO("Name", "Code", "Neighbourhood", "City");
        BindingResult bindingResult = new BeanPropertyBindingResult(localUpdateRequestDTO, "localUpdateRequestDTO");

        when(localRepository.existsByCode(localUpdateRequestDTO.getCode())).thenReturn(false);

        localUpdateValidator.validate(localUpdateRequestDTO, bindingResult);

        assertEquals(1, bindingResult.getErrorCount(), "Should have one validation error");
        assertEquals("error.local.does.not.exist", Objects.requireNonNull(bindingResult.getFieldError("code")).getCode(), "Error code should match");
        assertEquals("Não existe um local com este código", Objects.requireNonNull(bindingResult.getFieldError("code")).getDefaultMessage(), "Error message should match");
        verify(localRepository, times(1)).existsByCode(localUpdateRequestDTO.getCode());
    }

    @Test
    public void localUpdateForm__when_local_does_exist_by_code() {
        LocalUpdateRequestDTO localUpdateRequestDTO = new LocalUpdateRequestDTO("Name", "Code", "Neighbourhood", "City");
        BindingResult bindingResult = new BeanPropertyBindingResult(localUpdateRequestDTO, "localUpdateRequestDTO");

        when(localRepository.existsByCode(localUpdateRequestDTO.getCode())).thenReturn(true);

        localUpdateValidator.validate(localUpdateRequestDTO, bindingResult);

        assertFalse(bindingResult.hasErrors(), "Should not have any validation errors");
        verify(localRepository, times(1)).existsByCode(localUpdateRequestDTO.getCode());
    }

}
