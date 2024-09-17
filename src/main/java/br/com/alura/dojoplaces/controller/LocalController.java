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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String showUpdateForm(@PathVariable Long localId, LocalUpdateRequestDTO localUpdateRequestDTO, BindingResult bindingResult, Model model) {
        Optional<Local> existingLocal = localRepository.findById(localId);

        if (existingLocal.isEmpty()) {
            return "/error/404";
        }

        if (!localUpdateRequestDTO.isDirty()) {
            localUpdateRequestDTO = new LocalUpdateRequestDTO(existingLocal.get());
        }

        model.addAttribute("localId", localId);
        model.addAttribute("localUpdateRequestDTO", localUpdateRequestDTO);
        model.addAttribute("bindingResult", bindingResult);

        return "/local/updateLocalForm";
    }

    @PostMapping("/create")
    public String submitForm(@Valid LocalCreateDTO localCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            localCreateDTO.markAsDirty();
            return showRegisterForm(localCreateDTO);
        }

        localCreateDTO.setCode(localCreateDTO.getCode().trim());
        boolean localAlreadyExists = localRepository.existsByCode(localCreateDTO.getCode());

        if (localAlreadyExists) {
            bindingResult.rejectValue("code", "error.local.already.exists", "Já existe um local com este código");
            localCreateDTO.markAsDirty();
            return showRegisterForm(localCreateDTO);
        }

        Local local = localCreateDTO.toModel();
        localRepository.save(local);

        return "redirect:/form/list";
    }

    @PostMapping("/update/{localId}")
    public String editForm(@Valid LocalUpdateRequestDTO localUpdateDTO, BindingResult bindingResult, @PathVariable Long localId, Model model) {
        if (bindingResult.hasErrors()) {
            localUpdateDTO.markAsDirty();
            model.addAttribute("localUpdateRequestDTO", localUpdateDTO);
            model.addAttribute("bindingResult", bindingResult);
            return showUpdateForm(localId, localUpdateDTO, bindingResult, model);
        }

        Local existingLocal = localRepository.findById(localId).orElseThrow(NotFoundException::new);
        existingLocal.update(localUpdateDTO.getName(), localUpdateDTO.getCode().trim(), localUpdateDTO.getNeighbourhood(), localUpdateDTO.getCity());
        localRepository.save(existingLocal);

        return "redirect:/form/list";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<Local> locals = localRepository.findAll();
        List<LocalResponseDTO> localsDtos = locals.stream().map(LocalResponseDTO::new).toList();

        model.addAttribute("locals", localsDtos);

        return "/local/listLocal";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteLocal(@RequestParam Long localId) {
        if (localRepository.existsById(localId)) {
            localRepository.deleteById(localId);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
