package br.edu.ifto.projetofinal.controller;

import br.edu.ifto.projetofinal.model.entity.*;
import br.edu.ifto.projetofinal.model.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Controller
@RequestMapping("agendamento")
public class AgendamentoController {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendaRepository agendaRepository;
    private final MedicoRepository medicoRepository;
    public AgendamentoController(AgendamentoRepository agendamentoRepository, AgendaRepository agendaRepository, MedicoRepository medicoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.medicoRepository = medicoRepository;
        this.agendaRepository = agendaRepository;
    }

    @GetMapping()
    public String form(@PathVariable(value = "id", required = false) Long id, Agendamento agendamento,ModelMap model) {
        var agendas = agendaRepository.agendamentos();
        System.out.printf("teste "+ id);
        model.addAttribute("agenda",agendas);

        if (id!=null){
            var agendas1 = agendaRepository.agenda(id);
            model.addAttribute("agenda",agendas1);
        }

        var medicos = medicoRepository.medicos();

        model.addAttribute("agenda",agendas);
        model.addAttribute("medicos",medicos);
        model.addAttribute("agendamento",agendamento);
        return "/agendamentos/form";
    }


}
