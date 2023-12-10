package br.edu.ifto.projetofinal.controller;

import br.edu.ifto.projetofinal.model.entity.*;
import br.edu.ifto.projetofinal.model.repository.ConsultaRepository;
import br.edu.ifto.projetofinal.model.repository.MedicoRepository;
import br.edu.ifto.projetofinal.model.repository.PacienteRepository;
import br.edu.ifto.projetofinal.model.repository.UsuarioRepository;
import br.edu.ifto.projetofinal.model.validation.groups.Insert;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Controller
@RequestMapping("consulta")
public class ConsultaController {
    private final ConsultaRepository repository;

    private final MedicoRepository medicoRepository;

    private final PacienteRepository pacienteRepository;

    private final UsuarioRepository usuarioRepository;

    public ConsultaController(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/form/{id}")
    public String novaConsulta(@PathVariable(value = "id", required = false) Long id, ModelMap model, Consulta consulta) {
        var medicos = medicoRepository.medicos();
        Paciente paciente = pacienteRepository.paciente(id);
        LocalDateTime dataConsulta = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        consulta.setPaciente(paciente);
        model.addAttribute("medicos", medicos);
        model.addAttribute("consulta", consulta);
        model.addAttribute("dataConsulta", dataConsulta.format(formatter));
        return "consultas/form";
    }


    @RequestMapping("consulta/{id}")
    public String consulta(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("consulta", repository.consulta(id));
        return "consultas/consulta";
    }


    @ResponseBody
    @RequestMapping("list")
    public ModelAndView listar(ModelMap model, Authentication authentication) {
        // Obtém o nome do usuário autenticado
        String nomeUsuario = authentication.getName();

        // para evitar o NullPointerException
        Usuario usuario = usuarioRepository.usuario(nomeUsuario);
        Long idPessoa = usuario != null && usuario.getId() != null ? usuario.getId() : null;


        Medico medico = medicoRepository.medico(idPessoa);

        // Verifica se o usuário tem a role "MEDICO"
        boolean isMedico = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_MEDICO"));

        if (isMedico) {
            List<Consulta> consultasDoMedico = repository.consultasDoMedico(medico);
            model.addAttribute("consulta", consultasDoMedico);
            model.addAttribute("valorTotal", repository.valorTotalMedico(medico));
        } else {
            model.addAttribute("consulta", repository.consultas());
            model.addAttribute("valorTotal", repository.valorTotal());
        }

        return new ModelAndView("consultas/lista", model);
    }


    @ResponseBody
    @RequestMapping("listporpaciente/{id}")
    public ModelAndView listarPorPaciente(@PathVariable("id") Long id, ModelMap model, Authentication authentication) {
        // Obtém o nome do usuário autenticado
        String nomeUsuario = authentication.getName();
        Long idPessoa = (usuarioRepository.usuario(nomeUsuario)).getPessoa().getIdPessoa();
        Medico medico = medicoRepository.medico(idPessoa);
        Paciente paciente = pacienteRepository.paciente(id);

        // Verifica se o usuário tem a role "MEDICO"
        boolean isMedico = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_MEDICO"));

        if (isMedico) {
            // Se o usuário é um médico, filtra as consultas associadas ao seu usuário
            List<Consulta> consultasDoMedico = repository.consultasDoMedicoEpaciente(medico, paciente);
            model.addAttribute("consulta", consultasDoMedico);
            model.addAttribute("valorTotal", repository.valorTotalMedicoEpaciente(medico, paciente));
        } else {
            // Se não for um médico, lista todas as consultas
            model.addAttribute("consulta", repository.consultas());
            model.addAttribute("valorTotal", repository.valorTotal());
        }

        System.out.printf("valor total " + repository.consultasDoMedicoEpaciente(medico, paciente));

        return new ModelAndView("consultas/lista", model);
    }


    @PostMapping("/save")
    public ModelAndView save(@Valid Consulta consulta, BindingResult result, ModelMap model) {
        var medicos = medicoRepository.medicos(); // em caso de erro ao salver é necessário retornar a lista de medicos para continuar a edição
        model.addAttribute("medicos", medicos);

        LocalDateTime dataConsulta = LocalDateTime.now(); // retora a data atual do servidor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        model.addAttribute("dataConsulta", dataConsulta.format(formatter));


        if (result.hasErrors())
            return new ModelAndView("/consultas/form");

        repository.save(consulta);

        return new ModelAndView("redirect:/consulta/list");

    }


    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        repository.remove(id);
        return new ModelAndView("redirect:/consulta/list");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("consulta", repository.consulta(id));
        var medicos = medicoRepository.medicos();
        model.addAttribute("medicos", medicos);
        return new ModelAndView("/consultas/form");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Consulta consulta, BindingResult result, ModelMap model) {

        var medicos = medicoRepository.medicos();// em caso de erro ao salver é necessário retornar a lista de medicos para continuar a edição
        model.addAttribute("medicos", medicos);

        if (result.hasErrors()) {
            return new ModelAndView("/consultas/form");
        }

        repository.update(consulta);

        return new ModelAndView("redirect:/consulta/list");
    }


}
