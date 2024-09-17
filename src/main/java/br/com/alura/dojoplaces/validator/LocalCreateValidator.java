package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LocalCreateValidator implements Validator {

    private final LocalRepository localRepository;

    public LocalCreateValidator(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LocalCreateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        LocalCreateDTO localCreateDTO = (LocalCreateDTO) target;

        if (localRepository.existsByCode(localCreateDTO.getCode())) {
            errors.rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
        }
    }

}
