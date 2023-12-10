package br.edu.ifto.projetofinal.controller;

import br.edu.ifto.projetofinal.model.entity.Paciente;
import br.edu.ifto.projetofinal.model.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Transactional
@Controller
@RequestMapping("paciente")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @GetMapping("/modal")
    public String formPaciente() {
        return "/pacientes/modalpaciente";
    }

    @GetMapping("/form")
    public String form(Paciente paciente) {
        return "/pacientes/form";
    }

    @GetMapping("/selecionapaciente")
    public String selecionaPaciente() {
        return "/pacientes/formbuscapaciente";
    }

//    @ResponseBody
//    @RequestMapping("list")
@GetMapping("list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("paciente", repository.pacientes());
        return new ModelAndView("/pacientes/lista", model);
    }

//    @ResponseBody
//    @RequestMapping("listnome")
@GetMapping("listnome")
    public ModelAndView listarnome(ModelMap model, @RequestParam String nome) {//@RequestParam para capturar o parametro
        model.addAttribute("paciente", repository.pacientesNome(nome));
        return new ModelAndView("/pacientes/formbuscapaciente", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Paciente paciente, BindingResult result) {
        if(result.hasErrors())
            return new ModelAndView("/pacientes/form");
        repository.save(paciente);
        return new ModelAndView("redirect:/status/sucesso");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id, RedirectAttributes attributes) {
        if (!repository.paciente(id).getConsultaList().isEmpty()) {
            attributes.addFlashAttribute("erro", "Não é permitida a exclusão de um Paciente que já tenha consultas!");
        } else {
            repository.remove(id);
        }
        return new ModelAndView("redirect:/paciente/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("paciente", repository.paciente(id));
        return new ModelAndView("/pacientes/form");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Paciente paciente, BindingResult result) {
        if(result.hasErrors())
            return new ModelAndView("/pacientes/form");

        repository.update(paciente);
        return new ModelAndView("redirect:/status/sucesso");
    }

    @GetMapping("/consulta/{id}")
    public ModelAndView consultasPaciente(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("paciente", repository.paciente(id));
        return new ModelAndView("/pacientes/consultas");
    }

    @GetMapping("/paciente/{id}")
    public ModelAndView detalhaPaciente(@PathVariable("id") Long id, ModelMap model) {
        Paciente paciente = repository.paciente(id);
        model.addAttribute("paciente", paciente);
        return new ModelAndView("/pacientes/paciente");
    }

    @GetMapping("/paciente/")
    public ModelAndView detalhaPacienteLogado(Paciente paciente, ModelMap model) {
        Paciente paciente1 = repository.paciente(paciente.getIdPessoa());
        model.addAttribute("paciente", paciente);
        return new ModelAndView("/pacientes/paciente");
    }
}
