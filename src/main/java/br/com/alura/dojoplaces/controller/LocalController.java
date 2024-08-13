package br.com.alura.dojoplaces.controller;

import br.com.alura.dojoplaces.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import br.com.alura.dojoplaces.entity.Local;
import br.com.alura.dojoplaces.exception.NotFoundException;
import br.com.alura.dojoplaces.repository.LocalRepository;
import br.com.alura.dojoplaces.validator.LocalCreateValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/form")
public class LocalController {

    private final LocalRepository localRepository;

    public LocalController(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @GetMapping("/create")
    public String showRegisterForm(LocalCreateDTO form) {
        if (!form.isDirty()) {
            form = new LocalCreateDTO();
        }

        return "/local/registerLocalForm";
    }

    @GetMapping("/update/{localId}")
    public String showUpdateForm(@PathVariable Long localId, LocalUpdateRequestDTO form, Model model) {
        Optional<Local> existingLocal = localRepository.findById(localId);

        if (existingLocal.isEmpty()) {
            return "/error/404";
        }

        if (!form.isDirty()) {
            form = existingLocal.get().createLocalUpdateRequestDto();
        }

        model.addAttribute("localId", localId);
        model.addAttribute("localUpdateDTO", form);
        return "/local/updateLocalForm";
    }

    @PostMapping("/create")
    public String submitForm(@Valid LocalCreateDTO localCreateDTO, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            localCreateDTO.markAsDirty();
            return showRegisterForm(localCreateDTO);
        }

        boolean localAlreadyExists = localRepository.existsByCode(localCreateDTO.getCode());

        if (localAlreadyExists) {
            bindResult.rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
            localCreateDTO.markAsDirty();
            return showRegisterForm(localCreateDTO);
        }

        Local local = localCreateDTO.createLocalFromDTO();
        localRepository.save(local);

        return "redirect:/form/create";
    }

    @PostMapping("/update/{localId}")
    public String editForm(@Valid LocalUpdateRequestDTO localUpdateDTO, BindingResult bindingResult, @PathVariable Long localId, Model model) {
        if (bindingResult.hasErrors()) {
            localUpdateDTO.markAsDirty();
            return showUpdateForm(localId, localUpdateDTO, model);
        }

        Local existingLocal = localRepository.findById(localId).orElseThrow(NotFoundException::new);
        existingLocal.updateFromDTO(localUpdateDTO);
        localRepository.save(existingLocal);

        return "redirect:/form/update/" + localId;
    }

}
