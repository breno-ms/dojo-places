package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import br.com.alura.dojoplaces.dto.LocalUpdateResponseDTO;
import br.com.alura.dojoplaces.repository.LocalRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LocalUpdateValidator implements Validator {

    private final LocalRepository localRepository;

    public LocalUpdateValidator(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LocalUpdateResponseDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        LocalUpdateRequestDTO localUpdateRequestDTO = (LocalUpdateRequestDTO) target;

        if (!localRepository.existsByCode(localUpdateRequestDTO.getCode())) {
            errors.rejectValue("code", "error.local.does.not.exist", "Não existe um local com este código");
        }
    }

}
