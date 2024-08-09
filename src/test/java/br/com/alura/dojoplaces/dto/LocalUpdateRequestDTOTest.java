package br.com.alura.dojoplaces.dto;

import br.com.alura.dojoplaces.validator.LocalUpdateValidator;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LocalUpdateRequestDTOTest {

    private LocalUpdateValidator localUpdateValidator;

    @BeforeEach
    void setUp() {
        localUpdateValidator = new LocalUpdateValidator();
    }

    @DisplayName("Should contain errors if the local name has invalid characters")
    @ParameterizedTest
    @ValueSource(strings = {
            "{Local name}",
    })
    void should_contain_errors_if_name_has_invalid_characters(String localName) {
        LocalUpdateRequestDTO form = new LocalUpdateRequestDTO();
        form.setName(localName);

        Errors errors = new BeanPropertyBindingResult(form, "form");

        localUpdateValidator.validate(form, errors);

        assertThat(errors.hasFieldErrors("code")).isTrue();
        assertThat(Objects.requireNonNull(errors.getFieldError("name")).getDefaultMessage())
                .isEqualTo("Code must not contain special characters or spaces.");
    }

}
