package br.edu.ifto.projetofinal.controller;

import br.edu.ifto.projetofinal.model.entity.Medico;
import br.edu.ifto.projetofinal.model.repository.MedicoRepository;
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
@RequestMapping("medico")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @GetMapping("/form")
    public String form(Medico medico) {

        return "/medicos/form";
    }

    @ResponseBody
    @RequestMapping("list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("medico", repository.medicos());
        return new ModelAndView("/medicos/lista", model);
    }

       @PostMapping("/save")
    public ModelAndView save(@Valid Medico medico, BindingResult result) {
        if(result.hasErrors())
            return new ModelAndView("/medicos/form");
        repository.save(medico);
        return new ModelAndView("redirect:/medico/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id, RedirectAttributes attributes) {

        if (!repository.medico(id).getConsultaList().isEmpty()) {
            attributes.addFlashAttribute("erro", "Não é permitida a exclusão de um Médico que já tenha consultas!");

        } else {
            repository.remove(id);
        }
        return new ModelAndView("redirect:/medico/list");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("medico", repository.medico(id));
        return new ModelAndView("/medicos/form");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Medico medico, BindingResult result) {
        if(result.hasErrors())
            return new ModelAndView("/medicos/form");

        repository.update(medico);
        return new ModelAndView("redirect:/medico/list");
    }

    @GetMapping("/buscarmedico")
    public String buscarMedico() {
        return "medicos/formbuscamedico";
    }

    @ResponseBody
    @RequestMapping("listnome")
    public ModelAndView listarNome(ModelMap model, @RequestParam String nome) {//@RequestParam para capturar o parametro
        model.addAttribute("medico", repository.medicoNome(nome));
        return new ModelAndView("medicos/formbuscamedico", model);
    }

    @GetMapping("/consultas/{id}")
    public ModelAndView medico(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("medico", repository.medico(id));
        return new ModelAndView("/medicos/consultas");
    }

    @GetMapping("/medico/{id}")
    public ModelAndView detalhaPaciente(@PathVariable("id") Long id, ModelMap model) {
        Medico medico = repository.medico(id);
        model.addAttribute("medico", medico);
        return new ModelAndView("/medicos/medico");
    }
}
