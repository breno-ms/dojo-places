package br.com.alura.dojoplaces.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalCreateDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @CsvSource({
            "valid",
            "validCode",
            "validCode123",
            "123456"
    })
    public void code__should_not_have_validation_errors(String code) {
        LocalCreateDTO dto = new LocalCreateDTO("Name", code, "Neighbourhood", "City", "123");

        Set<ConstraintViolation<LocalCreateDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "The code validation should pass for code: " + code);
    }

    @ParameterizedTest
    @CsvSource({
            "Invalid@Code",
            "Another_Invalid_Code",
            "CodeWithSpaces 123",
            "Special!Characters@Code",
    })
    public void code__should_have_validation_errors_when_code_has_invalid_characters(String code) {
        LocalCreateDTO dto = new LocalCreateDTO("Name", code, "Neighbourhood", "City", "123");

        Set<ConstraintViolation<LocalCreateDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "The code validation should fail for code: " + code);
    }

}
