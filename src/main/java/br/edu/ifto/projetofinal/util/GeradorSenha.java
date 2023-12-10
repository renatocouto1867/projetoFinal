package br.edu.ifto.projetofinal.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author fagno
 */
public class GeradorSenha {

    public static void main(String[] args) {
        //solicitando o encode para 123
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }

}