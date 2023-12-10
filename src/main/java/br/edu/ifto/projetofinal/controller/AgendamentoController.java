package br.edu.ifto.projetofinal.controller;

import br.edu.ifto.projetofinal.model.entity.Agenda;
import br.edu.ifto.projetofinal.model.entity.Agendamento;
import br.edu.ifto.projetofinal.model.entity.Medico;
import br.edu.ifto.projetofinal.model.repository.AgendaRepository;
import br.edu.ifto.projetofinal.model.repository.AgendamentoRepository;
import br.edu.ifto.projetofinal.model.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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


    @GetMapping("/{idMedico}")
    public String novoAgendamento(@PathVariable(value = "idMedico", required = false) Long idMedico, ModelMap model, Medico medico, Agendamento agendamento) {
        medico = medicoRepository.medico(idMedico);
        var agenda = agendaRepository.agendasPorMedico(idMedico);
        model.addAttribute("agenda", agenda);
        model.addAttribute("medico", medico);
        return "agendamentos/form";
    }

    @Transactional
    @PostMapping("/save")
    public ModelAndView save(@Valid Agendamento agendamento, BindingResult result, ModelMap model) {

        Agenda agenda = agendaRepository.agenda(agendamento.getAgenda().getId());
        agenda.setDisponivel(false);
        agendamentoRepository.save(agendamento);
        agendaRepository.update(agenda);

        return new ModelAndView("redirect:/agendamento/list");

    }


    @GetMapping("/list")
    public String listarAgendamentos(ModelMap model) {
        List<Agendamento> agendamentos = agendamentoRepository.agendamentos();

        model.addAttribute("agendamentos", agendamentos);

        return "agendamentos/lista";
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        Agendamento agendamento = agendamentoRepository.agendamento(id);

        Agenda agenda = agendaRepository.agenda(agendamento.getAgenda().getId());
        agenda.setDisponivel(true);

        agendamentoRepository.remove(id);
        agendaRepository.update(agenda);


        return new ModelAndView("redirect:/agendamento/list");
    }

}
