package br.com.alura.dojoplaces.dto;

import br.com.alura.dojoplaces.entity.Local;
import br.com.alura.dojoplaces.repository.LocalRepository;
import br.com.alura.dojoplaces.validator.LocalUpdateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Should not have validation errors when local does not exist by code")
    public void localUpdateForm__when_local_does_not_exist_by_code() {
        LocalUpdateRequestDTO localUpdateRequestDTO = new LocalUpdateRequestDTO(
                "Name",
                "Code",
                "Neighbourhood",
                "City");

        BindingResult bindingResult = new BeanPropertyBindingResult(localUpdateRequestDTO, "localUpdateRequestDTO");

        when(localRepository.findByCodeAndIdNot(localUpdateRequestDTO.getCode(), localUpdateRequestDTO.getId())).thenReturn(Optional.empty());

        localUpdateValidator.validate(localUpdateRequestDTO, bindingResult);

        assertFalse(bindingResult.hasErrors(), "Should not have validation errors");

        verify(localRepository, times(1)).findByCodeAndIdNot(localUpdateRequestDTO.getCode(), localUpdateRequestDTO.getId());
    }

    @Test
    @DisplayName("Should have validation errors when local does exist by code")
    public void localUpdateForm__when_local_does_exist_by_code() {
        LocalUpdateRequestDTO localUpdateRequestDTO = new LocalUpdateRequestDTO(
                "Name",
                "Code",
                "Neighbourhood",
                "City");

        BindingResult bindingResult = new BeanPropertyBindingResult(localUpdateRequestDTO, "localUpdateRequestDTO");

        when(localRepository.findByCodeAndIdNot(localUpdateRequestDTO.getCode(), localUpdateRequestDTO.getId())).thenReturn(Optional.of(new Local()));

        localUpdateValidator.validate(localUpdateRequestDTO, bindingResult);

        assertTrue(bindingResult.hasErrors(), "Should have validation errors");
        assertEquals("Já existe um local com este código", bindingResult.getFieldError("code").getDefaultMessage());

        verify(localRepository, times(1)).findByCodeAndIdNot(localUpdateRequestDTO.getCode(), localUpdateRequestDTO.getId());
    }

}
