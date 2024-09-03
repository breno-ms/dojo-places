package br.com.alura.dojoplaces.validator;

import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
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
        return LocalUpdateRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        LocalUpdateRequestDTO localUpdateRequestDTO = (LocalUpdateRequestDTO) target;
        String code = localUpdateRequestDTO.getCode();
        Long id = localUpdateRequestDTO.getId();

        if (localRepository.findByCodeAndIdNot(code, id).isPresent()) {
            errors.rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
        }
    }

}
