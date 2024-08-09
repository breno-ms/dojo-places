package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LocalUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        LocalUpdateRequestDTO localUpdateRequestDTO = (LocalUpdateRequestDTO) target;

        // todo: continuar validações
    }

}
