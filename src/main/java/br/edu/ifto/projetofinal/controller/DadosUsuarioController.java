package br.edu.ifto.projetofinal.controller;

import br.edu.ifto.projetofinal.model.entity.Paciente;
import br.edu.ifto.projetofinal.model.repository.PacienteRepository;
import br.edu.ifto.projetofinal.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class DadosUsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/dadosusuario")
    @ResponseBody
    public ModelAndView obterUsuarioLogado(ModelMap model) {
        // Obtém a autenticação atual do contexto de segurança (SecurityContextHolder.getContext().getAuthentication())
        String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idPessoa = (usuarioRepository.usuario(nomeUsuario)).getPessoa().getIdPessoa();
        Paciente paciente = pacienteRepository.paciente(idPessoa);
        model.addAttribute("paciente", paciente);
        return new ModelAndView("/pacientes/paciente");
    }


    @GetMapping("/usuarioconsulta")
    public ModelAndView consultasPaciente(ModelMap model) {
        String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idPessoa = (usuarioRepository.usuario(nomeUsuario)).getPessoa().getIdPessoa();
        Paciente paciente = pacienteRepository.paciente(idPessoa);
        model.addAttribute("paciente", paciente);
        return new ModelAndView("/pacientes/consultas");
    }

}
