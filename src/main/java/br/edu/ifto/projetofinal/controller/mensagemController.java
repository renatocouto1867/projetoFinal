package br.edu.ifto.projetofinal.controller;

import br.edu.ifto.projetofinal.model.entity.Paciente;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
@RequestMapping("status")
public class mensagemController {


    @GetMapping("/sucesso")
    public String sucesso(Paciente paciente) {
        return "/mensagem/cadastro";
    }


}
