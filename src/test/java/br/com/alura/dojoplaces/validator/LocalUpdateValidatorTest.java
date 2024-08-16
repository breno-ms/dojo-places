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

import static org.assertj.core.api.Assertions.assertThat;
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
    void validate__no_errors() {
        String validCode = "123456";

        LocalUpdateRequestDTO localUpdateDTO = new LocalUpdateRequestDTO();
        localUpdateDTO.setCode(validCode);

        when(localRepository.existsByCode(localUpdateDTO.getCode())).thenReturn(true);

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(localRepository, times(1)).existsByCode(localUpdateDTO.getCode());
    }

    @Test
    void validate__local_does_not_exist() {
        String invalidCode = "123456";

        LocalUpdateRequestDTO localUpdateDTO = new LocalUpdateRequestDTO();
        localUpdateDTO.setCode(invalidCode);

        when(localRepository.existsByCode(localUpdateDTO.getCode())).thenReturn(false);

        localUpdateValidator.validate(localUpdateDTO, errors);

        verify(errors).rejectValue("code", "error.local.does.not.exist", "Não existe um local com este código");
    }

    @Test
    void updateLocal__success() {
        String existingCode = "123456";

        LocalUpdateRequestDTO localUpdateRequestDTO = new LocalUpdateRequestDTO();
        localUpdateRequestDTO.setCode(existingCode);
        localUpdateRequestDTO.setName("Updated Name");

        Local existingLocal = new Local();
        existingLocal.setCode(existingCode);
        existingLocal.setName("Old Name");

        when(localRepository.findByCode(existingCode)).thenReturn(Optional.of(existingLocal));

        when(localRepository.save(any(Local.class))).thenAnswer(invocation -> {
            Local updatedLocal = invocation.getArgument(0);
            existingLocal.setName(updatedLocal.getName());
            return existingLocal;
        });

        Local localToUpdate = localRepository.findByCode(existingCode).orElseThrow();
        localToUpdate.setName(localUpdateRequestDTO.getName());
        localRepository.save(localToUpdate);

        assertThat(localToUpdate.getName()).isEqualTo("Updated Name");

        verify(localRepository).save(argThat(local ->
                local.getCode().equals(existingCode) &&
                        local.getName().equals("Updated Name")
        ));
    }

}
