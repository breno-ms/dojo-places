package br.com.alura.dojoplaces.controller;

import br.com.alura.dojoplaces.dto.LocalCreateDTO;
import br.com.alura.dojoplaces.dto.LocalResponseDTO;
import br.com.alura.dojoplaces.dto.LocalUpdateRequestDTO;
import br.com.alura.dojoplaces.entity.Local;
import br.com.alura.dojoplaces.exception.NotFoundException;
import br.com.alura.dojoplaces.repository.LocalRepository;
import br.com.alura.dojoplaces.validator.LocalCreateValidator;
import br.com.alura.dojoplaces.validator.LocalUpdateValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/form")
public class LocalController {

    private final LocalRepository localRepository;
    private final LocalCreateValidator localCreateValidator;
    private final LocalUpdateValidator localUpdateValidator;

    public LocalController(LocalRepository localRepository, LocalCreateValidator localCreateValidator, LocalUpdateValidator localUpdateValidator) {
        this.localRepository = localRepository;
        this.localCreateValidator = localCreateValidator;
        this.localUpdateValidator = localUpdateValidator;
    }

    @InitBinder("localCreateDTO")
    public void initCreateBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(localCreateValidator);
    }

    @InitBinder("localUpdateRequestDTO")
    public void initUpdateBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(localUpdateValidator);
    }

    @GetMapping("/create")
    public String showRegisterForm(LocalCreateDTO form) {
        if (!form.isDirty()) {
            form = new LocalCreateDTO();
        }

        return "/local/registerLocalForm";
    }

    @GetMapping("/update/{localId}")
    public String showUpdateForm(@PathVariable Long localId, LocalUpdateRequestDTO form, BindingResult bindingResult, Model model) {
        Optional<Local> existingLocal = localRepository.findById(localId);

        if (existingLocal.isEmpty()) {
            return "/error/404";
        }

        if (!form.isDirty()) {
            form = existingLocal.get().createLocalUpdateRequestDto();
        }

        model.addAttribute("localId", localId);
        model.addAttribute("localUpdateRequestDTO", form);

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
            return showUpdateForm(localId, localUpdateDTO, bindingResult, model);
        }

        Local existingLocal = localRepository.findById(localId).orElseThrow(NotFoundException::new);
        existingLocal.updateFromDTO(localUpdateDTO);
        localRepository.save(existingLocal);

        return "redirect:/form/update/" + localId;
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<Local> locals = localRepository.findAll();
        List<LocalResponseDTO> localsDtos = locals.stream().map(Local::createLocalResponseDto).toList();

        model.addAttribute("locais", localsDtos);

        return "/local/listLocal";
    }

    @PostMapping("/delete")
    public String deleteLocal(@RequestParam Long localId) {
        if (localRepository.existsById(localId)) {
            localRepository.deleteById(localId);
        }

        return "redirect:/form/list";
    }

}
